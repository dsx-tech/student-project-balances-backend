package dsx.bcv.server.services.parsers.data_formats;

import dsx.bcv.server.services.parsers.TradeField;
import dsx.bcv.server.services.parsers.TransactionField;

import java.util.Map;

public interface IMarketplaceDataFormat {

    Map<TradeField, Integer> getTradeFormat();
    Map<TransactionField, Integer> getTransactionFormat();
}
