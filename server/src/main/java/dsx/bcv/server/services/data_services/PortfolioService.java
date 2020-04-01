package dsx.bcv.server.services.data_services;

import dsx.bcv.server.data.models.Portfolio;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.data.repositories.PortfolioRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.parsers.CsvParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TradeService tradeService;
    private final TransactionService transactionService;
    private final CsvParser csvParser;

    public PortfolioService(
            PortfolioRepository portfolioRepository,
            TradeService tradeService,
            TransactionService transactionService,
            CsvParser csvParser
    ) {
        this.portfolioRepository = portfolioRepository;
        this.tradeService = tradeService;
        this.transactionService = transactionService;
        this.csvParser = csvParser;
    }

    public Portfolio save(Portfolio portfolio) {
        var savedPortfolio = portfolioRepository.save(portfolio);
        log.debug("save: Portfolio {} saved", savedPortfolio);
        return savedPortfolio;
    }

    public Portfolio findById(long id) {
        var portfolio = portfolioRepository.findById(id).orElseThrow(NotFoundException::new);
        log.debug("findById: Portfolio {} found", portfolio);
        return portfolio;
    }

    public Portfolio findByName(String name) {
        var portfolio = portfolioRepository.findByName(name).orElseThrow(NotFoundException::new);
        log.debug("findByName: Portfolio {} found", portfolio);
        return portfolio;
    }

    public List<Trade> getTradesByPortfolioId(long id) {
        var portfolio = findById(id);
        var trades = new ArrayList<>(portfolio.getTrades());
        log.debug("getTradesByPortfolioId: Trades {}... found", trades.stream().limit(2));
        return trades;
    }

    public List<Transaction> getTransactionsByPortfolioId(long id) {
        var portfolio = findById(id);
        var transactions = new ArrayList<>(portfolio.getTransactions());
        log.debug("getTransactionsByPortfolioId: Transactions {}... found", transactions.stream().limit(2));
        return transactions;
    }

    public Iterable<Portfolio> findAll() {
        log.debug("findAll: Called");
        return portfolioRepository.findAll();
    }

    public Portfolio updateById(long id, Portfolio portfolio) {
        log.debug("updateById: Called (Not implemented)");
        throw new RuntimeException("Portfolio updating is not implemented");
    }

    @SneakyThrows
    public void uploadTradeFileByPortfolioId(MultipartFile file, long portfolioId) {
        log.debug(
                "uploadTradeFileByPortfolioId: File with filename {}({}) uploaded",
                file.getName(),
                file.getOriginalFilename()
        );
        var trades = csvParser.parseTrades(new InputStreamReader(file.getInputStream()), ';');
        log.debug("uploadTradeFileByPortfolioId: Trades {}... parsed", trades.stream().limit(2));
        addTradesByPortfolioId(trades, portfolioId);
    }

    @SneakyThrows
    public void uploadTransactionFileByPortfolioId(MultipartFile file, long portfolioId) {
        log.debug(
                "uploadTransactionFileByPortfolioId: File with filename {}({}) uploaded",
                file.getName(),
                file.getOriginalFilename()
        );
        var transactions = csvParser.parseTransactions(new InputStreamReader(file.getInputStream()), ';');
        log.debug("uploadTransactionFileByPortfolioId: Transactions {}... parsed", transactions.stream().limit(2));
        addTransactionsByPortfolioId(transactions, portfolioId);
    }

    public void addTradesByPortfolioId(List<Trade> trades, long portfolioId) {
        var savedTrades = tradeService.saveAll(trades);
        var portfolio = findById(portfolioId);
        portfolio.getTrades().addAll(savedTrades);
        save(portfolio);
        log.debug("addTradesByPortfolioId: Trades added");
    }

    public void addTransactionsByPortfolioId(List<Transaction> transactions, long portfolioId) {
        var savedTransactions = transactionService.saveAll(transactions);
        var portfolio = findById(portfolioId);
        portfolio.getTransactions().addAll(savedTransactions);
        save(portfolio);
        log.debug("addTransactionsByPortfolioId: Transactions added");
    }

    public void deleteById(long id) {
        log.debug("deleteById: Called with id = {}", id);
        portfolioRepository.deleteById(id);
    }

    public long count() {
        log.debug("count: Called");
        return portfolioRepository.count();
    }
}
