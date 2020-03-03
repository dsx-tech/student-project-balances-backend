package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.views.CurrencyVO;
import org.springframework.core.convert.converter.Converter;

public class CurrencyToCurrencyVOConverter implements Converter<Currency, CurrencyVO> {
    @Override
    public CurrencyVO convert(Currency source) {
        return new CurrencyVO(
                source.getCode(),
                source.getName()
        );
    }
}
