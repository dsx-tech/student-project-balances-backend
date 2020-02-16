package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.services.quote_providers.QuoteProvider;
import dsx.bcv.marketdata_provider.views.BarVO;
import dsx.bcv.marketdata_provider.views.BarVOConverter;
import dsx.bcv.marketdata_provider.views.TickerVO;
import dsx.bcv.marketdata_provider.views.TickerVOConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteProviderService {

    private QuoteProvider quoteProvider;
    private BarVOConverter barVOConverter;
    private TickerVOConverter tickerVOConverter;

    public QuoteProviderService(QuoteProvider quoteProvider, BarVOConverter barVOConverter, TickerVOConverter tickerVOConverter) {
        this.quoteProvider = quoteProvider;
        this.barVOConverter = barVOConverter;
        this.tickerVOConverter = tickerVOConverter;
    }

    public List<BarVO> getBarsInPeriod(String instrument, long startTime, long endTime) {
        var barList = quoteProvider.getBarsInPeriod(instrument, startTime, endTime);
        return barList.stream()
                .map(barVOConverter::convertBarToBarVO).collect(Collectors.toList());
    }

    public TickerVO getTicker(String instrument) {
        var ticker = quoteProvider.getTicker(instrument);
        return tickerVOConverter.convertTickerToTickerVO(ticker);
    }
}
