package dsx.bcv.data.models;

import java.math.BigDecimal;
import java.util.Objects;

public class CurrencyPair {
    public CurrencyPair(byte amount_decimal_places, String base_currency, BigDecimal max_price,
                        BigDecimal min_price, int hidden, BigDecimal min_amount, int fee, String quoted_currency,
                        byte decimal_places) {
        this.amount_decimal_places = amount_decimal_places;
        this.base_currency = base_currency;
        this.max_price = max_price;
        this.min_price = min_price;
        this.hidden = hidden;
        this.min_amount = min_amount;
        this.fee = fee;
        this.quoted_currency = quoted_currency;
        this.decimal_places = decimal_places;
    }

    public byte getAmount_decimal_places() { return amount_decimal_places; }

    public String getBase_currency() { return base_currency; }

    public BigDecimal getMax_price() { return max_price; }

    public BigDecimal getMin_price() { return min_price; }

    public int getHidden() { return hidden; }

    public BigDecimal getMin_amount() { return min_amount; }

    public int getFee() { return fee; }

    public String getQuoted_currency() { return quoted_currency; }

    public byte getDecimal_places() { return decimal_places; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPair currencyPair = (CurrencyPair) o;
        return (amount_decimal_places == currencyPair.amount_decimal_places) &&
                base_currency.equals(currencyPair.base_currency) &&
                max_price.equals(currencyPair.max_price) &&
                min_price.equals(currencyPair.min_price) &&
                (hidden == currencyPair.hidden) &&
                min_amount.equals(currencyPair.min_amount) &&
                (fee == currencyPair.fee) &&
                quoted_currency.equals(currencyPair.quoted_currency) &&
                (decimal_places == currencyPair.decimal_places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount_decimal_places, base_currency, max_price.toString(),
                min_price.toString(),hidden, min_amount.toString(),
                fee, quoted_currency, decimal_places);
    }

    @Override
    public String toString(){
        return amount_decimal_places + "," + base_currency + "," +
                max_price.toString() + "," + min_price.toString() + "," +
                hidden + "," + min_amount.toString() + "," +
                fee + "," + quoted_currency + "," +
                decimal_places;
    }

    private final byte amount_decimal_places;
    private final String base_currency;
    private final BigDecimal max_price;
    private final BigDecimal min_price;
    private final int hidden;
    private final BigDecimal min_amount;
    private final int fee;
    private final String quoted_currency;
    private final byte decimal_places;
}
