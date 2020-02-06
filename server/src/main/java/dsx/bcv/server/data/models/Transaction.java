package dsx.bcv.server.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @NonNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @NonNull
    @JsonSerialize(using = ToStringSerializer.class)
    @ManyToOne
    private Currency currency;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private BigDecimal commission;
    @NonNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @NonNull
    private String transactionValueId;
}