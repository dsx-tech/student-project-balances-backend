package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageInstrument;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class AlphaVantageSupportedInstruments {

    @Getter
    private Set<AlphaVantageInstrument> instruments;

    public AlphaVantageSupportedInstruments(
            AlphaVantageSupportedCurrencies alphaVantageSupportedCurrencies
    ) {
        instruments = new HashSet<>();
        /*
        for (var baseCurrency : alphaVantageSupportedCurrencies.getCurrencies())
            for (var quotedCurrency : alphaVantageSupportedCurrencies.getCurrencies()) {
                if (baseCurrency != quotedCurrency)
                    instruments.add(new AlphaVantageInstrument(baseCurrency, quotedCurrency));
            }
         */
        log.info("AlphaVantageSupportedInstruments: {}", instruments);
    }
}
