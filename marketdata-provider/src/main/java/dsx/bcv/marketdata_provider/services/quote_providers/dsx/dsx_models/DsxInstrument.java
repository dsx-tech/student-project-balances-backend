package dsx.bcv.marketdata_provider.services.quote_providers.dsx.dsx_models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.currency_graph.DsxCurrencyVertex;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"baseCurrency", "quotedCurrency"})
public class DsxInstrument {

    private byte decimalPlaces;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minAmount;
    private int hidden;
    private int fee;
    private byte amountDecimalPlaces;
    private DsxCurrencyVertex quotedCurrency;
    private DsxCurrencyVertex baseCurrency;

    @JsonCreator
    public DsxInstrument(
            @JsonProperty("decimal_places") byte decimalPlaces,
            @JsonProperty("min_price") BigDecimal minPrice,
            @JsonProperty("max_price") BigDecimal maxPrice,
            @JsonProperty("min_amount") BigDecimal minAmount,
            @JsonProperty("hidden") int hidden,
            @JsonProperty("fee") int fee,
            @JsonProperty("amount_decimal_places") byte amountDecimalPlaces,
            @JsonProperty("quoted_currency") String quotedCurrency,
            @JsonProperty("base_currency") String baseCurrency
    ) {
        this.decimalPlaces = decimalPlaces;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minAmount = minAmount;
        this.hidden = hidden;
        this.fee = fee;
        this.amountDecimalPlaces = amountDecimalPlaces;
        this.quotedCurrency = new DsxCurrencyVertex(quotedCurrency.toLowerCase());
        this.baseCurrency = new DsxCurrencyVertex(baseCurrency.toLowerCase());
    }
}
