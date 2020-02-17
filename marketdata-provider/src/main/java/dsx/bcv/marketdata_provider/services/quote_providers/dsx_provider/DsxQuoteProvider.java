package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Ticker;
import dsx.bcv.marketdata_provider.exceptions.NotFoundException;
import dsx.bcv.marketdata_provider.services.RequestService;
import dsx.bcv.marketdata_provider.services.quote_providers.QuoteProvider;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.currency_graph.DsxCurrencyGraph;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.currency_graph.DsxCurrencyVertex;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.currency_graph.DsxSupportedCurrenciesRepository;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxBar;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxTicker;
import kotlin.Pair;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DsxQuoteProvider implements QuoteProvider {

    private RequestService requestService;
    private ObjectMapper objectMapper;
    private ConversionService conversionService;
    private DsxCurrencyGraph dsxCurrencyGraph;
    private DsxSupportedCurrenciesRepository dsxSupportedCurrenciesRepository;

    public DsxQuoteProvider(
            RequestService requestService,
            ObjectMapper objectMapper,
            ConversionService conversionService,
            DsxCurrencyGraph dsxCurrencyGraph,
            DsxSupportedCurrenciesRepository dsxSupportedCurrenciesRepository)
    {
        this.requestService = requestService;
        this.objectMapper = objectMapper;
        this.conversionService = conversionService;
        this.dsxCurrencyGraph = dsxCurrencyGraph;
        this.dsxSupportedCurrenciesRepository = dsxSupportedCurrenciesRepository;
    }

    @Override
    @SneakyThrows
    public List<Bar> getBarsInPeriod(String instrument, long startTime, long endTime) {

        var currencyPair = getCurrencyPairFromString(instrument);
        var instrumentList = dsxCurrencyGraph.getShortestPath(
                currencyPair.getFirst(),
                currencyPair.getSecond()
        );

        var barsList = new ArrayList<List<Bar>>();
        for (var dsxInstrumentEdge : instrumentList) {
            var responseBody = requestService.doGetRequest(
                    String.format(
                            "https://dsx.uk/mapi/periodBars/%s/d/%d/%d",
                            dsxInstrumentEdge.toString(),
                            startTime,
                            endTime
                    ));
            var jsonObject = new JSONObject(responseBody);
            var barsString = String.valueOf(jsonObject.get(fixBchProblem(dsxInstrumentEdge.toString())));
            List<DsxBar> tmp = objectMapper.readValue(barsString, new TypeReference<List<DsxBar>>() {});
            barsList.add(tmp.stream()
                    .map(dsxBar -> conversionService.convert(dsxBar, Bar.class))
                    .collect(Collectors.toList()));
        }

        for (var bars : barsList) {
            if (bars.size() != barsList.get(0).size()) {
                throw new NotFoundException("Not enough data on server");
            }
        }

        var resultList = new ArrayList<Bar>();
        for (int i = 0; i < barsList.get(0).size(); i++) {
            var exchangeRate = new BigDecimal("1");
            for (int j = 0; j < barsList.size(); j++) {
                if (instrumentList.get(j).isReversed()) {
                    exchangeRate = exchangeRate.divide(
                            barsList.get(j).get(i).getExchangeRate(),
                            10,
                            RoundingMode.HALF_UP
                    );
                } else {
                    exchangeRate = exchangeRate.multiply(barsList.get(j).get(i).getExchangeRate());
                }
            }
            resultList.add(new Bar(
                    exchangeRate,
                    barsList.get(0).get(i).getTimestamp()
            ));
        }

        return resultList;
    }

    @Override
    @SneakyThrows
    public Ticker getTicker(String instrument) {

        var currencyPair = getCurrencyPairFromString(instrument);
        var instrumentEdgeList = dsxCurrencyGraph.getShortestPath(
                currencyPair.getFirst(),
                currencyPair.getSecond()
        );

        var tickerList = new ArrayList<Ticker>();
        for (var instrumentEdge : instrumentEdgeList) {
            var responseBody = requestService.doGetRequest(
                    String.format(
                            "https://dsx.uk/mapi/ticker/%s",
                            instrumentEdge.toString()
                    ));
            var jsonObject = new JSONObject(responseBody);
            var tickerString = String.valueOf(jsonObject.get(fixBchProblem(instrumentEdge.toString())));
            var dsxTicker = objectMapper.readValue(tickerString, DsxTicker.class);
            tickerList.add(conversionService.convert(dsxTicker, Ticker.class));
        }

        var exchangeRate = new BigDecimal("1");
        for (int j = 0; j < tickerList.size(); j++) {
            if (instrumentEdgeList.get(j).isReversed()) {
                exchangeRate = exchangeRate.divide(
                        tickerList.get(j).getExchangeRate(),
                        10,
                        RoundingMode.HALF_UP
                );
            } else {
                exchangeRate = exchangeRate.multiply(tickerList.get(j).getExchangeRate());
            }
        }

        return new Ticker(exchangeRate);
    }

    private Pair<DsxCurrencyVertex, DsxCurrencyVertex> getCurrencyPairFromString(String instrument) {

        var currencyPair = Lists.newArrayList(Splitter.on("-").split(instrument));
        if (currencyPair.size() != 2) {
            throw new NotFoundException("Invalid instrument");
        }
        var baseCurrency = new DsxCurrencyVertex(currencyPair.get(0));
        var quotedCurrency = new DsxCurrencyVertex(currencyPair.get(1));
        if (!dsxSupportedCurrenciesRepository.getSupportedCurrencies().contains(baseCurrency)) {
            log.warn("Base currency {} from request is not supported by dsx", baseCurrency);
            throw new NotFoundException("Base currency from request is not supported by dsx");
        }
        if (!dsxSupportedCurrenciesRepository.getSupportedCurrencies().contains(quotedCurrency)) {
            log.warn("Quoted currency {} from request is not supported by dsx", quotedCurrency);
            throw new NotFoundException("Base currency from request is not supported by dsx");
        }

        return new Pair<>(baseCurrency, quotedCurrency);
    }

    private String fixBchProblem(String instrument) {
        return instrument.replace("bcc", "bch");
    }
}
