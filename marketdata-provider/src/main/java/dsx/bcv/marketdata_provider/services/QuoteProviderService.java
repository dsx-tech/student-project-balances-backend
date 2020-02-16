package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.services.quote_providers.QuoteProvider;
import dsx.bcv.marketdata_provider.views.BarVO;
import dsx.bcv.marketdata_provider.views.TickerVO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteProviderService {

    private QuoteProvider quoteProvider;
    private ConversionService conversionService;

    public QuoteProviderService(QuoteProvider quoteProvider, ConversionService conversionService) {
        this.quoteProvider = quoteProvider;
        this.conversionService = conversionService;
    }

    public List<BarVO> getBarsInPeriod(String instrument, long startTime, long endTime) {
        var barList = quoteProvider.getBarsInPeriod(instrument, startTime, endTime);
        return barList.stream()
                .map(bar -> conversionService.convert(bar, BarVO.class)).collect(Collectors.toList());
    }

    public TickerVO getTicker(String instrument) {
        var ticker = quoteProvider.getTicker(instrument);
        return conversionService.convert(ticker, TickerVO.class);
    }
}
