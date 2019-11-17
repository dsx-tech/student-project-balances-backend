package dsx.bcv.server.services;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVParserService {

    public List<Trade> parseTrades(Reader in) throws IOException {
        return parseTrades(in, ',');
    }

    public List<Trade> parseTrades(Reader in, char separator) throws IOException {

        List<Trade> trades = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(in);
        for (CSVRecord record : records) {
            trades.add(getTradeFromCSVRecord(record));
        }

        return trades;
    }

    private Trade getTradeFromCSVRecord(CSVRecord record) {

        final LocalDateTime dateTime = getDateTimeFromString(record.get(0));
        final String instrument = record.get(1);
        final String dealType = record.get(2);
        final BigDecimal tradedQuantity = new BigDecimal(record.get(3));
        final String tradedQuantityCurrency = record.get(4);
        final BigDecimal tradedPrice = new BigDecimal(record.get(5));
        final String tradedPriceCurrency = record.get(6);
        final BigDecimal commission = new BigDecimal(record.get(7));
        final String commissionCurrency = record.get(8);
        final String tradeValueId = record.get(9);

        return new Trade(dateTime, instrument, dealType, tradedQuantity, tradedQuantityCurrency,
                tradedPrice, tradedPriceCurrency, commission, commissionCurrency, tradeValueId);
    }

    public List<Transaction> parseTransactions(Reader in) throws IOException {
        return parseTransactions(in, ',');
    }

    public List<Transaction> parseTransactions(Reader in, char separator) throws IOException {

        List<Transaction> transactions = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(in);
        for (CSVRecord record : records) {
            transactions.add(getTransactionFromCSVRecord(record));
        }

        return transactions;
    }

    private Transaction getTransactionFromCSVRecord(CSVRecord record) {

        final LocalDateTime dateTime = getDateTimeFromString(record.get(0));
        final String transactionType = record.get(1);
        final String currency = record.get(2);
        final BigDecimal amount = new BigDecimal(record.get(3));
        final BigDecimal commission = new BigDecimal(record.get(4));
        final String transactionStatus = record.get(5);
        final String transactionValueId = record.get(6);

        return new Transaction(dateTime, transactionType, currency, amount, commission,
                transactionStatus, transactionValueId);
    }

    private LocalDateTime getDateTimeFromString(String string) {

        final String localDateTimeFormat = string.replace(' ', 'T');
        return LocalDateTime.parse(localDateTimeFormat);
    }
}
