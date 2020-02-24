package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.services.quote_providers.dsx.DsxQuoteProvider;
import dsx.bcv.marketdata_provider.views.BarVO;
import dsx.bcv.marketdata_provider.views.InstrumentVO;
import dsx.bcv.marketdata_provider.views.TickerVO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteProviderService {

    private DsxQuoteProvider dsxQuoteProvider;
    private ConversionService conversionService;

    public QuoteProviderService(DsxQuoteProvider dsxQuoteProvider, ConversionService conversionService) {
        this.dsxQuoteProvider = dsxQuoteProvider;
        this.conversionService = conversionService;
    }

    public List<InstrumentVO> getInstruments() {
        return dsxQuoteProvider.getInstruments().stream()
                .map(instrument -> conversionService.convert(instrument, InstrumentVO.class))
                .collect(Collectors.toList());
    }

    public List<BarVO> getBarsInPeriod(String instrument, long startTime, long endTime) {
        var barList = dsxQuoteProvider.getBarsInPeriod(instrument, startTime, endTime);
        return barList.stream()
                .map(bar -> conversionService.convert(bar, BarVO.class)).collect(Collectors.toList());
    }

    public TickerVO getTicker(String instrument) {
        var ticker = dsxQuoteProvider.getTicker(instrument);
        return conversionService.convert(ticker, TickerVO.class);
    }
}
