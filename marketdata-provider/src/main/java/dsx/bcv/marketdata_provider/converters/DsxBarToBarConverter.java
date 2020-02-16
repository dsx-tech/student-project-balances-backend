package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxBar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class DsxBarToBarConverter implements Converter<DsxBar, Bar> {
    @Override
    public Bar convert(DsxBar dsxBar) {
        return new Bar(
                dsxBar.getClose(),
                dsxBar.getTimestamp()
        );
    }
}
