package dsx.bcv.data.interfaces;

import dsx.bcv.data.models.Deal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDealRepository {

    void add(Deal deal);
    boolean contains(Deal deal);
    List<Deal> getAllDeals();

}
