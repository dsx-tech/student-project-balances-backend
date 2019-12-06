package dsx.bcv.data.mocks;

import dsx.bcv.data.interfaces.ITradeRepository;
import dsx.bcv.data.models.Trade;
import dsx.bcv.exceptions.NotFoundException;
import dsx.bcv.services.parsers.dsx.DsxCsvTradeParserService;
import lombok.val;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MockTrades implements ITradeRepository {

    public static final MockTrades instance = new MockTrades();

    @Override
    public Trade add(Trade trade) {
        trades.add(trade);
        return trade;
    }

    @Override
    public boolean contains(Trade trade) {
        return trades.contains(trade);
    }

    @Override
    public List<Trade> getAll() {
        return trades;
    }

    @Override
    public Trade getById(long id) {
        return trades.stream()
                .filter(trade -> trade.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public Trade update(long id, Trade trade) {
        trade.setId(id);
        val tradeInList = getById(id);
        val index = trades.indexOf(tradeInList);
        trades.set(index, trade);
        return trade;
    }

    @Override
    public void delete(long id) {
        val tradeInList = getById(id);
        trades.remove(tradeInList);
    }

    private List<Trade> trades;

    private MockTrades() {

        var classLoader = this.getClass().getClassLoader();
        var inputStream = classLoader.getResourceAsStream("dsx_trades.csv");
        assert inputStream != null;
        var inputStreamReader = new InputStreamReader(inputStream);

        try {
            trades = new DsxCsvTradeParserService().parseTrades(
                    inputStreamReader, ';');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
