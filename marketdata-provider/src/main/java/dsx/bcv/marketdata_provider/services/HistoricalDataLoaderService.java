package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.AlphaVantageQuoteProvider;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.AlphaVantageSupportedCurrencies;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.stream.Collectors;

//@Service
@Slf4j
public class HistoricalDataLoaderService {

    private final AlphaVantageQuoteProvider alphaVantageQuoteProvider;
    private final ConversionService conversionService;
    private final CurrencyService currencyService;
    private final AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies;
    private final BarService barService;

    public HistoricalDataLoaderService(
            AlphaVantageQuoteProvider alphaVantageQuoteProvider,
            ConversionService conversionService,
            CurrencyService currencyService,
            AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies,
            BarService barService
    ) {

        this.alphaVantageQuoteProvider = alphaVantageQuoteProvider;
        this.conversionService = conversionService;
        this.currencyService = currencyService;
        this.alphaVantageSupportedCurrencies = alphaVantageSupportedCurrencies;
        this.barService = barService;

        saveSupportedCurrenciesToDb();

        new Thread(this::loadData).start();
    }

    void saveSupportedCurrenciesToDb() {

        log.info("saveSupportedCurrenciesToDb method called");

        if (currencyService.count() != 0) {
            log.info("Supported currencies are already saved in database");
            return;
        }

        final List<Currency> physicalCurrencies = alphaVantageSupportedCurrencies.getPhysicalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Currency.class))
                .collect(Collectors.toList());

        currencyService.saveAll(physicalCurrencies);
        log.info("Physical currencies saved. List: {}", physicalCurrencies);

        final List<Currency> digitalCurrencies = alphaVantageSupportedCurrencies.getDigitalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Currency.class))
                .collect(Collectors.toList());

        currencyService.saveAll(digitalCurrencies);
        log.info("Digital currencies saved. List: {}", digitalCurrencies);
    }

    public void loadData() {

        log.info("Start loading historical data");

        final var physicalCurrencies = alphaVantageSupportedCurrencies.getPhysicalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Currency.class))
                .collect(Collectors.toList());

        log.debug("Start loading physical currencies. List: {}", physicalCurrencies);

        for (int i = 0; i < physicalCurrencies.size(); i++) {

            var currency = physicalCurrencies.get(i);
            log.debug("Loading history for {} {}/{}", currency, i + 1, physicalCurrencies.size());

            if (!barService.existsByCurrency(currency)) {
                final var forexDailyBars =
                        alphaVantageQuoteProvider.getForexDailyHistoricalRate(
                                conversionService.convert(currency, AlphaVantageCurrency.class)
                        );
                barService.saveAll(forexDailyBars);
                log.debug("History for {} saved", currency);
                sleep();
            } else {
                log.debug("History for {} is already saved", currency);
            }
        }

        log.info("Physical currencies history saved");

        final var digitalCurrencies = alphaVantageSupportedCurrencies.getDigitalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Currency.class))
                .collect(Collectors.toList());

        log.debug("Start loading digital currencies. List: {}", digitalCurrencies);

        for (int i = 0; i < digitalCurrencies.size(); i++) {

            var currency = digitalCurrencies.get(i);
            log.debug("Loading history for {} {}/{}", currency, i + 1, digitalCurrencies.size());

            if (!barService.existsByCurrency(currency)) {
                final var digitalDailyBars =
                        alphaVantageQuoteProvider.getDigitalDailyHistoricalRate(
                                conversionService.convert(currency, AlphaVantageCurrency.class)
                        );
                barService.saveAll(digitalDailyBars);
                log.debug("History for {} saved", currency);
                sleep();
            } else {
                log.debug("History for {} is already saved", currency);
            }
        }
        log.info("Digital currencies history saved");
    }

    private void sleep() {
        final var sleepTimeInSeconds = 12;
        final var millisecondsInSecond = 1000;
        final var sleepTimeInMilliseconds = (long)millisecondsInSecond * sleepTimeInSeconds;
        try {
            Thread.sleep(sleepTimeInMilliseconds);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
