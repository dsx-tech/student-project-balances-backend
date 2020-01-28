package dsx.bcv.server.services;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.Transaction;
import dsx.bcv.server.services.parsers.CsvParser;
import dsx.bcv.server.services.parsers.data_formats.ProjectDataFormat;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CsvParserTest {

    @Test
    public void parseTrades() throws IOException {

        var trades = "2019-09-20 07:41:35;btcusd;Sell;0.00097134;BTC;10142.28001;USD;0.02;USD;37387684\n" +
                "2019-09-18 09:05:05;ethbtc;Buy;0.001;ETH;0.021;BTC;0.0000025;ETH;37381155\n";

        var result = new CsvParser(new ProjectDataFormat()).parseTrades(new StringReader(trades), ';');

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
    public void parseTransactions() throws IOException {

        var transactions = "2019-10-26 15:30:34;Deposit;BTC;0.0052036;0;Complete;3662143\n" +
                "2019-10-26 15:30:34;Withdraw;USD;48.22;0;Complete;3662142\n";

        var result = new CsvParser(new ProjectDataFormat()).parseTransactions(new StringReader(transactions), ';');

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