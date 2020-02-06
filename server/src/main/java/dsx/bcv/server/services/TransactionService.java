package dsx.bcv.server.services;

import dsx.bcv.server.data.dto.TransactionDTO;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.data.models.TransactionStatus;
import dsx.bcv.server.data.models.TransactionType;
import dsx.bcv.server.data.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyService currencyService;
    private final LocalDateTimeService localDateTimeService;

    public TransactionService(
            TransactionRepository transactionRepository,
            CurrencyService currencyService,
            LocalDateTimeService localDateTimeService
    ) {
        this.transactionRepository = transactionRepository;
        this.currencyService = currencyService;
        this.localDateTimeService = localDateTimeService;
    }

    public Transaction save(Transaction transaction) {
        transaction.setCurrency(currencyService.saveIfNotExists(transaction.getCurrency()));
        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(
        String localDateTime,
        String transactionType,
        String currency,
        String amount,
        String commission,
        String transactionStatus,
        String transactionValueId
    ) {
        return new Transaction(
                localDateTimeService.getDateTimeFromString(localDateTime),
                transactionType.toLowerCase().equals("deposit") ? TransactionType.Deposit : TransactionType.Withdraw,
                currencyService.getCurrency(currency),
                new BigDecimal(amount.replaceAll(",", ".")),
                new BigDecimal(commission.replaceAll(",", ".")),
                transactionStatus.toLowerCase().equals("complete") ?
                        TransactionStatus.Complete : TransactionStatus.Complete,
                transactionValueId
        );
    }

    public Transaction getTransaction(TransactionDTO transactionDTO) {
        return getTransaction(
                transactionDTO.getDateTime().toString(),
                transactionDTO.getTransactionType(),
                transactionDTO.getCurrency(),
                transactionDTO.getAmount().toString(),
                transactionDTO.getCommission().toString(),
                transactionDTO.getTransactionStatus(),
                transactionDTO.getTransactionValueId()
        );
    }
}
