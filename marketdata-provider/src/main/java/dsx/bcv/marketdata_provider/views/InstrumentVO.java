package dsx.bcv.marketdata_provider.views;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@JsonSerialize(using = ToStringSerializer.class)
public class InstrumentVO {

    private String currencyPair;

    public InstrumentVO (String baseCurrency, String quotedCurrency) {
        currencyPair = baseCurrency + "-" + quotedCurrency;
    }

    @Override
    public String toString() {
        return currencyPair;
    }
}
