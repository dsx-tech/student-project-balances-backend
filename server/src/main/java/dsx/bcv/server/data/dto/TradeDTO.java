package dsx.bcv.server.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeDTO {
    private LocalDateTime dateTime;
    private String instrument;
    private String tradeType;
    private BigDecimal tradedQuantity;
    private String tradedQuantityCurrency;
    private BigDecimal tradedPrice;
    private String tradedPriceCurrency;
    private BigDecimal commission;
    private String commissionCurrency;
    private String tradeValueId;
}
