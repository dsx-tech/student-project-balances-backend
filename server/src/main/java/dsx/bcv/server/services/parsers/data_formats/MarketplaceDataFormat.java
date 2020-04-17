package dsx.bcv.server.services.parsers.data_formats;

import dsx.bcv.server.services.parsers.TradeField;
import dsx.bcv.server.services.parsers.TransactionField;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface MarketplaceDataFormat {
    Map<TradeField, Integer> getTradeFormat();
    Map<TransactionField, Integer> getTransactionFormat();
}
