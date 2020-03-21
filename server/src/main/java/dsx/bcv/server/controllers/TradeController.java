package dsx.bcv.server.controllers;

import dsx.bcv.server.data.dto.TradeDTO;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.repositories.TradeRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.TradeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trades")
public class TradeController {

    private final TradeRepository tradeRepository;
    private final TradeService tradeService;

    public TradeController(TradeRepository tradeRepository, TradeService tradeService) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
    }

    @GetMapping
    public Iterable<Trade> getAll() {
        return tradeRepository.findAll();
    }

    @GetMapping("{id}")
    public Trade getByID(@PathVariable long id) {
        var tradeOptional = tradeRepository.findById(id);
        if (tradeOptional.isPresent())
            return tradeOptional.get();
        else
            throw new NotFoundException();
    }

    @PostMapping
    public Trade add(@RequestBody TradeDTO tradeDTO) {
        var trade = tradeService.getTrade(tradeDTO);
        return tradeService.save(trade);
    }

    @PutMapping("{id}")
    public Trade update(@PathVariable long id, @RequestBody TradeDTO tradeDTO) {
        tradeRepository.deleteById(id);
        var trade = tradeService.getTrade(tradeDTO);
        return tradeService.save(trade);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        if (tradeRepository.existsById(id))
            tradeRepository.deleteById(id);
        else
            throw new NotFoundException();
    }
}
