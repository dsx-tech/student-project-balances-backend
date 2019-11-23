package dsx.bcv.data.mocks;

import dsx.bcv.data.interfaces.ITransactionRepository;
import dsx.bcv.data.models.Transaction;
import dsx.bcv.exceptions.NotFoundException;
import dsx.bcv.services.TmpTradeIdGeneratorService;
import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockTransactions implements ITransactionRepository {

    @Override
    public Transaction add(Transaction transaction) {
        transaction.setId(TmpTradeIdGeneratorService.createID());
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public boolean contains(Transaction transaction) {
        return transactions.contains(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return transactions;
    }

    @Override
    public Transaction getById(long id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Transaction update(long id, Transaction transaction) {
        transaction.setId(id);
        val transactionInList = getById(id);
        val index = transactions.indexOf(transactionInList);
        transactions.set(index, transaction);
        return transaction;
    }

    @Override
    public void delete(long id) {
        var transactionInList = getById(id);
        transactions.remove(transactionInList);
    }

    private List<Transaction> transactions = new ArrayList<>(Arrays.asList(
            new Transaction(LocalDateTime.now(), "Deposit", "BTC", new BigDecimal("0.0052036"),
                    new BigDecimal("0"), "Complete", "3662143"),
            new Transaction(LocalDateTime.now(), "Withdraw", "USD", new BigDecimal("48.22"),
                    new BigDecimal("0"), "Complete", "3662142")));
}
