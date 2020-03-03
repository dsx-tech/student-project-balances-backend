package dsx.bcv.marketdata_provider.services;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.data.models.Ticker;
import dsx.bcv.marketdata_provider.exceptions.NotFoundException;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.AlphaVantageSupportedCurrencies;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.DsxQuoteProvider;
import dsx.bcv.marketdata_provider.views.InstrumentVO;
import kotlin.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuoteProviderService {

    private final DsxQuoteProvider dsxQuoteProvider;
    private final ConversionService conversionService;
    private final AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies;
    private final BarService barService;
    private final TickerService tickerService;
    private final CurrencyService currencyService;

    public QuoteProviderService(
            DsxQuoteProvider dsxQuoteProvider,
            ConversionService conversionService,
            AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies,
            BarService barService,
            TickerService tickerService,
            CurrencyService currencyService
    ) {
        this.dsxQuoteProvider = dsxQuoteProvider;
        this.conversionService = conversionService;
        this.alphaVantageSupportedCurrencies = alphaVantageSupportedCurrencies;
        this.barService = barService;
        this.tickerService = tickerService;
        this.currencyService = currencyService;
    }

    public List<Currency> getCurrencies() {
        var currencies = alphaVantageSupportedCurrencies.getPhysicalCurrencies();
        currencies.addAll(alphaVantageSupportedCurrencies.getDigitalCurrencies());
        return currencies.stream()
                .map(alphaVantageCurrency ->
                        conversionService.convert(alphaVantageCurrency, Currency.class)
                )
                .collect(Collectors.toList());
    }

    public List<InstrumentVO> getInstruments() {
        return dsxQuoteProvider.getInstruments().stream()
                .map(instrument -> conversionService.convert(instrument, InstrumentVO.class))
                .collect(Collectors.toList());
    }

    public List<Bar> getBarsInPeriod(String instrument, long startTime, long endTime) {

        final var currencyPair = getCurrencyPairFromInstrumentString(instrument);
        final var baseCurrency = currencyPair.getFirst();
        final var quotedCurrency = currencyPair.getSecond();

        var baseCurrencyBars = barService.findByBaseCurrencyAndTimestampBetween(
                baseCurrency,
                startTime,
                endTime
        );

        var quotedCurrencyBars = barService.findByBaseCurrencyAndTimestampBetween(
                quotedCurrency,
                startTime,
                endTime
        );

        if (baseCurrencyBars.size() != quotedCurrencyBars.size()) {
            log.warn("Not enough data on server");
            throw new RuntimeException(
                    "Not enough data on server. Try to select a smaller interval."
            );
        }

        var resultBars = new ArrayList<Bar>();
        for (int i = 0; i < baseCurrencyBars.size(); i++) {
            if (baseCurrencyBars.get(i).getTimestamp() != quotedCurrencyBars.get(i).getTimestamp()) {
                log.warn("Timestamps are mixed up!");
                throw new RuntimeException("Timestamps are mixed up!");
            }
            var resultBar = new Bar(
                    baseCurrency,
                    baseCurrencyBars.get(i).getExchangeRate().divide(
                            quotedCurrencyBars.get(i).getExchangeRate(),
                            10,
                            RoundingMode.HALF_UP
                    ),
                    baseCurrencyBars.get(i).getTimestamp()
            );
            resultBars.add(resultBar);
        }

        return resultBars;
    }

    private Pair<Currency, Currency> getCurrencyPairFromInstrumentString(String instrument) {

        var currencyPair = Lists.newArrayList(Splitter.on("-").split(instrument));
        if (currencyPair.size() != 2) {
            throw new NotFoundException("Invalid instrument");
        }

        var baseCurrency = new Currency(currencyPair.get(0));
        var quotedCurrency = new Currency(currencyPair.get(1));

        if (currencyService.findByCode(baseCurrency.getCode()).isEmpty()) {
            log.warn("Base currency {} from request is not supported", baseCurrency);
            throw new NotFoundException(
                    "Base currency" + baseCurrency + "from request is not supported"
            );
        }
        if (currencyService.findByCode(quotedCurrency.getCode()).isEmpty()) {
            log.warn("Quoted currency {} from request is not supported", quotedCurrency);
            throw new NotFoundException(
                    "Quoted currency" + quotedCurrency + "from request is not supported"
            );
        }

        baseCurrency = currencyService.findByCode(baseCurrency.getCode()).get();
        quotedCurrency = currencyService.findByCode(quotedCurrency.getCode()).get();

        return new Pair<>(baseCurrency, quotedCurrency);
    }

    public Ticker getTicker(String instrument) {

        final var currencyPair = getCurrencyPairFromInstrumentString(instrument);
        final var baseCurrency = currencyPair.getFirst();
        final var quotedCurrency = currencyPair.getSecond();

        var baseCurrencyTicker = tickerService.findByBaseCurrency(baseCurrency);
        var quotedCurrencyTicker = tickerService.findByBaseCurrency(quotedCurrency);

        return new Ticker(
                baseCurrency,
                baseCurrencyTicker.getExchangeRate().divide(
                        quotedCurrencyTicker.getExchangeRate(),
                        10,
                        RoundingMode.HALF_UP
                ),
                baseCurrencyTicker.getTimestamp()
        );
    }
}
