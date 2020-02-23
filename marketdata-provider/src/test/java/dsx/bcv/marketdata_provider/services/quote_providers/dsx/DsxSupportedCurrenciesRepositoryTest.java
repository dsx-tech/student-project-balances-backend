package dsx.bcv.marketdata_provider.services.quote_providers.dsx;

import dsx.bcv.marketdata_provider.Application;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.currency_graph.DsxSupportedCurrenciesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DsxSupportedCurrenciesRepositoryTest {

    @Autowired
    private DsxSupportedCurrenciesRepository dsxSupportedCurrenciesRepository;

    @Test
    public void getSupportedCurrencies() {

        var result = dsxSupportedCurrenciesRepository.getSupportedCurrencies();

        System.out.println(result);
    }
}