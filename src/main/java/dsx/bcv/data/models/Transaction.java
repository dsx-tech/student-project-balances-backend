package dsx.bcv.data.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    public Transaction(LocalDateTime dateTime, String transactionType,
                       String currency, BigDecimal amount, BigDecimal commission,
                       String transactionStatus, long transactionValueId) {
            this.dateTime = dateTime;
            this.transactionType = transactionType;
            this.currency = currency;
            this.amount = amount;
            this.commission = commission;
            this.transactionStatus = transactionStatus;
            this.transactionValueId = transactionValueId;
        }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public long getTransactionValueId() {
        return transactionValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionValueId == that.transactionValueId &&
                dateTime.equals(that.dateTime) &&
                transactionType.equals(that.transactionType) &&
                currency.equals(that.currency) &&
                amount.equals(that.amount) &&
                commission.equals(that.commission) &&
                transactionStatus.equals(that.transactionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, transactionType, currency, amount, commission, transactionStatus, transactionValueId);
    }

    private final LocalDateTime dateTime;
    private final String transactionType;
    private final String currency;
    private final BigDecimal amount;
    private final BigDecimal commission;
    private final String transactionStatus;
    private final long transactionValueId;
}
