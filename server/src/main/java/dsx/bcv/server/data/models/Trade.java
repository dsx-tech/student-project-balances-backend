package dsx.bcv.server.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "trades")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Instrument instrument;
    @NonNull
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    @NonNull
    private BigDecimal tradedQuantity;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency tradedQuantityCurrency;
    @NonNull
    private BigDecimal tradedPrice;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency tradedPriceCurrency;
    @NonNull
    private BigDecimal commission;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency commissionCurrency;
    @NonNull
    private String tradeValueId;
}