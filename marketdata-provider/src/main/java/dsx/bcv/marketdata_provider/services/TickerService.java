package dsx.bcv.marketdata_provider.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.marketdata_provider.data.dto.TickerDTO;
import dsx.bcv.marketdata_provider.data.models.Ticker;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TickerService {

    private RequestService requestService;

    public TickerService(RequestService requestService) {
        this.requestService = requestService;
    }

    public TickerDTO convertTickerToTickerDTO(Ticker ticker) {
        TickerDTO tickerDTO = new TickerDTO();
        tickerDTO.setExchangeRate(ticker.getLast());
        return tickerDTO;
    }

    public TickerDTO getTicker(String instrument) throws IOException {
        var responseBody = requestService.doGetRequest(String.format("https://dsx.uk/mapi/ticker/%s", instrument));
        JSONObject jsonObject = new JSONObject(responseBody);
        String tickerString = String.valueOf(jsonObject.get(instrument));
        Ticker ticker = new ObjectMapper().readValue(tickerString, Ticker.class);
        return convertTickerToTickerDTO(ticker);
    }
}
