package dsx.bcv.marketdata_provider.data.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Bar {
    private BigDecimal high;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal amount;
    private long timestamp;
}
