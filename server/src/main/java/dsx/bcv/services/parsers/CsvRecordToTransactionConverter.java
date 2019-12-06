package dsx.bcv.services.parsers;

import dsx.bcv.data.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static dsx.bcv.services.parsers.DateFormatter.getDateTimeFromString;

public class CsvRecordToTransactionConverter {

    public static Transaction convert(Map<TransactionField, String> csvRecord) {

        final LocalDateTime dateTime = getDateTimeFromString(csvRecord.get(TransactionField.DateTime));
        final String transactionType = csvRecord.get(TransactionField.TransactionType);
        final String currency = csvRecord.get(TransactionField.Currency);
        final BigDecimal amount = new BigDecimal(csvRecord.get(TransactionField.Amount)
                .replaceAll(",", "."));
        final BigDecimal commission = new BigDecimal(csvRecord.get(TransactionField.Commission)
                .replaceAll(",", "."));
        final String transactionStatus = csvRecord.get(TransactionField.TransactionStatus);
        final String transactionValueId = csvRecord.get(TransactionField.TransactionValueId);

        return new Transaction(dateTime, transactionType, currency, amount, commission,
                transactionStatus, transactionValueId);
    }
}
