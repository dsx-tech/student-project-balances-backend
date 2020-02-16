package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_converters;

import dsx.bcv.marketdata_provider.data.models.Ticker;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxTicker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DsxTickerConverter {

    public Ticker convertDsxTickerToTicker(DsxTicker dsxTicker) {
        return new Ticker(
                dsxTicker.getBuy()
                        .add(dsxTicker.getSell())
                        .divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP)
        );
    }
}
