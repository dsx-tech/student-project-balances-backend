package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.interfaces.ITransactionRepository;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.parsers.dsx.DsxCsvTransactionParserService;
import lombok.val;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MockTransactions implements ITransactionRepository {

    public static final MockTransactions instance = new MockTransactions();

    @Override
    public Transaction add(Transaction transaction) {
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

    private List<Transaction> transactions;

    private MockTransactions() {

        var classLoader = this.getClass().getClassLoader();
        var inputStream = classLoader.getResourceAsStream("dsx_transactions.csv");
        assert inputStream != null;
        var inputStreamReader = new InputStreamReader(inputStream);

        try {
            transactions = new DsxCsvTransactionParserService().parseTransactions(
                    inputStreamReader, ';');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
