package dsx.bcv.server.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Data
@RequiredArgsConstructor
public class Trade {

    private long id = TmpTradeIdGeneratorService.createID();
    @NonNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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

class TmpTradeIdGeneratorService {
    private static AtomicLong idCounter = new AtomicLong();

    static long createID()
    {
        return idCounter.getAndIncrement();
    }
}
