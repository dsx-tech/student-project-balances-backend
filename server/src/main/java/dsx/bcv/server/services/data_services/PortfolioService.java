package dsx.bcv.server.services.data_services;

import dsx.bcv.server.data.models.Portfolio;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.data.repositories.PortfolioRepository;
import dsx.bcv.server.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio save(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public Portfolio findById(long id) {
        return portfolioRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Portfolio findByName(String name) {
        return portfolioRepository.findByName(name).orElseThrow(NotFoundException::new);
    }

    public List<Trade> getTradesById(long id) {
        var portfolio = portfolioRepository.findById(id).orElseThrow(NotFoundException::new);
        return new ArrayList<>(portfolio.getTrades());
    }

    public List<Transaction> getTransactionsById(long id) {
        var portfolio = portfolioRepository.findById(id).orElseThrow(NotFoundException::new);
        return new ArrayList<>(portfolio.getTransactions());
    }

    public Iterable<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public Portfolio updateById(long id, Portfolio portfolio) {
        throw new RuntimeException("Portfolio updating is not implemented");
    }

    public void deleteById(long id) {
        portfolioRepository.deleteById(id);
    }
}
