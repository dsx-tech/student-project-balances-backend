package dsx.bcv.data.mocks;

import dsx.bcv.data.interfaces.ITradeRepository;
import dsx.bcv.data.models.Trade;
import dsx.bcv.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockTrades implements ITradeRepository {

    @Override
    public void add(Trade trade) {
        trades.add(trade);
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

    private List<Trade> trades = new ArrayList<>(Arrays.asList(
            new Trade(LocalDateTime.now(), "BTCUSD", "Sell", new BigDecimal("0.00097134"), "BTC",
                    new BigDecimal("10142.28001"), "USD", new BigDecimal("0.02"), "USD", "37387684"),
            new Trade(LocalDateTime.now(), "ETHBTC", "Buy", new BigDecimal("0.001"), "ETH",
                    new BigDecimal("0.021"), "BTC", new BigDecimal("0.0000025"), "USD", "37381155")));
}
