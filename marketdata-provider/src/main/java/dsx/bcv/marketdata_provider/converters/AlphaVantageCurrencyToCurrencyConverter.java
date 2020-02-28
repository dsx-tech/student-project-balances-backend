package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCurrency;
import org.springframework.core.convert.converter.Converter;

public class AlphaVantageCurrencyToCurrencyConverter
        implements Converter<AlphaVantageCurrency, Currency> {
    @Override
    public Currency convert(AlphaVantageCurrency source) {
        return new Currency(
                source.getCode(),
                source.getName()
        );
    }
}
