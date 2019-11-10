package dsx.bcv.data.interfaces;

import dsx.bcv.data.models.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository {

    void add(Transaction transaction);
    boolean contains(Transaction transaction);
    List<Transaction> getAllTransactions();
    
}
