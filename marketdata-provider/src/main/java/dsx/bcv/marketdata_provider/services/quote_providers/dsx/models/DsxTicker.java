package dsx.bcv.marketdata_provider.services.quote_providers.dsx.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DsxTicker {
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal avg;
    private BigDecimal vol;
    private BigDecimal vol_cur;
    private BigDecimal last;
    private BigDecimal buy;
    private BigDecimal sell;
    private long updated;
    private String pair;
}


