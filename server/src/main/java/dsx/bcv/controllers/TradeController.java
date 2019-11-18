package dsx.bcv.controllers;

import dsx.bcv.data.mocks.MockTrades;
import dsx.bcv.data.models.Trade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trades")
public class TradeController {

    private MockTrades tradeRepository;

    public TradeController() {
        this.tradeRepository = new MockTrades();
    }

    @RequestMapping("list")
    public List<Trade> getAll(){
        return tradeRepository.getAll();
    }

    @GetMapping("{id}")
    public Trade getByID(@PathVariable long id){
        return tradeRepository.getById(id);
    }

    @PostMapping
    public Trade add(@RequestBody Trade trade){
        return tradeRepository.add(trade);
    }

    @PutMapping
    public Trade update(@PathVariable long id, @RequestBody Trade trade) {
        return tradeRepository.update(id, trade);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id){
        tradeRepository.delete(id);
    }
}
