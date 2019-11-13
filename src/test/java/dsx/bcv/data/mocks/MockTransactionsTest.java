package dsx.bcv.data.mocks;

import dsx.bcv.data.models.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MockTransactionsTest {

    @Test
    public void add() {

        var mockTransactions = new MockTransactions();
        var transaction = new Transaction(LocalDateTime.now(), "Deposit", "BTC", new BigDecimal("0.0052036"),
                new BigDecimal("0"), "Complete", new BigInteger("3692143"));

        assertFalse(mockTransactions.contains(transaction));

        mockTransactions.add(transaction);

        assertTrue(mockTransactions.contains(transaction));
    }
}