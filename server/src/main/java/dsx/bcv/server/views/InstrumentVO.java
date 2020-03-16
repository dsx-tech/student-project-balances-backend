package dsx.bcv.server.views;

import lombok.Data;

@Data
public class InstrumentVO {

    private CurrencyVO baseCurrency;
    private CurrencyVO quotedCurrency;

    public InstrumentVO(CurrencyVO baseCurrency, CurrencyVO quotedCurrency) {
        this.baseCurrency = baseCurrency;
        this.quotedCurrency = quotedCurrency;
    }

    @Override
    public String toString() {
        return baseCurrency + "-" + quotedCurrency;
    }
}
