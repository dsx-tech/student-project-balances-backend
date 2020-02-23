package dsx.bcv.marketdata_provider.views;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BarVO {
    private BigDecimal exchangeRate;
    private LocalDate timestamp;
}
