package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.services.RequestService;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCryptoBar;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCurrency;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageForexBar;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageTicker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlphaVantageQuoteProvider {

    private final List<String> apiKeyList = ImmutableList.of(
            "9AHDGW3TU1P53QD5",
            "KLSNN06MT5YKD8G6",
            "M17XFAE2JFPU6GB0",
            "M593Q9YBLNAHRXS9",
            "R8E6U7RI11YSJ9YU",
            "GPHASUAC6Y3JNZLH",
            "N9NMMTJL56RZO0EW",
            "CLWFP9W6HGD5KSO9",
            "X4T83HP3NZMB985U",
            "2Z98LLDBELVNIS9H",
            "3XFWJR1EBGNZ0LIG",
            "E4DD94ZAZ9Z1ZE0G",
            "SILL33XLLYXOXDEZ",
            "6YNTYM5J7YETR6X9",
            "FTBUJQ2P079OLHI9"
    );

    private final RequestService requestService;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;

    public AlphaVantageQuoteProvider(RequestService requestService, ObjectMapper objectMapper, ConversionService conversionService) {
        this.requestService = requestService;
        this.objectMapper = objectMapper;
        this.conversionService = conversionService;
    }

    @SneakyThrows
    public List<Bar> getForexDailyHistoricalRate(AlphaVantageCurrency baseCurrency) {

        log.trace("Historical rate for {} method called", baseCurrency);

        var requestUrl =
                "https://www.alphavantage.co/query" +
                "?function=FX_DAILY" +
                "&from_symbol=" + baseCurrency +
                "&to_symbol=USD" +
                "&outputsize=full" +
                "&apikey=" + getApiKey();

        log.trace("Send request to Alpha Vantage, url: {}", requestUrl);

        var responseBody = requestService.doGetRequest(requestUrl);

        if (responseBody.contains("Error Message")) {
            log.warn("{} is not supported? Retry request...", baseCurrency);
            getForexDailyHistoricalRate(baseCurrency);
        }
        if (responseBody.contains("Our standard API call frequency is 5 calls per minute and 500 calls per day")) {
            log.warn("ERROR. High API call frequency");
        }

        log.trace("Alpha Vantage returns:\n{}...", responseBody.substring(0, 200));

        var responseBodyJO = new JSONObject(responseBody);
        var rates = String.valueOf(responseBodyJO.get("Time Series FX (Daily)"));

        var ratesJO = new JSONObject(rates);
        var sortedKeySet = new TreeSet<>(ratesJO.keySet());

        var firstDate = sortedKeySet.first();
        var previousAlphaVantageForexBar = objectMapper.readValue(
                String.valueOf(ratesJO.get(firstDate)),
                AlphaVantageForexBar.class
        );
        previousAlphaVantageForexBar.setDate(LocalDate.parse(firstDate));

        var resultList = new ArrayList<AlphaVantageForexBar>();
        for (var key : sortedKeySet) {
            var currencyRateString = String.valueOf(ratesJO.get(key));
            var currentAlphaVantageForexBar = objectMapper.readValue(
                    currencyRateString,
                    AlphaVantageForexBar.class
            );
            currentAlphaVantageForexBar.setCurrency(baseCurrency);
            currentAlphaVantageForexBar.setDate(LocalDate.parse(key));
            for (var epochDay = previousAlphaVantageForexBar.getDate().toEpochDay() + 1;
                 epochDay < currentAlphaVantageForexBar.getDate().toEpochDay();
                 epochDay++) {
                resultList.add(
                        new AlphaVantageForexBar(
                                baseCurrency,
                                previousAlphaVantageForexBar.getExchangeRate(),
                                LocalDate.ofEpochDay(epochDay)
                        )
                );
            }
            resultList.add(currentAlphaVantageForexBar);
            previousAlphaVantageForexBar = currentAlphaVantageForexBar;
        }

        log.trace("First 5 bars: {}", resultList.subList(0, 5));

        return resultList.stream()
                .map(bar -> conversionService.convert(bar, Bar.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public List<Bar> getDigitalDailyHistoricalRate(AlphaVantageCurrency baseCurrency) {

        log.trace("Historical rate for {} method called", baseCurrency);

        var requestUrl =
                "https://www.alphavantage.co/query" +
                "?function=DIGITAL_CURRENCY_DAILY" +
                "&symbol=" + baseCurrency +
                "&market=USD" +
                "&apikey=" + getApiKey();

        log.trace("Send request to Alpha Vantage, url: {}", requestUrl);

        var responseBody = requestService.doGetRequest(requestUrl);

        if (responseBody.contains("Error Message")) {
            log.warn("{} is not supported", baseCurrency);
        }
        if (responseBody.contains("Our standard API call frequency is 5 calls per minute and 500 calls per day")) {
            log.warn("ERROR. High API call frequency");
        }

        log.trace("Alpha Vantage returns:\n{}...", responseBody.substring(0, 1000));

        var responseBodyJO = new JSONObject(responseBody);
        var rates = String.valueOf(responseBodyJO.get("Time Series (Digital Currency Daily)"));

        var ratesJO = new JSONObject(rates);
        var sortedKeySet = new TreeSet<>(ratesJO.keySet());

        var firstDate = sortedKeySet.first();
        var previousAlphaVantageCryptoBar = objectMapper.readValue(
                String.valueOf(ratesJO.get(firstDate)),
                AlphaVantageCryptoBar.class
        );
        previousAlphaVantageCryptoBar.setDate(LocalDate.parse(firstDate));

        var resultList = new ArrayList<AlphaVantageCryptoBar>();
        for (var key : sortedKeySet) {
            var currencyRateString = String.valueOf(ratesJO.get(key));
            var currentAlphaVantageCryptoBar = objectMapper.readValue(
                    currencyRateString,
                    AlphaVantageCryptoBar.class
            );
            currentAlphaVantageCryptoBar.setCurrency(baseCurrency);
            currentAlphaVantageCryptoBar.setDate(LocalDate.parse(key));
            for (var epochDay = previousAlphaVantageCryptoBar.getDate().toEpochDay() + 1;
                 epochDay < currentAlphaVantageCryptoBar.getDate().toEpochDay();
                 epochDay++) {
                resultList.add(
                        new AlphaVantageCryptoBar(
                                baseCurrency,
                                previousAlphaVantageCryptoBar.getExchangeRate(),
                                LocalDate.ofEpochDay(epochDay)
                        )
                );
            }
            resultList.add(currentAlphaVantageCryptoBar);
            previousAlphaVantageCryptoBar = currentAlphaVantageCryptoBar;
        }

        log.trace("First 5 bars: {}", resultList.subList(0, 5));

        return resultList.stream()
                .map(rate -> conversionService.convert(rate, Bar.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public AlphaVantageTicker getTicker(String baseCurrencyCode, String quotedCurrencyCode) {

        log.trace("Historical rate for {}-{} method called", baseCurrencyCode, quotedCurrencyCode);

        var requestUrl =
                "https://www.alphavantage.co/query" +
                        "?function=CURRENCY_EXCHANGE_RATE" +
                        "&from_currency=" + baseCurrencyCode +
                        "&to_currency=" + quotedCurrencyCode +
                        "&apikey=" + getApiKey();

        log.trace("Send request to Alpha Vantage, url: {}", requestUrl);

        var responseBody = requestService.doGetRequest(requestUrl);
        if (responseBody.contains("Error Message")) {
            log.warn("Error messsage");
        }
        if (responseBody.contains(
                "Our standard API call frequency is 5 calls per minute and 500 calls per day"
        )) {
            log.warn("Error. High API call frequency");
            throw new RuntimeException(
                    "Error. High API call frequency. " +
                    "Our standard ticker API call frequency is 5 calls per minute and 500 calls per day. " +
                    "Sorry, скоро сделаю нормально"
            );
        }

        var responseBodyJO = new JSONObject(responseBody);
        var alphaVantageTickerString = String.valueOf(responseBodyJO.get("Realtime Currency Exchange Rate"));

        return objectMapper.readValue(alphaVantageTickerString, AlphaVantageTicker.class);
    }

    private String getApiKey() {
        return apiKeyList.get(new Random().nextInt(apiKeyList.size()));
    }
}
