package dsx.bcv.controllers;

import dsx.bcv.data.mocks.MockTrades;
import dsx.bcv.data.models.Trade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("trades")
public class TradeController {

    private final MockTrades tradeRepository;

    public TradeController() {
        this.tradeRepository = new MockTrades();
    }

    @RequestMapping("list")
    public List<Trade> getAllTrades(){
        return tradeRepository.getAllTrades();
    }
}
