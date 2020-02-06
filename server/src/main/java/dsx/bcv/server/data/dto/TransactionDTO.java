package dsx.bcv.server.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private LocalDateTime dateTime;
    private String transactionType;
    private String currency;
    private BigDecimal amount;
    private BigDecimal commission;
    private String transactionStatus;
    private String transactionValueId;
}
