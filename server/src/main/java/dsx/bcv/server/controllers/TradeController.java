package dsx.bcv.server.controllers;

import dsx.bcv.server.data.dto.TradeDTO;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.repositories.TradeRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("trades")
@Slf4j
public class TradeController {

    private final TradeRepository tradeRepository;
    private final TradeService tradeService;

    public TradeController(TradeRepository tradeRepository, TradeService tradeService) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
    }

    @GetMapping
    public Iterable<Trade> getAll() {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return tradeRepository.findAll();
    }

    @GetMapping("{id}")
    public Trade getByID(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return tradeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Trade add(@RequestBody TradeDTO tradeDTO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var trade = tradeService.getTrade(tradeDTO);
        return tradeService.save(trade);
    }

    @PutMapping("{id}")
    public Trade update(@PathVariable long id, @RequestBody TradeDTO tradeDTO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        tradeRepository.deleteById(id);
        var trade = tradeService.getTrade(tradeDTO);
        return tradeService.save(trade);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        if (tradeRepository.existsById(id))
            tradeRepository.deleteById(id);
        else
            throw new NotFoundException();
    }
}
