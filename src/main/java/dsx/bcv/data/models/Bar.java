package dsx.bcv.data.models;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Bar {
    public Bar(BigDecimal high, BigDecimal amount, BigDecimal low,
               BigDecimal close, BigDecimal open, long timestamp) {
        this.high = high;
        this.amount = amount;
        this.low = low;
        this.close = close;
        this.open = open;
        this.timestamp = timestamp;
    }

    public BigDecimal getHigh() { return high; }

    public BigDecimal getAmount() { return amount; }

    public BigDecimal getLow() { return low; }

    public BigDecimal getClose() { return close; }

    public BigDecimal getOpen() { return open; }

    public long getTimestamp() { return timestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return high.equals(bar.high) &&
                amount.equals(bar.amount) &&
                low.equals(bar.low) &&
                close.equals(bar.close) &&
                open.equals(bar.open) &&
                (timestamp == bar.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(high.toString(),amount.toString(), low.toString(), close.toString(), open.toString(), timestamp);
    }

    @Override
    public String toString(){
        return high.toString() + "," + amount.toString() + "," +
                low.toString() + "," + close.toString() + "," +
                open.toString() + "," + timestamp;
    }

    private final BigDecimal high;
    private final BigDecimal amount;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal open;
    private final long timestamp;
}
