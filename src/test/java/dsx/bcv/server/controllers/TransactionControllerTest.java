package dsx.bcv.server.controllers;

import dsx.bcv.server.data.interfaces.ITransactionRepository;
import dsx.bcv.server.data.models.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.runners.Parameterized.Parameter;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void getAll() {

        var x = transactionController.getAll();

        var y = verify(transactionRepository).getAll();
    }

    @Parameter
    public int id;

    @Test
    public void getByID() {

        var x = transactionController.getByID(id);

        var y = verify(transactionRepository).getById(id);
    }

    @Parameter
    public Transaction transaction;

    @Test
    public void add() {

        var x = transactionController.add(transaction);

        var y = verify(transactionRepository).add(transaction);
    }

    @Test
    public void update() {

        var x = transactionController.update(id, transaction);

        var y = verify(transactionRepository).update(id, transaction);
    }

    @Test
    public void delete() {

        transactionController.delete(id);

        verify(transactionRepository).delete(id);
    }
}