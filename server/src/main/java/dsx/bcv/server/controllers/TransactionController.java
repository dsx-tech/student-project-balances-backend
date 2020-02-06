package dsx.bcv.server.controllers;

import dsx.bcv.server.data.dto.TransactionDTO;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.data.repositories.TransactionRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @GetMapping
    public Iterable<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("{id}")
    public Transaction getByID(@PathVariable long id){
        var transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent())
            return transactionOptional.get();
        else
            throw new NotFoundException();
    }

    @PostMapping
    public Transaction add(@RequestBody TransactionDTO transactionDTO) {
        var transaction = transactionService.getTransaction(transactionDTO);
        return transactionService.save(transaction);
    }

    @PutMapping("{id}")
    public Transaction update(@PathVariable long id, @RequestBody TransactionDTO transactionDTO) {
        transactionRepository.deleteById(id);
        var transaction = transactionService.getTransaction(transactionDTO);
        return transactionService.save(transaction);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        if (transactionRepository.existsById(id))
            transactionRepository.deleteById(id);
        else
            throw new NotFoundException();
    }
}
