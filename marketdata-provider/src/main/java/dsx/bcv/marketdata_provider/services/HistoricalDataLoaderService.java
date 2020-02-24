package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.AlphaVantageQuoteProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HistoricalDataLoaderService {

    private final AlphaVantageQuoteProvider alphaVantageQuoteProvider;

    public HistoricalDataLoaderService(AlphaVantageQuoteProvider alphaVantageQuoteProvider) {

        this.alphaVantageQuoteProvider = alphaVantageQuoteProvider;

        new Thread(this::startLoading).start();
    }

    public void startLoading() {
        var sleepTime = 1000L;// * 12;
        while (true) {
            log.info("loading...");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }
}
