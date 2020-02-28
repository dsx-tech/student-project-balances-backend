package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.Application;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Test
    public void doGetRequest() throws IOException, JSONException {

        var responseBody = requestService.doGetRequest(String.format("https://dsx.uk/mapi/lastBars/%s/d/%d", "usdrub", 10));

        assertNotNull(responseBody);
    }

    @Test
    public void doGetRequestTicker() throws IOException, JSONException {

        var responseBody = requestService.doGetRequest(String.format("https://dsx.uk/mapi/ticker/%s", "usdrub"));

        assertNotNull(responseBody);
    }
}