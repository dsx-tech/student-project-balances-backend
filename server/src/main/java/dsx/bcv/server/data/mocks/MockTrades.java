package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.services.TradeService;
import dsx.bcv.server.services.parsers.CsvParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class MockTrades {

    private List<Trade> trades;

    private MockTrades(CsvParser csvParser, TradeService tradeService) {

        var classLoader = this.getClass().getClassLoader();
        var inputStream = classLoader.getResourceAsStream("dsx_trades.csv");
        assert inputStream != null;
        var inputStreamReader = new InputStreamReader(inputStream);

        try {
            trades = csvParser.parseTrades(
                    inputStreamReader, ';');
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var trade : trades) {
            tradeService.save(trade);
        }
    }
}
