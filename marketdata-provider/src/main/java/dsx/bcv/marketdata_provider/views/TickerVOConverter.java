package dsx.bcv.marketdata_provider.views;

import dsx.bcv.marketdata_provider.data.models.Ticker;
import org.springframework.stereotype.Service;

@Service
public class TickerVOConverter {

    public TickerVO convertTickerToTickerVO(Ticker ticker) {
        var tickerVO = new TickerVO();
        tickerVO.setExchangeRate(ticker.getExchangeRate());
        return tickerVO;
    }
}
