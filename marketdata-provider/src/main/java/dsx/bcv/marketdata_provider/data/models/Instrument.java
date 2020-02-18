package dsx.bcv.marketdata_provider.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Instrument {

    public String baseCurrency;
    public String quotedCurrency;

    @Override
    public String toString() {
        return baseCurrency + "-" + quotedCurrency;
    }
}
