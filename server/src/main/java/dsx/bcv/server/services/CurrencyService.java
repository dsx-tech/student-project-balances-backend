package dsx.bcv.server.services;

import dsx.bcv.server.data.models.Currency;
import dsx.bcv.server.data.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency saveIfNotExists(Currency currency) {
        return saveIfNotExists(currency.getName());
    }

    public Currency saveIfNotExists(String currencyName) {
        currencyName = currencyName.toLowerCase();
        var currencyOptional = currencyRepository.findByName(currencyName);
        if (currencyOptional.isPresent())
            return currencyOptional.get();
        else
            return currencyRepository.save(new Currency(currencyName));
    }

    public Currency getCurrency(String currencyName) {
        return new Currency(currencyName.toLowerCase());
    }
}
