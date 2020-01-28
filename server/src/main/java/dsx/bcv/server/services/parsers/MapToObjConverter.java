package dsx.bcv.server.services.parsers;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.Transaction;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class MapToObjConverter {

    public static Trade convertTrade(Map<TradeField, String> tradeMap) {

        final LocalDateTime dateTime = getDateTimeFromString(tradeMap.get(TradeField.DateTime));
        final String instrument = tradeMap.get(TradeField.Instrument);
        final String tradeType = tradeMap.get(TradeField.TradeType);
        final BigDecimal tradedQuantity = new BigDecimal(tradeMap.get(TradeField.TradedQuantity)
                .replaceAll(",", "."));
        final String tradedQuantityCurrency = tradeMap.get(TradeField.TradedQuantityCurrency);
        final BigDecimal tradedPrice = new BigDecimal(tradeMap.get(TradeField.TradedPrice)
                .replaceAll(",", "."));
        final String tradedPriceCurrency = tradeMap.get(TradeField.TradedPriceCurrency);
        final BigDecimal commission = new BigDecimal(tradeMap.get(TradeField.Commission)
                .replaceAll(",", "."));
        final String commissionCurrency = tradeMap.get(TradeField.CommissionCurrency);
        final String tradeValueId = tradeMap.get(TradeField.TradeValueId);

        return new Trade(dateTime, instrument, tradeType, tradedQuantity, tradedQuantityCurrency,
                tradedPrice, tradedPriceCurrency, commission, commissionCurrency, tradeValueId);
    }

    public static Transaction convertTransaction(Map<TransactionField, String> transactionMap) {

        final LocalDateTime dateTime = getDateTimeFromString(transactionMap.get(TransactionField.DateTime));
        final String transactionType = transactionMap.get(TransactionField.TransactionType);
        final String currency = transactionMap.get(TransactionField.Currency);
        final BigDecimal amount = new BigDecimal(transactionMap.get(TransactionField.Amount)
                .replaceAll(",", "."));
        final BigDecimal commission = new BigDecimal(transactionMap.get(TransactionField.Commission)
                .replaceAll(",", "."));
        final String transactionStatus = transactionMap.get(TransactionField.TransactionStatus);
        final String transactionValueId = transactionMap.get(TransactionField.TransactionValueId);

        return new Transaction(dateTime, transactionType, currency, amount, commission,
                transactionStatus, transactionValueId);
    }

    private static LocalDateTime getDateTimeFromString(@NonNull String dateString) {

        var spacePos = dateString.indexOf(' ');
        if (dateString.charAt(spacePos + 2) == ':') {
            dateString = dateString.substring(0, spacePos + 1) + '0' + dateString.substring(spacePos + 1);
        }

        final String localDateTimeFormat = dateString.replace(' ', 'T');
        return LocalDateTime.parse(localDateTimeFormat);
    }
}
