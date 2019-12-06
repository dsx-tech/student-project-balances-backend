package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.models.Trade;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MockTradesTest {

    @Test
    public void add() {

        var mockTrades = MockTrades.instance;
        var trade = new Trade(LocalDateTime.now(), "BTCUSD", "Sell", new BigDecimal("0.00097134"), "BTC",
                new BigDecimal("10142.28001"), "USD", new BigDecimal("0.02"), "USD", "17387684");

        assertFalse(mockTrades.contains(trade));

        mockTrades.add(trade);

        assertTrue(mockTrades.contains(trade));
    }
}