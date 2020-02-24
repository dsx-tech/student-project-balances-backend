package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCurrency;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class AlphaVantageSupportedCurrencies {

    private final AlphaVantageCsvParser alphaVantageCsvParser;

    @Getter
    private Set<AlphaVantageCurrency> physicalCurrencies;
    @Getter
    private Set<AlphaVantageCurrency> digitalCurrencies;

    public AlphaVantageSupportedCurrencies(AlphaVantageCsvParser alphaVantageCsvParser) {

        this.alphaVantageCsvParser = alphaVantageCsvParser;

        try {
            physicalCurrencies = new HashSet<>(getCurrenciesFromFile("physical_currency_list.csv"));
            digitalCurrencies = new HashSet<>(getCurrenciesFromFile("digital_currency_list.csv"));
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        log.info("AlphaVantageSupportedCurrencies:\n" +
                "Physical: {}\n" +
                "Digital: {}",
                physicalCurrencies,
                digitalCurrencies);
    }

    private List<AlphaVantageCurrency> getCurrenciesFromFile(String fileName) throws IOException {

        var classLoader = this.getClass().getClassLoader();
        var inputStream = classLoader.getResourceAsStream(fileName);
        assert inputStream != null;
        var inputStreamReader = new InputStreamReader(inputStream);

        return alphaVantageCsvParser.parseCurrencies(inputStreamReader, ',');
    }
}
