package dsx.bcv.marketdata_provider.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class Tiker {

    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal avg;
    private final BigDecimal vol;
    private final BigDecimal volCur;
    private final BigDecimal last;
    private final BigDecimal buy;
    private final BigDecimal sell;
    private final long updated;
    private final String pair;

    public Tiker(@JsonProperty("high") BigDecimal high,
                 @JsonProperty("low") BigDecimal low,
                 @JsonProperty("avg") BigDecimal avg,
                 @JsonProperty("vol") BigDecimal vol,
                 @JsonProperty("vol_cur") BigDecimal volCur,
                 @JsonProperty("last") BigDecimal last,
                 @JsonProperty("buy") BigDecimal buy,
                 @JsonProperty("sell") BigDecimal sell,
                 @JsonProperty("updated") long updated,
                 @JsonProperty("pair") String pair) {
        this.high = high;
        this.low = low;
        this.avg = avg;
        this.vol = vol;
        this.volCur = volCur;
        this.last = last;
        this.buy = buy;
        this.sell = sell;
        this.updated = updated;
        this.pair = pair;
    }
}


