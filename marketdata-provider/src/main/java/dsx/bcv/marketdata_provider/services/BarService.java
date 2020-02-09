package dsx.bcv.marketdata_provider.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.marketdata_provider.data.dto.BarDTO;
import dsx.bcv.marketdata_provider.data.models.Bar;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarService {

    private RequestService requestService;

    public BarService(RequestService requestService) {
        this.requestService = requestService;
    }

    public BarDTO convertBarToBarDTO(Bar bar) {
        BarDTO barDTO = new BarDTO();
        barDTO.setExchangeRate(bar.getClose());
        barDTO.setTimestamp(bar.getTimestamp());
        return barDTO;
    }

    public List<BarDTO> getLastBars(String instrument, int count) throws IOException {
        var responseBody = requestService.doGetRequest(String.format("https://dsx.uk/mapi/lastBars/%s/d/%d", instrument, count));
        JSONObject jsonObject = new JSONObject(responseBody);
        String listBarString = String.valueOf(jsonObject.get(instrument));
        List<Bar> listBar =  new ObjectMapper().readValue(listBarString, new TypeReference<List<Bar>>(){});
        return listBar.stream()
                .map(this::convertBarToBarDTO)
                .collect(Collectors.toList());
    }
}
