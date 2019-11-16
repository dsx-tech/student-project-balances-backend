package dsx.bcv.controllers;

import dsx.bcv.data.mocks.MockTransactions;
import dsx.bcv.data.models.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
