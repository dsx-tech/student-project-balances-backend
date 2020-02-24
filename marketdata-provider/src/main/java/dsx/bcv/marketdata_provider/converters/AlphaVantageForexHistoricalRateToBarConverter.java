package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageForexHistoricalRate;
import org.springframework.core.convert.converter.Converter;

public class AlphaVantageForexHistoricalRateToBarConverter
        implements Converter<AlphaVantageForexHistoricalRate, Bar> {
    @Override
    public Bar convert(AlphaVantageForexHistoricalRate source) {
        return new Bar(
                source.getExchangeRate(),
                source.getDate()
        );
    }
}
