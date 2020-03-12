package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageAsset;
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
public class AlphaVantageSupportedAssets {

    private final AlphaVantageCsvParser alphaVantageCsvParser;

    @Getter
    private Set<AlphaVantageAsset> physicalCurrencies;
    @Getter
    private Set<AlphaVantageAsset> digitalCurrencies;

    public AlphaVantageSupportedAssets(AlphaVantageCsvParser alphaVantageCsvParser) {

        this.alphaVantageCsvParser = alphaVantageCsvParser;

        try {
            physicalCurrencies = new HashSet<>(getCurrenciesFromFile("physical_currency_list.csv"));
            digitalCurrencies = new HashSet<>(getCurrenciesFromFile("digital_currency_list.csv"));
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        log.info("AlphaVantageSupportedAssets:\n" +
                "Physical currencies: {}\n" +
                "Digital currencies: {}",
                physicalCurrencies,
                digitalCurrencies);
    }

    private List<AlphaVantageAsset> getCurrenciesFromFile(String fileName) throws IOException {

        var classLoader = this.getClass().getClassLoader();
        var inputStream = classLoader.getResourceAsStream(fileName);
        assert inputStream != null;
        var inputStreamReader = new InputStreamReader(inputStream);

        return alphaVantageCsvParser.parseCurrencies(inputStreamReader, ',');
    }
}
