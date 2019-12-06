package dsx.bcv.services.parsers.dsx;

import dsx.bcv.data.models.Trade;
import dsx.bcv.services.parsers.CsvRecordToTradeConverter;
import dsx.bcv.services.parsers.TradeField;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DsxCsvTradeParserService {

    public List<Trade> parseTrades(Reader in, char separator) throws IOException {

        List<Trade> trades = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(in);
        for (CSVRecord record : records) {
            var csvRecord = Map.of(
                    TradeField.DateTime, record.get(0),
                    TradeField.Instrument, record.get(1),
                    TradeField.TradeType, record.get(2),
                    TradeField.TradedQuantity, record.get(3),
                    TradeField.TradedQuantityCurrency, record.get(4),
                    TradeField.TradedPrice, record.get(5),
                    TradeField.TradedPriceCurrency, record.get(6),
                    TradeField.Commission, record.get(9),
                    TradeField.CommissionCurrency, record.get(10),
                    TradeField.TradeValueId, record.get(12)
            );
            trades.add(CsvRecordToTradeConverter.convert(csvRecord));
        }

        return trades;
    }
}
