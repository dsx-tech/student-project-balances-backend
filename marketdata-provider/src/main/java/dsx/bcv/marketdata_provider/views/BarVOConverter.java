package dsx.bcv.marketdata_provider.views;

import dsx.bcv.marketdata_provider.data.models.Bar;
import org.springframework.stereotype.Service;

@Service
public class BarVOConverter {

    public BarVO convertBarToBarVO(Bar bar) {
        var barVO = new BarVO();
        barVO.setExchangeRate(bar.getExchangeRate());
        barVO.setTimestamp(bar.getTimestamp());
        return barVO;
    }
}
