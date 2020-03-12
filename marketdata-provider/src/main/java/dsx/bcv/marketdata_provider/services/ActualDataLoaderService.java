package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActualDataLoaderService {

    private final TickerService tickerService;
    private final BarService barService;
    private final AssetService assetService;

    public ActualDataLoaderService(
            TickerService tickerService,
            BarService barService,
            AssetService assetService
    ) {
        this.tickerService = tickerService;
        this.barService = barService;
        this.assetService = assetService;

        loadDataFromLastBars();
    }

    public void loadDataFromLastBars() {

        log.info("Loading tickers from last bars");
        if (tickerService.count() != 0) {
            log.info("Tickers are already saved");
            return;
        }

        final var currencies = assetService.findAll();
        for (var currency : currencies) {
            final var lastBar = barService.findTopByBaseCurrencyOrderByTimestampDesc(currency);
            final var ticker = new Ticker(
                    lastBar.getBaseAsset(),
                    lastBar.getExchangeRate(),
                    lastBar.getTimestamp()
            );
            tickerService.save(ticker);
            log.debug(
                    "Ticker for {} saved. Rate: {}. Timestamp: {}",
                    currency,
                    ticker.getExchangeRate(),
                    ticker.getTimestamp()
            );
        }
        log.info("Loading tickers from last bars is complete");
    }
}
