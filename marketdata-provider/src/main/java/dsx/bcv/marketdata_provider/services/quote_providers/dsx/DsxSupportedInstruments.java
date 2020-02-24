package dsx.bcv.marketdata_provider.services.quote_providers.dsx;

import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.marketdata_provider.services.RequestService;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.currency_graph.DsxInstrumentEdge;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.models.DsxInstrument;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DsxSupportedInstruments {

    @Getter
    private final Set<DsxInstrumentEdge> instruments;

    @Getter
    private final Set<DsxInstrumentEdge> reversedInstruments;

    private final RequestService requestService;
    private final ObjectMapper objectMapper;

    public DsxSupportedInstruments(RequestService requestService, ObjectMapper objectMapper) {

        this.requestService = requestService;
        this.objectMapper = objectMapper;

        instruments = initSupportedInstruments();
        log.info("Dsx supported instruments: {}", instruments);

        reversedInstruments = new HashSet<>();
        instruments.forEach(dsxInstrumentEdge -> reversedInstruments.add(dsxInstrumentEdge.reverse()));
    }

    @SneakyThrows
    private Set<DsxInstrumentEdge> initSupportedInstruments() {

        var dsxUrlInfo = "https://dsx.uk/mapi/info";
        var responseBody = requestService.doGetRequest(dsxUrlInfo);

        var responseBodyJsonObject = new JSONObject(responseBody);
        var instrumentList = String.valueOf(responseBodyJsonObject.get("pairs"));
        var instrumentListJsonObject = new JSONObject(instrumentList);

        var instruments = new HashSet<DsxInstrumentEdge>();
        for (var key : instrumentListJsonObject.keySet()) {
            var instrumentString = String.valueOf(instrumentListJsonObject.get(key));
            var instrument = objectMapper.readValue(instrumentString, DsxInstrument.class);
            instruments.add(new DsxInstrumentEdge(instrument));
        }

        return instruments;
    }
}
