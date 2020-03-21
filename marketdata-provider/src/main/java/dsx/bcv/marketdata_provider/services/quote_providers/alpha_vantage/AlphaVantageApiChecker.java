package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.data.models.Asset;
import dsx.bcv.marketdata_provider.services.RequestService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;

import java.io.IOException;
import java.util.stream.Collectors;

//@Service
@Slf4j
public class AlphaVantageApiChecker {

    private final AlphaVantageSupportedAssets alphaVantageSupportedCurrencies;
    private final ConversionService conversionService;
    private final RequestService requestService;
    private final AlphaVantageApiKeyProvider alphaVantageApiKeyProvider;
    private final AlphaVantageRateLimiterService rateLimiterService;

    public AlphaVantageApiChecker(
            AlphaVantageSupportedAssets alphaVantageSupportedCurrencies,
            ConversionService conversionService,
            RequestService requestService,
            AlphaVantageApiKeyProvider alphaVantageApiKeyProvider,
            AlphaVantageRateLimiterService rateLimiterService) {
        this.alphaVantageSupportedCurrencies = alphaVantageSupportedCurrencies;
        this.conversionService = conversionService;
        this.requestService = requestService;
        this.alphaVantageApiKeyProvider = alphaVantageApiKeyProvider;
        this.rateLimiterService = rateLimiterService;

        new Thread(this::checkAll).start();
    }

    private void checkAll() {
        checkPhysicalCurrencies();
        checkDigitalCurrencies();
    }

    @SneakyThrows
    private void checkPhysicalCurrencies() {

        log.info("Checking supported physical currencies");

        final var physicalCurrencies = this.alphaVantageSupportedCurrencies.getPhysicalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Asset.class))
                .collect(Collectors.toList());

        for (var currency : physicalCurrencies) {
            log.info("Request history for {}", currency);
            var requestUrl =
                    "https://www.alphavantage.co/query" +
                            "?function=FX_DAILY" +
                            "&from_symbol=" + currency +
                            "&to_symbol=USD" +
                            "&apikey=" + alphaVantageApiKeyProvider.getApiKey();
            sendRequest(currency, requestUrl);
        }
    }

    @SneakyThrows
    private void checkDigitalCurrencies() {

        log.info("Checking supported digital currencies");

        final var digitalCurrencies = this.alphaVantageSupportedCurrencies.getDigitalCurrencies()
                .stream()
                .map(currency -> conversionService.convert(currency, Asset.class))
                .collect(Collectors.toList());

        var i = 1;
        for (var currency : digitalCurrencies) {
            log.info("{}. Request history for {}", i++, currency);
            var requestUrl =
                    "https://www.alphavantage.co/query" +
                            "?function=DIGITAL_CURRENCY_DAILY" +
                            "&symbol=" + currency +
                            "&market=USD" +
                            "&apikey=" + alphaVantageApiKeyProvider.getApiKey();
            sendRequest(currency, requestUrl);
        }
    }

    private void sendRequest(Asset asset, String requestUrl) throws IOException {
        log.trace("Send request to Alpha Vantage, url: {}", requestUrl);
        rateLimiterService.getRateLimiter().acquire();
        var responseBody = requestService.doGetRequest(requestUrl);
        log.trace("Response body: {}", responseBody);
        if (responseBody.contains("Error Message")) {
            log.info("{} is not supported", asset);
        }
        if (responseBody.contains("Our standard API call frequency is 5 calls per minute and 500 calls per day")) {
            log.info("ERROR. High API call frequency");
        }
    }
}
