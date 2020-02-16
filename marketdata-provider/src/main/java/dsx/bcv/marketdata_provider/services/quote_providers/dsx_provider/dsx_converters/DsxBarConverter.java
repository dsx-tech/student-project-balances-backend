package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxBar;
import org.springframework.stereotype.Service;

@Service
public class DsxBarConverter {

    public Bar convertDsxBarToBar(DsxBar dsxBar) {
        return new Bar(
                dsxBar.getClose(),
                dsxBar.getTimestamp()
        );
    }
}
