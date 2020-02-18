package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.views.BarVO;
import org.springframework.core.convert.converter.Converter;

public class BarToBarVOConverter implements Converter<Bar, BarVO> {
    @Override
    public BarVO convert(Bar bar) {
        var barVO = new BarVO();
        barVO.setExchangeRate(bar.getExchangeRate());
        barVO.setTimestamp(bar.getTimestamp());
        return barVO;
    }
}
