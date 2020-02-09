package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.dto.BarDTO;
import dsx.bcv.marketdata_provider.data.dto.TickerDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotesProviderService {

    private BarService barService;
    private TickerService tickerService;

    public QuotesProviderService(BarService barService, TickerService tickerService) {
        this.barService = barService;
        this.tickerService = tickerService;
    }

    public List<BarDTO> getBarsInPeriod(String instrument, long startTime, long endTime) throws IOException {
        var lastBars = barService.getLastBars(instrument, 2000);
        return lastBars.stream()
                .filter(barDTO -> barDTO.getTimestamp() >= startTime && barDTO.getTimestamp() <= endTime)
                .collect(Collectors.toList());
    }

    public TickerDTO getTicker(String instrument) throws IOException {
        return tickerService.getTicker(instrument);
    }


}
