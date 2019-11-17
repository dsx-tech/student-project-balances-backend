package dsx.bcv.utils.data.models;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class Instrument {
    @JsonCreator
    public Instrument(@JacksonInject String id,
                      @JsonProperty("decimal_places") byte decimalPlaces,
                      @JsonProperty("min_price")  BigDecimal minPrice,
                      @JsonProperty("max_price")  BigDecimal maxPrice,
                      @JsonProperty("min_amount")  BigDecimal minAmount,
                      @JsonProperty("hidden")  int hidden,
                      @JsonProperty("fee")  int fee,
                      @JsonProperty("amount_decimal_places") byte amountDecimalPlaces,
                      @JsonProperty("quoted_currency") String quotedCurrency,
                      @JsonProperty("base_currency")  String baseCurrency) {
        this.id = id;
        this.decimalPlaces = decimalPlaces;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minAmount = minAmount;
        this.hidden = hidden;
        this.fee = fee;
        this.amountDecimalPlaces = amountDecimalPlaces;
        this.quotedCurrency = quotedCurrency;
        this.baseCurrency = baseCurrency;
    }

    private final String id;
    private final byte decimalPlaces;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final BigDecimal minAmount;
    private final int hidden;
    private final int fee;
    private final byte amountDecimalPlaces;
    private final String quotedCurrency;
    private final String baseCurrency;
}
