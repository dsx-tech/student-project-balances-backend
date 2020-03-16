package dsx.bcv.server.views;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dsx.bcv.server.data.models.TradeType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeVO {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @JsonSerialize(using = ToStringSerializer.class)
    private InstrumentVO instrument;
    private TradeType tradeType;
    private BigDecimal tradedQuantity;
    @JsonSerialize(using = ToStringSerializer.class)
    private CurrencyVO tradedQuantityCurrency;
    private BigDecimal tradedPrice;
    @JsonSerialize(using = ToStringSerializer.class)
    private CurrencyVO tradedPriceCurrency;
    private BigDecimal commission;
    @JsonSerialize(using = ToStringSerializer.class)
    private CurrencyVO commissionCurrency;
    private String tradeValueId;

    public TradeVO(
            LocalDateTime dateTime,
            InstrumentVO instrument,
            TradeType tradeType,
            BigDecimal tradedQuantity,
            CurrencyVO tradedQuantityCurrency,
            BigDecimal tradedPrice,
            CurrencyVO tradedPriceCurrency,
            BigDecimal commission,
            CurrencyVO commissionCurrency,
            String tradeValueId
    ) {
        this.dateTime = dateTime;
        this.instrument = instrument;
        this.tradeType = tradeType;
        this.tradedQuantity = tradedQuantity;
        this.tradedQuantityCurrency = tradedQuantityCurrency;
        this.tradedPrice = tradedPrice;
        this.tradedPriceCurrency = tradedPriceCurrency;
        this.commission = commission;
        this.commissionCurrency = commissionCurrency;
        this.tradeValueId = tradeValueId;
    }
}
