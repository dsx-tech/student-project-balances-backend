package dsx.bcv.server.services.parsers;

import dsx.bcv.server.data.models.Trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class CsvRecordToTradeConverter {

    public static Trade convert(Map<TradeField, String> csvRecord) {

        final LocalDateTime dateTime = DateFormatter.getDateTimeFromString(csvRecord.get(TradeField.DateTime));
        final String instrument = csvRecord.get(TradeField.Instrument);
        final String tradeType = csvRecord.get(TradeField.TradeType);
        final BigDecimal tradedQuantity = new BigDecimal(csvRecord.get(TradeField.TradedQuantity)
                .replaceAll(",", "."));
        final String tradedQuantityCurrency = csvRecord.get(TradeField.TradedQuantityCurrency);
        final BigDecimal tradedPrice = new BigDecimal(csvRecord.get(TradeField.TradedPrice)
                .replaceAll(",", "."));
        final String tradedPriceCurrency = csvRecord.get(TradeField.TradedPriceCurrency);
        final BigDecimal commission = new BigDecimal(csvRecord.get(TradeField.Commission)
                .replaceAll(",", "."));
        final String commissionCurrency = csvRecord.get(TradeField.CommissionCurrency);
        final String tradeValueId = csvRecord.get(TradeField.TradeValueId);

        return new Trade(dateTime, instrument, tradeType, tradedQuantity, tradedQuantityCurrency,
                tradedPrice, tradedPriceCurrency, commission, commissionCurrency, tradeValueId);
    }
}
