package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.data.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Optional<Currency> findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    public Iterable<Currency> saveAll(Iterable<Currency> currencies) {
        return currencyRepository.saveAll(currencies);
    }

    public Iterable<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public long count() {
        return currencyRepository.count();
    }
}
