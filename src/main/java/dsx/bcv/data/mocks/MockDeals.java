package dsx.bcv.data.mocks;

import dsx.bcv.data.interfaces.IDealRepository;
import dsx.bcv.data.models.Deal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDeals implements IDealRepository {

    @Override
    public void add(Deal deal) {
        deals.add(deal);
    }

    @Override
    public boolean contains(Deal deal) {
        return deals.contains(deal);
    }

    @Override
    public List<Deal> getAllDeals() {
        return deals;
    }

    private List<Deal> deals = new ArrayList<>(Arrays.asList(
            new Deal(LocalDateTime.now(), "BTCUSD", "Sell", new BigDecimal("0.00097134"), "BTC",
                    new BigDecimal("10142.28001"), "USD", new BigDecimal("0.02"), "USD", new BigInteger("37387684")),
            new Deal(LocalDateTime.now(), "ETHBTC", "Buy", new BigDecimal("0.001"), "ETH",
                    new BigDecimal("0.021"), "BTC", new BigDecimal("0.0000025"), "USD", new BigInteger("37381155"))));
}
