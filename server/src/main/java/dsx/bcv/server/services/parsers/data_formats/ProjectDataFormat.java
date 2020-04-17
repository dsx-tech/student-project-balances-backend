package dsx.bcv.server.services.parsers.data_formats;

import dsx.bcv.server.services.parsers.TradeField;
import dsx.bcv.server.services.parsers.TransactionField;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProjectDataFormat implements MarketplaceDataFormat {

    private final Map<TradeField, Integer> tradeFormat = Map.of(
            TradeField.DateTime, 0,
            TradeField.Instrument, 1,
            TradeField.TradeType, 2,
            TradeField.TradedQuantity, 3,
            TradeField.TradedQuantityCurrency, 4,
            TradeField.TradedPrice, 5,
            TradeField.TradedPriceCurrency, 6,
            TradeField.Commission, 7,
            TradeField.CommissionCurrency, 8,
            TradeField.TradeValueId, 9
    );

    private final Map<TransactionField, Integer> transactionFormat = Map.of(
            TransactionField.DateTime, 0,
            TransactionField.TransactionType, 1,
            TransactionField.Currency, 2,
            TransactionField.Amount, 3,
            TransactionField.Commission, 4,
            TransactionField.TransactionStatus, 5,
            TransactionField.TransactionValueId, 6
    );

    @Override
    public Map<TradeField, Integer> getTradeFormat() {
        return tradeFormat;
    }

    @Override
    public Map<TransactionField, Integer> getTransactionFormat() {
        return transactionFormat;
    }
}
