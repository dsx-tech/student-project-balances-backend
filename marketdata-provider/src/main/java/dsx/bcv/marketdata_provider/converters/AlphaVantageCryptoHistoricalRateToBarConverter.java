package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCryptoHistoricalRate;
import org.springframework.core.convert.converter.Converter;

public class AlphaVantageCryptoHistoricalRateToBarConverter
        implements Converter<AlphaVantageCryptoHistoricalRate, Bar> {
    @Override
    public Bar convert(AlphaVantageCryptoHistoricalRate source) {
        return new Bar(
                source.getExchangeRate(),
                source.getDate()
        );
    }
}
