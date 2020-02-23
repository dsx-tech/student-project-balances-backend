package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.dsx_models.DsxBar;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DsxBarToBarConverter implements Converter<DsxBar, Bar> {
    @Override
    public Bar convert(DsxBar dsxBar) {
        return new Bar(
                dsxBar.getClose(),
                LocalDate.ofInstant(new Date(dsxBar.getTimestamp()).toInstant(), ZoneId.systemDefault())
        );
    }
}
