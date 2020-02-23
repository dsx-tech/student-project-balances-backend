package dsx.bcv.marketdata_provider.converters;

import dsx.bcv.marketdata_provider.data.models.Currency;
import dsx.bcv.marketdata_provider.data.models.Instrument;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.currency_graph.DsxInstrumentEdge;
import org.springframework.core.convert.converter.Converter;

public class DsxInstrumentEdgeToInstrumentConverter implements Converter<DsxInstrumentEdge, Instrument> {

    @Override
    public Instrument convert(DsxInstrumentEdge dsxInstrumentEdge) {
        return new Instrument(
                new Currency(dsxInstrumentEdge.getBaseCurrency().toString()),
                new Currency(dsxInstrumentEdge.getQuotedCurrency().toString())
        );
    }
}
