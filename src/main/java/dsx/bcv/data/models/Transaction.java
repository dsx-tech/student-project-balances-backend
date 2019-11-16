package dsx.bcv.data.models;

import dsx.bcv.services.TmpIdGeneratorService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Transaction {

    private long id = TmpIdGeneratorService.createID();
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
