package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Asset;
import dsx.bcv.marketdata_provider.data.models.Ticker;
import dsx.bcv.marketdata_provider.data.repositories.TickerRepository;
import org.springframework.stereotype.Service;

@Service
public class TickerService {

    private final TickerRepository tickerRepository;

    public TickerService(TickerRepository tickerRepository) {
        this.tickerRepository = tickerRepository;
    }

    public Ticker save(Ticker ticker) {
        return tickerRepository.save(ticker);
    }

    public Iterable<Ticker> saveAll(Iterable<Ticker> tickers) {
        return tickerRepository.saveAll(tickers);
    }

    public Ticker findByBaseAsset(Asset baseAsset) {
        return tickerRepository.findByBaseAsset(baseAsset);
    }

    public void deleteAll() {
        tickerRepository.deleteAll();
    }

    public long count() {
        return tickerRepository.count();
    }
}
