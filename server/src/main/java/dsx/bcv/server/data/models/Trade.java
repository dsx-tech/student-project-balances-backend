package dsx.bcv.server.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "trades")
@Data
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Instrument instrument;
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    private BigDecimal tradedQuantity;
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency tradedQuantityCurrency;
    private BigDecimal tradedPrice;
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency tradedPriceCurrency;
    private BigDecimal commission;
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency commissionCurrency;
    private String tradeValueId;

    public Trade(
            LocalDateTime dateTime,
            Instrument instrument,
            TradeType tradeType,
            BigDecimal tradedQuantity,
            Currency tradedQuantityCurrency,
            BigDecimal tradedPrice,
            Currency tradedPriceCurrency,
            BigDecimal commission,
            Currency commissionCurrency,
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
