package dsx.bcv.marketdata_provider.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bar {
    private BigDecimal exchangeRate;
    private LocalDate date;
}
