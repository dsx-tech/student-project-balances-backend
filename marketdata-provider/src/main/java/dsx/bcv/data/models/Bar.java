package dsx.bcv.data.models;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.Formatter;

@Data
@EqualsAndHashCode
public class Bar {
    private final String id;
    private final BigDecimal high;
    private final BigDecimal open;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal amount;
    private final long timestamp;

    @JsonCreator
    public Bar(@JacksonInject String id,
               @JsonProperty("high") BigDecimal high,
               @JsonProperty("open") BigDecimal open,
               @JsonProperty("low") BigDecimal low,
               @JsonProperty("close") BigDecimal close,
               @JsonProperty("amount") BigDecimal amount,
               @JsonProperty("timestamp") long timestamp) {
        this.id = id;
        this.high = high;
        this.open = open;
        this.low = low;
        this.close = close;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String toCsvRecord(Formatter formatter){
        return formatter.format("%s,%s,%s,%s,%s,%s,%d\n",
                this.id,
                this.high.toString(),
                this.open.toString(),
                this.low.toString(),
                this.close.toString(),
                this.amount.toString(),
                this.timestamp).toString();
    }
}
