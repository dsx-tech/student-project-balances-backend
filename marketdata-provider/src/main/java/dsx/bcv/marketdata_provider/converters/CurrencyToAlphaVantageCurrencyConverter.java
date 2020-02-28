package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCurrency;
import org.springframework.core.convert.converter.Converter;

public class CurrencyToAlphaVantageCurrencyConverter
        implements Converter<Currency, AlphaVantageCurrency> {
    @Override
    public AlphaVantageCurrency convert(Currency source) {
        return new AlphaVantageCurrency(
                source.getCode(),
                source.getName()
        );
    }
}
