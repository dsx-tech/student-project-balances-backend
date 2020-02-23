package dsx.bcv.marketdata_provider.services.quote_providers.dsx;

import dsx.bcv.marketdata_provider.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DsxQuoteProviderTest {

    @Autowired
    DsxQuoteProvider dsxQuoteProvider;

    @Test
    public void getBarsInPeriod() {

        var result = dsxQuoteProvider.getBarsInPeriod("eur-rub", 1580905135, 1581077935);

        assertEquals(2, result.size());
    }

    @Test
    public void getTicker() {

        var result = dsxQuoteProvider.getTicker("eur-rub");
        var exchangeRate = result.getExchangeRate().doubleValue();

        assertTrue(exchangeRate > 60);
        assertTrue(exchangeRate < 80);
    }
}