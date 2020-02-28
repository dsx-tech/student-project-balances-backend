package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.services.RequestService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;

import java.util.stream.Collectors;

//@Service
@Slf4j
public class AlphaVantageApiChecker {

    private final AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies;
    private final ConversionService conversionService;
    private final RequestService requestService;

    public AlphaVantageApiChecker(
            AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies,
            ConversionService conversionService,
            RequestService requestService
    ) {
        this.alphaVantageSupportedCurrencies = alphaVantageSupportedCurrencies;
        this.conversionService = conversionService;
        this.requestService = requestService;

        new Thread(this::check).start();
    }

    @SneakyThrows
    private void check() {

        final var apiKey1 = "PPKFFSGZ5IH9KBU9";
        final var apiKey2 = "IBD9WPO8HRINYP15";

        final var physicalCurrencies = this.alphaVantageSupportedCurrencies.getPhysicalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Currency.class))
                .collect(Collectors.toList());

        for (var currency : physicalCurrencies) {

            log.info("Request history for {}", currency);
            var requestUrl =
                    "https://www.alphavantage.co/query" +
                            "?function=FX_DAILY" +
                            "&from_symbol=" + currency +
                            "&to_symbol=USD" +
                            "&apikey=" + apiKey1;

            log.trace("Send request to Alpha Vantage, url: {}", requestUrl);
            var responseBody = requestService.doGetRequest(requestUrl);
            log.trace("Response body: {}", responseBody);
            if (responseBody.contains("Error Message")) {
                log.info("{} is not supported", currency);
            }
            if (responseBody.contains("Our standard API call frequency is 5 calls per minute and 500 calls per day")) {
                log.info("ERROR. High API call frequency");
            }
            sleep();
        }

        final var digitalCurrencies = this.alphaVantageSupportedCurrencies.getDigitalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Currency.class))
                .collect(Collectors.toList());

        var i = 1;
        for (var currency : digitalCurrencies) {

            log.info("{}. Request history for {}", i++, currency);

            var requestUrl =
                    "https://www.alphavantage.co/query" +
                            "?function=DIGITAL_CURRENCY_DAILY" +
                            "&symbol=" + currency +
                            "&market=USD" +
                            "&apikey=" + ((i < 400) ? apiKey1 : apiKey2);

            log.trace("Send request to Alpha Vantage, url: {}", requestUrl);
            var responseBody = requestService.doGetRequest(requestUrl);
            log.trace("Response body: {}", responseBody);
            if (responseBody.contains("Error Message")) {
                log.info("{} is not supported", currency);
            }
            if (responseBody.contains("Our standard API call frequency is 5 calls per minute and 500 calls per day")) {
                log.info("high API call frequency");
            }
            sleep();
        }
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