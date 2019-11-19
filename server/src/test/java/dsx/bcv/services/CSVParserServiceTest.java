package dsx.bcv.services;

import dsx.bcv.data.models.Trade;
import dsx.bcv.data.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CSVParserServiceTest {

    @Test
    public void parseOneTrade() throws IOException {

        var deals = "2019-09-20 07:41:35;btcusd;Sell;0.00097134;BTC;10142.28001;USD;0.02;USD;37387684\n";

        var result = new CSVParserService().parseTrades(new StringReader(deals), ';').get(0);

        var expected = new Trade(LocalDateTime.parse("2019-09-20T07:41:35"), "btcusd",
                "Sell", new BigDecimal("0.00097134"), "BTC",
                new BigDecimal("10142.28001"), "USD",
                new BigDecimal("0.02"), "USD", "37387684");

        expected.setId(result.getId());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void parseTrades() throws IOException {

        var trades = "2019-09-20 07:41:35;btcusd;Sell;0.00097134;BTC;10142.28001;USD;0.02;USD;37387684\n" +
                "2019-09-18 09:05:05;ethbtc;Buy;0.001;ETH;0.021;BTC;0.0000025;ETH;37381155\n";

        var result = new CSVParserService().parseTrades(new StringReader(trades), ';');

        var expected = Arrays.asList(
                new Trade(LocalDateTime.parse("2019-09-20T07:41:35"), "btcusd",
                        "Sell", new BigDecimal("0.00097134"), "BTC",
                        new BigDecimal("10142.28001"), "USD",
                        new BigDecimal("0.02"), "USD", "37387684"),
                new Trade(LocalDateTime.parse("2019-09-18T09:05:05"), "ethbtc",
                        "Buy", new BigDecimal("0.001"), "ETH",
                        new BigDecimal("0.021"), "BTC",
                        new BigDecimal("0.0000025"), "ETH", "37381155")
        );

        for (var i = 0; i < expected.size(); i++){
            expected.get(i).setId(result.get(i).getId());
        }

        assertEquals(expected, result);
    }

    @Test
    public void parseOneTransaction() throws IOException {

        var transactions = "2019-10-26 15:30:34;Deposit;BTC;0.0052036;0;Complete;3662143\n";

        var result = new CSVParserService().parseTransactions(new StringReader(transactions), ';').get(0);

        var expected = new Transaction(LocalDateTime.parse("2019-10-26T15:30:34"), "Deposit", "BTC",
                        new BigDecimal("0.0052036"), new BigDecimal("0"),"Complete", "3662143");

        expected.setId(result.getId());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void parseTransactions() throws IOException {

        var transactions = "2019-10-26 15:30:34;Deposit;BTC;0.0052036;0;Complete;3662143\n" +
                "2019-10-26 15:30:34;Withdraw;USD;48.22;0;Complete;3662142\n";

        var result = new CSVParserService().parseTransactions(new StringReader(transactions), ';');

        var expected = Arrays.asList(
                new Transaction(LocalDateTime.parse("2019-10-26T15:30:34"), "Deposit", "BTC",
                        new BigDecimal("0.0052036"), new BigDecimal("0"),"Complete", "3662143"),
                new Transaction(LocalDateTime.parse("2019-10-26T15:30:34"), "Withdraw", "USD",
                        new BigDecimal("48.22"), new BigDecimal("0"), "Complete", "3662142")
        );

        for (var i = 0; i < expected.size(); i++){
            expected.get(i).setId(result.get(i).getId());
        }

        assertEquals(expected, result);
    }
}