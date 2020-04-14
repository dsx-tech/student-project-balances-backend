package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void loadDataFromLastBars() {

        log.info("Loading tickers from last bars");

        final var assets = assetService.findAll();
        for (var asset : assets) {
            final var lastBar = barService.findTopByBaseAssetOrderByTimestampDesc(asset);
            final var ticker = new Ticker(
                    lastBar.getBaseAsset(),
                    lastBar.getExchangeRate(),
                    lastBar.getTimestamp()
            );
            if (tickerService.existsByBaseAsset(ticker.getBaseAsset())) {
                tickerService.updateExchangeRateAndTimestampByBaseAsset(
                        ticker.getExchangeRate(),
                        ticker.getTimestamp(),
                        ticker.getBaseAsset()
                );
            } else {
                tickerService.save(ticker);
            }
            log.debug(
                    "Ticker for {} saved. Rate: {}. Timestamp: {}",
                    asset,
                    ticker.getExchangeRate(),
                    ticker.getTimestamp()
            );
        }
        log.info("Loading tickers from last bars is complete");
    }
}
