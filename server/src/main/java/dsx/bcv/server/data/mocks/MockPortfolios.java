package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.models.Portfolio;
import dsx.bcv.server.services.data_services.PortfolioService;
import dsx.bcv.server.services.data_services.TradeService;
import dsx.bcv.server.services.data_services.TransactionService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@DependsOn({"mockTrades", "mockTransactions"})
public class MockPortfolios {

    private MockPortfolios(
            PortfolioService portfolioService,
            TradeService tradeService,
            TransactionService transactionService
    ) {
        var portfolio = new Portfolio("my_portfolio");
        portfolio.getTrades().addAll(StreamSupport.stream(
                tradeService.findAll().spliterator(), false).collect(Collectors.toSet())
        );
        portfolio.getTransactions().addAll(StreamSupport.stream(
                transactionService.findAll().spliterator(), false).collect(Collectors.toSet())
        );

        if (portfolioService.count() == 0) {
            portfolioService.save(portfolio);
        }
    }
}
