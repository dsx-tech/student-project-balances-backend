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
public class Transaction {

    private long id = TmpTransactionIdGeneratorService.createID();
    @NonNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @NonNull
    private String transactionType;
    @NonNull
    private String currency;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private BigDecimal commission;
    @NonNull
    private String transactionStatus;
    @NonNull
    private String transactionValueId;
}

class TmpTransactionIdGeneratorService {
    private static AtomicLong idCounter = new AtomicLong();

    static long createID()
    {
        return idCounter.getAndIncrement();
    }
}