package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.currency_graph;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DsxSupportedCurrenciesRepository {

    @Getter
    private final Set<DsxCurrencyVertex> supportedCurrencies;

    private DsxSupportedInstrumentsRepository dsxSupportedInstrumentsRepository;

    public DsxSupportedCurrenciesRepository(DsxSupportedInstrumentsRepository dsxSupportedInstrumentsRepository) {
        this.dsxSupportedInstrumentsRepository = dsxSupportedInstrumentsRepository;
        supportedCurrencies = initSupportedCurrencies();
        log.info("Dsx supported currencies: {}", supportedCurrencies);
    }

    private Set<DsxCurrencyVertex> initSupportedCurrencies() {

        var supportedInstruments = dsxSupportedInstrumentsRepository.getSupportedInstruments();

        var currencies = new HashSet<DsxCurrencyVertex>();
        supportedInstruments.forEach(dsxInstrument -> currencies.add(dsxInstrument.getBaseCurrency()));
        supportedInstruments.forEach(dsxInstrument -> currencies.add(dsxInstrument.getQuotedCurrency()));

        return currencies;
    }
}
