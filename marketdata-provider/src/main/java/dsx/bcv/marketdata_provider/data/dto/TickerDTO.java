package dsx.bcv.marketdata_provider.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TickerDTO {
    private BigDecimal exchangeRate;
}
