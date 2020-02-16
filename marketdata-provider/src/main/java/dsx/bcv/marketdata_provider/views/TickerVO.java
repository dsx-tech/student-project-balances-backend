package dsx.bcv.marketdata_provider.views;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TickerVO {
    private BigDecimal exchangeRate;
}
