package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageAsset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlphaVantageCsvParser {

    public List<AlphaVantageAsset> parseCurrencies(Reader inputReader, char separator) throws IOException {

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(inputReader);

        List<AlphaVantageAsset> assets = new ArrayList<>();
        for (CSVRecord record : records) {
            var asset = new AlphaVantageAsset(
                    record.get(0),
                    record.get(1)
            );
            assets.add(asset);
        }

        return assets;
    }
}
