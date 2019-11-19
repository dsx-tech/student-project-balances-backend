package dsx.bcv.data.interfaces;

import dsx.bcv.data.models.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository {

    Transaction add(Transaction transaction);
    boolean contains(Transaction transaction);
    List<Transaction> getAll();
    Transaction getById(long id);
    Transaction update(long id, Transaction transaction);
    void delete(long id);
}
