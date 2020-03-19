package dsx.bcv.server.controllers;

import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.services.data_services.TransactionService;
import dsx.bcv.server.views.TransactionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Deprecated
@RestController
@RequestMapping("transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;
    private final ConversionService conversionService;

    public TransactionController(
            TransactionService transactionService,
            ConversionService conversionService
    ) {
        this.transactionService = transactionService;
        this.conversionService = conversionService;
    }

    @Deprecated
    @GetMapping
    public Iterable<TransactionVO> findAll() {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return StreamSupport.stream(transactionService.findAll().spliterator(), false)
                .map(transaction -> conversionService.convert(transaction, TransactionVO.class))
                .collect(Collectors.toList());
    }

    @Deprecated
    @GetMapping("{id}")
    public TransactionVO findByID(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var transaction = transactionService.findById(id);
        return conversionService.convert(transaction, TransactionVO.class);
    }

    @Deprecated
    @PostMapping
    public TransactionVO add(@RequestBody TransactionVO transactionVO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var transaction = conversionService.convert(transactionVO, Transaction.class);
        assert transaction != null;
        return conversionService.convert(
                transactionService.save(transaction),
                TransactionVO.class
        );
    }

    @Deprecated
    @PutMapping("{id}")
    public TransactionVO update(@PathVariable long id, @RequestBody TransactionVO transactionVO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var transaction = conversionService.convert(transactionVO, Transaction.class);
        var newTransaction = transactionService.updateById(id, transaction);
        return conversionService.convert(newTransaction, TransactionVO.class);
    }

    @Deprecated
    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        transactionService.deleteById(id);
    }
}
