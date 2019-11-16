package dsx.bcv.data.interfaces;

import dsx.bcv.data.models.Trade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITradeRepository {

    Trade add(Trade trade);
    boolean contains(Trade trade);
    List<Trade> getAllTrades();
    Trade getById(long id);
    Trade update(long id, Trade trade);

}
