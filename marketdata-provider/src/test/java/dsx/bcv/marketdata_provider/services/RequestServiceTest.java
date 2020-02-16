package dsx.bcv.marketdata_provider.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.marketdata_provider.Application;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_converters.DsxTickerConverter;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxBar;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.dsx_models.DsxTicker;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RequestServiceTest {

    @Autowired
    private RequestService requestService;
    @Autowired
    private DsxTickerConverter dsxTickerConverter;

    @Test
    public void doGetRequest() throws IOException, JSONException {

        var responseBody = requestService.doGetRequest(String.format("https://dsx.uk/mapi/lastBars/%s/d/%d", "usdrub", 10));
        System.out.println(responseBody);
        JSONObject jsonObject = new JSONObject(responseBody);
        String x = String.valueOf(jsonObject.get("usdrub"));
        ObjectMapper mapper = new ObjectMapper();
        List<DsxBar> listDsxBar = mapper.readValue(x, new TypeReference<List<DsxBar>>(){});
        for (DsxBar dsxBar : listDsxBar) {
            System.out.println(dsxBar);
        }
    }

    @Test
    public void doGetRequestTicker() throws IOException, JSONException {

        var responseBody = requestService.doGetRequest(String.format("https://dsx.uk/mapi/ticker/%s", "usdrub"));
        System.out.println(responseBody);
        JSONObject jsonObject = new JSONObject(responseBody);
        String tickerString = String.valueOf(jsonObject.get("usdrub"));
        DsxTicker dsxTicker = new ObjectMapper().readValue(tickerString, DsxTicker.class);
        System.out.println(dsxTickerConverter.convertDsxTickerToTicker(dsxTicker));
    }
}