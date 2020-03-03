package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.data.repositories.BarRepository;
import dsx.bcv.marketdata_provider.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarService {

    private final BarRepository barRepository;
    private final CurrencyService currencyService;

    public BarService(BarRepository barRepository, CurrencyService currencyService) {
        this.barRepository = barRepository;
        this.currencyService = currencyService;
    }

    public Bar save(Bar bar) {
        bar.setBaseCurrency(
                currencyService.findByCode(bar.getBaseCurrency().getCode()).orElseThrow(NotFoundException::new)
        );
        return barRepository.save(bar);
    }

    public Iterable<Bar> saveAll(Iterable<Bar> bars) {
        bars.forEach(bar -> bar.setBaseCurrency(
                currencyService.findByCode(bar.getBaseCurrency().getCode()).orElseThrow(NotFoundException::new)
        ));
        return barRepository.saveAll(bars);
    }

    public boolean existsByCurrency(Currency currency) {
        return barRepository.findByBaseCurrency(
                currencyService.findByCode(currency.getCode()).orElseThrow(NotFoundException::new)
        ).isPresent();
    }

    public List<Bar> findByBaseCurrencyAndTimestampBetween(Currency currency, long startTime, long endTime) {
        return barRepository.findByBaseCurrencyAndTimestampBetween(currency, startTime, endTime);
    }

    public Bar findTopByBaseCurrencyOrderByTimestampDesc(Currency currency) {
        return barRepository.findTopByBaseCurrencyOrderByTimestampDesc(currency).orElseThrow(NotFoundException::new);
    }
}
