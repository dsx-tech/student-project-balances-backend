package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Ticker;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxTicker;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DsxTickerToTickerConverter implements Converter<DsxTicker, Ticker> {
    @Override
    public Ticker convert(DsxTicker dsxTicker) {
        return new Ticker(
                dsxTicker.getBuy()
                        .add(dsxTicker.getSell())
                        .divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP)
        );
    }
}
