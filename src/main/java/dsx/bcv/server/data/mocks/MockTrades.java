package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.interfaces.ITradeRepository;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.TmpIdGeneratorService;
import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockTrades implements ITradeRepository {

    @Override
    public Trade add(Trade trade) {
        trade.setId(TmpIdGeneratorService.createID());
        trades.add(trade);
        return trade;
    }

    @Override
    public boolean contains(Trade trade) {
        return trades.contains(trade);
    }

    @Override
    public List<Trade> getAllTrades() {
        return trades;
    }

    @Override
    public Trade getById(long id) {
        return trades.stream()
                .filter(trade -> trade.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public Trade update(long id, Trade trade) {
        trade.setId(id);
        val tradeInList = getById(id);
        val index = trades.indexOf(tradeInList);
        trades.set(index, trade);
        return trade;
    }

    @Override
    public void delete(long id) {
        val tradeInList = getById(id);
        trades.remove(tradeInList);
    }

    private List<Trade> trades = new ArrayList<>(Arrays.asList(
            new Trade(LocalDateTime.now(), "BTCUSD", "Sell", new BigDecimal("0.00097134"), "BTC",
                    new BigDecimal("10142.28001"), "USD", new BigDecimal("0.02"), "USD", "37387684"),
            new Trade(LocalDateTime.now(), "ETHBTC", "Buy", new BigDecimal("0.001"), "ETH",
                    new BigDecimal("0.021"), "BTC", new BigDecimal("0.0000025"), "USD", "37381155")));
}
