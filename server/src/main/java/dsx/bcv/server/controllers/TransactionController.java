package dsx.bcv.server.controllers;

import dsx.bcv.server.data.dto.TransactionDTO;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.data.repositories.TransactionRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("transactions")
@Slf4j
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @GetMapping
    public Iterable<Transaction> getAll() {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return transactionRepository.findAll();
    }

    @GetMapping("{id}")
    public Transaction getByID(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return transactionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Transaction add(@RequestBody TransactionDTO transactionDTO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var transaction = transactionService.getTransaction(transactionDTO);
        return transactionService.save(transaction);
    }

    @PutMapping("{id}")
    public Transaction update(@PathVariable long id, @RequestBody TransactionDTO transactionDTO) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        transactionRepository.deleteById(id);
        var transaction = transactionService.getTransaction(transactionDTO);
        return transactionService.save(transaction);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        if (transactionRepository.existsById(id))
            transactionRepository.deleteById(id);
        else
            throw new NotFoundException();
    }
}
