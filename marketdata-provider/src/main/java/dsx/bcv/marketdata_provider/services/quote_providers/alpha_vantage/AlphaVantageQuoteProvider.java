package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.services.RequestService;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCryptoHistoricalRate;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageForexHistoricalRate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlphaVantageQuoteProvider {

    private final String apiKey = "IBD9WPO8HRINYP15";
    private final RequestService requestService;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;

    public AlphaVantageQuoteProvider(RequestService requestService, ObjectMapper objectMapper, ConversionService conversionService) {
        this.requestService = requestService;
        this.objectMapper = objectMapper;
        this.conversionService = conversionService;
    }

    @SneakyThrows
    public List<Bar> getForexDailyHistoricalRate(Currency baseCurrency) {

        log.debug("getForexDailyHistoricalRate called for {}", baseCurrency);

        var responseBody = requestService.doGetRequest(
                "https://www.alphavantage.co/query" +
                        "?function=FX_DAILY" +
                        "&from_symbol=" + baseCurrency +
                        "&to_symbol=USD" +
                        "&outputsize=full" +
                        "&apikey=" + apiKey
        );

        var responseBodyJO = new JSONObject(responseBody);
        var rates = String.valueOf(responseBodyJO.get("Time Series FX (Daily)"));

        var ratesJO = new JSONObject(rates);
        var sortedKeySet = new TreeSet<>(ratesJO.keySet());

        var firstDate = sortedKeySet.first();
        var previousAlphaVantageForexHistoricalRate = objectMapper.readValue(
                String.valueOf(ratesJO.get(firstDate)),
                AlphaVantageForexHistoricalRate.class
        );
        previousAlphaVantageForexHistoricalRate.setDate(LocalDate.parse(firstDate));

        var resultList = new ArrayList<AlphaVantageForexHistoricalRate>();
        for (var key : sortedKeySet) {
            var currencyRateString = String.valueOf(ratesJO.get(key));
            var currentAlphaVantageBar = objectMapper.readValue(
                    currencyRateString,
                    AlphaVantageForexHistoricalRate.class
            );
            currentAlphaVantageBar.setDate(LocalDate.parse(key));
            for (var epochDay = previousAlphaVantageForexHistoricalRate.getDate().toEpochDay() + 1;
                 epochDay < currentAlphaVantageBar.getDate().toEpochDay();
                 epochDay++) {
                resultList.add(
                        new AlphaVantageForexHistoricalRate(
                                previousAlphaVantageForexHistoricalRate.getExchangeRate(),
                                LocalDate.ofEpochDay(epochDay)
                        )
                );
            }
            resultList.add(currentAlphaVantageBar);
            previousAlphaVantageForexHistoricalRate = currentAlphaVantageBar;
        }

        return resultList.stream()
                .map(rate -> conversionService.convert(rate, Bar.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public List<Bar> getDigitalDailyHistoricalRate(Currency baseCurrency) {

        log.debug("getDigitalDailyHistoricalRate called for {}", baseCurrency);

        var responseBody = requestService.doGetRequest(
                "https://www.alphavantage.co/query" +
                        "?function=DIGITAL_CURRENCY_DAILY" +
                        "&symbol=" + baseCurrency +
                        "&market=USD" +
                        "&apikey=" + apiKey
        );

        var responseBodyJO = new JSONObject(responseBody);
        var rates = String.valueOf(responseBodyJO.get("Time Series (Digital Currency Daily)"));

        var ratesJO = new JSONObject(rates);
        var sortedKeySet = new TreeSet<>(ratesJO.keySet());

        var firstDate = sortedKeySet.first();
        var previousAlphaVantageCryptoHistoricalRate = objectMapper.readValue(
                String.valueOf(ratesJO.get(firstDate)),
                AlphaVantageCryptoHistoricalRate.class
        );
        previousAlphaVantageCryptoHistoricalRate.setDate(LocalDate.parse(firstDate));

        var resultList = new ArrayList<AlphaVantageCryptoHistoricalRate>();
        for (var key : sortedKeySet) {
            var currencyRateString = String.valueOf(ratesJO.get(key));
            var currentAlphaVantageCryptoHistoricalRate = objectMapper.readValue(
                    currencyRateString,
                    AlphaVantageCryptoHistoricalRate.class
            );
            currentAlphaVantageCryptoHistoricalRate.setDate(LocalDate.parse(key));
            for (var epochDay = previousAlphaVantageCryptoHistoricalRate.getDate().toEpochDay() + 1;
                 epochDay < currentAlphaVantageCryptoHistoricalRate.getDate().toEpochDay();
                 epochDay++) {
                resultList.add(
                        new AlphaVantageCryptoHistoricalRate(
                                previousAlphaVantageCryptoHistoricalRate.getExchangeRate(),
                                LocalDate.ofEpochDay(epochDay)
                        )
                );
            }
            resultList.add(currentAlphaVantageCryptoHistoricalRate);
            previousAlphaVantageCryptoHistoricalRate = currentAlphaVantageCryptoHistoricalRate;
        }

        return resultList.stream()
                .map(rate -> conversionService.convert(rate, Bar.class))
                .collect(Collectors.toList());
    }
}
