package dsx.bcv.server.controllers;

import dsx.bcv.server.data.repositories.TransactionRepository;
import dsx.bcv.server.views.TransactionVO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.runners.Parameterized.Parameter;
import static org.mockito.Mockito.verify;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void getAll() {

        var x = transactionController.getAll();

        var y = verify(transactionRepository).findAll();
    }

    @Parameter
    public long id;

    @Test
    public void getByID() {

        var x = transactionController.getByID(id);

        var y = verify(transactionRepository).findById(id);
    }

    @Parameter
    public TransactionVO transactionVO;

    @Test
    public void add() {

        var x = transactionController.add(transactionVO);

        //var y = verify(transactionRepository).save(transaction);
    }

    @Test
    public void update() {

        var x = transactionController.update(id, transactionVO);

        //var y = verify(transactionRepository).save(transaction);
    }

    @Test
    public void delete() {

        transactionController.delete(id);

        verify(transactionRepository).deleteById(id);
    }
}