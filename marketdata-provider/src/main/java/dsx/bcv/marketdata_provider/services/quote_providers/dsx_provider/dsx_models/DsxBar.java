package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DsxBar {
    private BigDecimal high;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal amount;
    private long timestamp;
}
