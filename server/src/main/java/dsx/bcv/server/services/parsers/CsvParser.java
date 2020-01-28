package dsx.bcv.server.services.parsers;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.services.parsers.data_formats.IMarketplaceDataFormat;
import lombok.NonNull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvParser implements IParser {

    private IMarketplaceDataFormat marketplaceDataFormat;

    public CsvParser(@NonNull IMarketplaceDataFormat marketplaceDataFormat){

        this.marketplaceDataFormat = marketplaceDataFormat;
    }

    @Override
    public List<Trade> parseTrades(Reader inputReader, char separator) throws IOException {

        List<Trade> trades = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(inputReader);
        var tradeFormat = marketplaceDataFormat.getTradeFormat();
        for (CSVRecord record : records) {
            var tradeMap = Map.of(
                    TradeField.DateTime, record.get(tradeFormat.get(TradeField.DateTime)),
                    TradeField.Instrument, record.get(tradeFormat.get(TradeField.Instrument)),
                    TradeField.TradeType, record.get(tradeFormat.get(TradeField.TradeType)),
                    TradeField.TradedQuantity, record.get(tradeFormat.get(TradeField.TradedQuantity)),
                    TradeField.TradedQuantityCurrency, record.get(tradeFormat.get(TradeField.TradedQuantityCurrency)),
                    TradeField.TradedPrice, record.get(tradeFormat.get(TradeField.TradedPrice)),
                    TradeField.TradedPriceCurrency, record.get(tradeFormat.get(TradeField.TradedPriceCurrency)),
                    TradeField.Commission, record.get(tradeFormat.get(TradeField.Commission)),
                    TradeField.CommissionCurrency, record.get(tradeFormat.get(TradeField.CommissionCurrency)),
                    TradeField.TradeValueId, record.get(tradeFormat.get(TradeField.TradeValueId))
            );
            trades.add(MapToObjConverter.convertTrade(tradeMap));
        }

        return trades;
    }

    @Override
    public List<Transaction> parseTransactions(Reader inputReader, char separator) throws IOException {

        List<Transaction> transactions = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(inputReader);
        var transactionFormat = marketplaceDataFormat.getTransactionFormat();
        for (CSVRecord record : records) {
            var transactionMap = Map.of(
                    TransactionField.DateTime, record.get(transactionFormat.get(TransactionField.DateTime)),
                    TransactionField.TransactionType, record.get(transactionFormat.get(TransactionField.TransactionType)),
                    TransactionField.Currency, record.get(transactionFormat.get(TransactionField.Currency)),
                    TransactionField.Amount, record.get(transactionFormat.get(TransactionField.Amount)),
                    TransactionField.Commission, record.get(transactionFormat.get(TransactionField.Commission)),
                    TransactionField.TransactionStatus, record.get(transactionFormat.get(TransactionField.TransactionStatus)),
                    TransactionField.TransactionValueId, record.get(transactionFormat.get(TransactionField.TransactionValueId))
            );
            transactions.add(MapToObjConverter.convertTransaction(transactionMap));
        }

        return transactions;
    }
}
