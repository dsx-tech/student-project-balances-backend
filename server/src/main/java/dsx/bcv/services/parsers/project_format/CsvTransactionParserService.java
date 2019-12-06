package dsx.bcv.services.parsers.project_format;

import dsx.bcv.data.models.Transaction;
import dsx.bcv.services.parsers.CsvRecordToTransactionConverter;
import dsx.bcv.services.parsers.TransactionField;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvTransactionParserService {

    public List<Transaction> parseTransactions(Reader in, char separator) throws IOException {

        List<Transaction> transactions = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(in);
        for (CSVRecord record : records) {
            var transactionMap = Map.of(
                    TransactionField.DateTime, record.get(0),
                    TransactionField.TransactionType, record.get(1),
                    TransactionField.Currency, record.get(2),
                    TransactionField.Amount, record.get(3),
                    TransactionField.Commission, record.get(4),
                    TransactionField.TransactionStatus, record.get(5),
                    TransactionField.TransactionValueId, record.get(6)
            );
            transactions.add(CsvRecordToTransactionConverter.convert(transactionMap));
        }

        return transactions;
    }
}

