package dsx.bcv.data.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Trade {

    private long id;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private String instrument;
    @NonNull
    private String tradeType;
    @NonNull
    private BigDecimal tradedQuantity;
    @NonNull
    private String tradedQuantityCurrency;
    @NonNull
    private BigDecimal tradedPrice;
    @NonNull
    private String tradedPriceCurrency;
    @NonNull
    private BigDecimal commission;
    @NonNull
    private String commissionCurrency;
    @NonNull
    private String tradeValueId;
}
