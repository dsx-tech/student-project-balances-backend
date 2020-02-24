package dsx.bcv.marketdata_provider.services.quote_providers.dsx.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DsxBar {
    private BigDecimal high;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal amount;
    private long timestamp;
}
