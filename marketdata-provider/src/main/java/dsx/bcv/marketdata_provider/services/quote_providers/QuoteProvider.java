package dsx.bcv.marketdata_provider.services.quote_providers;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Instrument;
import dsx.bcv.marketdata_provider.data.models.Ticker;

import java.util.List;

public interface QuoteProvider {
    List<Bar> getBarsInPeriod(String instrument, long startTime, long endTime);
    Ticker getTicker(String instrument);
    List<Instrument> getInstruments();
}
