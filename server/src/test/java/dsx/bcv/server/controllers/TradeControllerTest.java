package dsx.bcv.server.controllers;

import dsx.bcv.server.data.repositories.TradeRepository;
import dsx.bcv.server.services.data_services.TradeService;
import dsx.bcv.server.views.TradeVO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.runners.Parameterized.Parameter;
import static org.mockito.Mockito.verify;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class TradeControllerTest {

    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private TradeService tradeService;
    @InjectMocks
    private TradeController tradeController;


    @Test
    public void getAll() {

        var x = tradeController.getAll();

        var y = verify(tradeRepository).findAll();
    }

    @Parameter
    public long id;

    @Test
    public void getByID() {

        var x = tradeController.getByID(id);

        var y = verify(tradeRepository).findById(id);
    }

    @Parameter
    public TradeVO tradeVO;

    @Test
    public void add() {

        var x = tradeController.add(tradeVO);

        //var y = verify(tradeRepository).save(trade);
    }

    @Test
    public void update() {

        var x = tradeController.update(id, tradeVO);

        //var y = verify(tradeRepository).save(trade);
    }

    @Test
    public void delete() {

        Mockito.when(tradeRepository.existsById(id)).thenReturn(true);

        tradeController.delete(id);

        verify(tradeRepository).deleteById(id);
    }
}