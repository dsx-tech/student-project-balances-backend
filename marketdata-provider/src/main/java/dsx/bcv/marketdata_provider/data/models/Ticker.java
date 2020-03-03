package dsx.bcv.marketdata_provider.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "tickers")
@Data
@NoArgsConstructor
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Currency baseCurrency;
    @Column(precision = 20, scale = 10)
    private BigDecimal exchangeRate;
    private long timestamp;

    public Ticker(Currency baseCurrency, BigDecimal exchangeRate, long timestamp) {
        this.baseCurrency = baseCurrency;
        this.exchangeRate = exchangeRate;
        this.timestamp = timestamp;
    }
}
