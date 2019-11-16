package dsx.bcv.controllers;

import dsx.bcv.data.mocks.MockTransactions;
import dsx.bcv.data.models.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final MockTransactions transactionRepository;

    public TransactionController() {
        transactionRepository = new MockTransactions();
    }

    @RequestMapping("list")
    public List<Transaction> getAllTransactions(){
        return transactionRepository.getAllTransactions();
    }

    @GetMapping("{id}")
    public Transaction getByID(@PathVariable long id){
        return transactionRepository.getById(id);
    }

    @PostMapping
    public Transaction add(@RequestBody Transaction transaction){
        return transactionRepository.add(transaction);
    }

    @PutMapping
    public Transaction update(@PathVariable long id, @RequestBody Transaction transaction) {
        return transactionRepository.update(id, transaction);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id){
        transactionRepository.delete(id);
    }
}
