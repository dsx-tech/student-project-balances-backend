package dsx.bcv.marketdata_provider.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "bars")
@Data
@NoArgsConstructor
public class Bar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Asset baseAsset;
    @Column(precision = 20, scale = 10)
    private BigDecimal exchangeRate;
    private long timestamp;

    public Bar(Asset baseAsset, BigDecimal exchangeRate, long timestamp) {
        this.baseAsset = baseAsset;
        this.exchangeRate = exchangeRate;
        this.timestamp = timestamp;
    }
}
