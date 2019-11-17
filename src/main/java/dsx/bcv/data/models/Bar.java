package dsx.bcv.data.models;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class Bar {
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

    public String toCsvRecord(){
        StringBuilder record = new StringBuilder(this.toString());
        record.delete(0,record.indexOf("=") + 1);
        for (int i = 0; i < 6; ++i){
            record.delete(record.indexOf(" "),record.indexOf("=") + 1);
        }
        record.delete(record.length() - 1,record.length());
        record.append('\n');
        return record.toString();
    }

    private final String id;
    private final BigDecimal high;
    private final BigDecimal open;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal amount;
    private final long timestamp;
}
