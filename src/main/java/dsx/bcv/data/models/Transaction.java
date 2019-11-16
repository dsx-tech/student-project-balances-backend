package dsx.bcv.data.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Transaction {

    private long id;
    @NonNull
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
