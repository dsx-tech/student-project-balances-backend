package dsx.bcv.server.controllers;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.services.data_services.TradeService;
import dsx.bcv.server.views.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("trades")
@Slf4j
public class TradeController {

    private final TradeService tradeService;
    private final ConversionService conversionService;

    public TradeController(
            TradeService tradeService,
            ConversionService conversionService
    ) {
        this.tradeService = tradeService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public Iterable<TradeVO> getAll() {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return StreamSupport.stream(tradeService.findAll().spliterator(), false)
                .map(trade -> conversionService.convert(trade, TradeVO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TradeVO getByID(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var trade = tradeService.findById(id);
        return conversionService.convert(trade, TradeVO.class);
    }

    @PostMapping
    public TradeVO add(@RequestBody TradeVO tradeVO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var trade = conversionService.convert(tradeVO, Trade.class);
        assert trade != null;
        return conversionService.convert(
                tradeService.save(trade),
                TradeVO.class
        );
    }

    @PutMapping("{id}")
    public TradeVO update(@PathVariable long id, @RequestBody TradeVO tradeVO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var trade = conversionService.convert(tradeVO, Trade.class);
        var newTrade = tradeService.updateById(id, trade);
        return conversionService.convert(newTrade, TradeVO.class);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        tradeService.deleteById(id);
    }
}
