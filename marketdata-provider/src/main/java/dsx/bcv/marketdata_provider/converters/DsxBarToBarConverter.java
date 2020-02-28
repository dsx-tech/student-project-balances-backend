package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.models.DsxBar;
import org.springframework.core.convert.converter.Converter;

public class DsxBarToBarConverter implements Converter<DsxBar, Bar> {
    @Override
    public Bar convert(DsxBar dsxBar) {
        return new Bar(
                new Currency("usd"),//?
                dsxBar.getClose(),
                dsxBar.getTimestamp()
        );
    }
}
