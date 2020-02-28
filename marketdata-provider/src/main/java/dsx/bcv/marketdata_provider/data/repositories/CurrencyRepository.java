package dsx.bcv.marketdata_provider.data.repositories;

import dsx.bcv.marketdata_provider.data.models.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
}
