package dsx.bcv.data.mocks;

import dsx.bcv.data.interfaces.ITransactionRepository;
import dsx.bcv.data.models.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockTransactions implements ITransactionRepository {

    @Override
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public boolean contains(Transaction transaction) {
        return transactions.contains(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    private List<Transaction> transactions = new ArrayList<>(Arrays.asList(
            new Transaction(LocalDateTime.now(), "Deposit", "BTC", new BigDecimal("0.0052036"),
                    new BigDecimal("0"), "Complete", new BigInteger("3662143")),
            new Transaction(LocalDateTime.now(), "Withdraw", "USD", new BigDecimal("48.22"),
                    new BigDecimal("0"), "Complete", new BigInteger("3662142"))));
}
