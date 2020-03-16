package dsx.bcv.server.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency currency;
    private BigDecimal amount;
    private BigDecimal commission;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String transactionValueId;

    public Transaction(
            LocalDateTime dateTime,
            TransactionType transactionType,
            Currency currency,
            BigDecimal amount,
            BigDecimal commission,
            TransactionStatus transactionStatus,
            String transactionValueId
    ) {
        this.dateTime = dateTime;
        this.transactionType = transactionType;
        this.currency = currency;
        this.amount = amount;
        this.commission = commission;
        this.transactionStatus = transactionStatus;
        this.transactionValueId = transactionValueId;
    }
}
