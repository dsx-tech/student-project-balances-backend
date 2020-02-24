package dsx.bcv.marketdata_provider.services.quote_providers.dsx;

import dsx.bcv.marketdata_provider.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DsxSupportedCurrenciesTest {

    @Autowired
    private DsxSupportedCurrencies dsxSupportedCurrencies;

    @Test
    public void getSupportedCurrencies() {

        var result = dsxSupportedCurrencies.getCurrencies();

        System.out.println(result);
    }
}