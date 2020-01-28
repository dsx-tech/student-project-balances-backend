package dsx.bcv.server.data.mocks;

import dsx.bcv.server.data.interfaces.ITradeRepository;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.exceptions.NotFoundException;
import dsx.bcv.server.services.parsers.CsvParser;
import dsx.bcv.server.services.parsers.data_formats.DsxDataFormat;
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
            trades = new CsvParser(new DsxDataFormat()).parseTrades(
                    inputStreamReader, ';');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}