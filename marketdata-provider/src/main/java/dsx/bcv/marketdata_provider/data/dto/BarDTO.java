package dsx.bcv.marketdata_provider.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BarDTO {
    private BigDecimal exchangeRate;
    private long timestamp;
}
