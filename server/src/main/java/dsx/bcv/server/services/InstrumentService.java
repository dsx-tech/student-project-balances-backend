package dsx.bcv.server.services;

import dsx.bcv.server.data.models.Currency;
import dsx.bcv.server.data.models.Instrument;
import dsx.bcv.server.data.repositories.InstrumentRepository;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final CurrencyService currencyService;

    public InstrumentService(InstrumentRepository instrumentRepository, CurrencyService currencyService) {
        this.instrumentRepository = instrumentRepository;
        this.currencyService = currencyService;
    }

    public Instrument saveIfNotExists(Instrument instrument) {
        return saveIfNotExists(instrument.getFirstCurrency(), instrument.getSecondCurrency());
    }

    public Instrument saveIfNotExists(Currency firstCurrency, Currency secondCurrency){
        firstCurrency = currencyService.saveIfNotExists(firstCurrency);
        secondCurrency = currencyService.saveIfNotExists(secondCurrency);
        var instrumentOptional = instrumentRepository.findByFirstCurrencyAndSecondCurrency(firstCurrency, secondCurrency);
        if (instrumentOptional.isPresent())
            return instrumentOptional.get();
        else
            return instrumentRepository.save(new Instrument(firstCurrency, secondCurrency));
    }

    public Instrument saveIfNotExists(String currencyPair){
        var firstCurrency = currencyService.getCurrency(currencyPair.substring(0, 3));
        var secondCurrency = currencyService.getCurrency(currencyPair.substring(3, 6));
        return saveIfNotExists(firstCurrency, secondCurrency);
    }

    public Instrument getInstrument(Currency firstCurrency, Currency secondCurrency) {
        return new Instrument(firstCurrency, secondCurrency);
    }

    public Instrument getInstrument(String currencyPair) {
        var firstCurrency = currencyService.getCurrency(currencyPair.substring(0, 3));
        var secondCurrency = currencyService.getCurrency(currencyPair.substring(3, 6));
        return new Instrument(firstCurrency, secondCurrency);
    }
}
