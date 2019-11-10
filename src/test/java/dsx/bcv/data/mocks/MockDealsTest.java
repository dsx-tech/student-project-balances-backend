package dsx.bcv.data.mocks;

import dsx.bcv.data.models.Deal;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MockDealsTest {

    @Test
    public void add() {

        var mockDeals = new MockDeals();
        var deal = new Deal(LocalDateTime.now(), "BTCUSD", "Sell", new BigDecimal("0.00097134"), "BTC",
                new BigDecimal("10142.28001"), "USD", new BigDecimal("0.02"), "USD", new BigInteger("17387684"));

        assertFalse(mockDeals.contains(deal));

        mockDeals.add(deal);

        assertTrue(mockDeals.contains(deal));
    }
}