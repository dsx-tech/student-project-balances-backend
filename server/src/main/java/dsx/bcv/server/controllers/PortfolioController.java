package dsx.bcv.server.controllers;

import dsx.bcv.server.data.models.Portfolio;
import dsx.bcv.server.services.data_services.PortfolioService;
import dsx.bcv.server.views.PortfolioVO;
import dsx.bcv.server.views.TradeVO;
import dsx.bcv.server.views.TransactionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("portfolios")
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final ConversionService conversionService;

    public PortfolioController(PortfolioService portfolioService, ConversionService conversionService) {
        this.portfolioService = portfolioService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public Iterable<PortfolioVO> findAll() {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return StreamSupport.stream(portfolioService.findAll().spliterator(), false)
                .map(portfolio -> conversionService.convert(portfolio, PortfolioVO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public PortfolioVO findByID(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = portfolioService.findById(id);
        return conversionService.convert(portfolio, PortfolioVO.class);
    }

    @GetMapping("{name}")
    public PortfolioVO findByName(@PathVariable String name) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = portfolioService.findByName(name);
        return conversionService.convert(portfolio, PortfolioVO.class);
    }

    @PostMapping
    public PortfolioVO add(@RequestBody PortfolioVO portfolioVO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = conversionService.convert(portfolioVO, Portfolio.class);
        assert portfolio != null;
        return conversionService.convert(
                portfolioService.save(portfolio),
                PortfolioVO.class
        );
    }

    @PutMapping("{id}")
    public PortfolioVO updateById(@PathVariable long id, @RequestBody PortfolioVO portfolioVO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = conversionService.convert(portfolioVO, Portfolio.class);
        var newPortfolio = portfolioService.updateById(id, portfolio);
        return conversionService.convert(newPortfolio, PortfolioVO.class);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        portfolioService.deleteById(id);
    }

    @GetMapping("{id}/trades")
    public List<TradeVO> getTradesById(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var trades = portfolioService.getTradesById(id);
        return trades.stream()
                .map(trade -> conversionService.convert(trade, TradeVO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/transactions")
    public List<TransactionVO> getTransactionsById(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var transactions = portfolioService.getTransactionsById(id);
        return transactions.stream()
                .map(transaction -> conversionService.convert(transaction, TransactionVO.class))
                .collect(Collectors.toList());
    }
}
