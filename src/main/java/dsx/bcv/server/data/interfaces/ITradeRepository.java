package dsx.bcv.server.data.interfaces;

import dsx.bcv.server.data.models.Trade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITradeRepository {

    Trade add(Trade trade);
    boolean contains(Trade trade);
    List<Trade> getAll();
    Trade getById(long id);
    Trade update(long id, Trade trade);
    void delete(long id);
}
