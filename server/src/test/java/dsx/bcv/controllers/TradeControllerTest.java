package dsx.bcv.controllers;

import dsx.bcv.data.mocks.MockTrades;
import dsx.bcv.data.models.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.runners.Parameterized.Parameter;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TradeControllerTest {

    @Mock
    private MockTrades tradeRepository;

    @InjectMocks
    private TradeController tradeController;

    @Test
    public void getAll() {

        var x = tradeController.getAll();

        var y = verify(tradeRepository).getAll();
    }

    @Parameter
    public int id;

    @Test
    public void getByID() {

        var x = tradeController.getByID(id);

        var y = verify(tradeRepository).getById(id);
    }

    @Parameter
    public Trade trade;

    @Test
    public void add() {

        var x = tradeController.add(trade);

        var y = verify(tradeRepository).add(trade);
    }

    @Test
    public void update() {

        var x = tradeController.update(id, trade);

        var y = verify(tradeRepository).update(id, trade);
    }

    @Test
    public void delete() {

        tradeController.delete(id);

        verify(tradeRepository).delete(id);
    }
}