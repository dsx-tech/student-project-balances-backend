package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage;

import dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models.AlphaVantageCurrency;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlphaVantageCsvParser {

    public List<AlphaVantageCurrency> parseCurrencies(Reader inputReader, char separator) throws IOException {

        Iterable<CSVRecord> records = CSVFormat.newFormat(separator).parse(inputReader);

        List<AlphaVantageCurrency> currencies = new ArrayList<>();
        for (CSVRecord record : records) {
            var currency = new AlphaVantageCurrency(
                    record.get(0),
                    record.get(1)
            );
            currencies.add(currency);
        }

        return currencies;
    }
}
