package dsx.bcv.marketdata_provider.services.quote_providers.dsx;

import dsx.bcv.marketdata_provider.Application;
import dsx.bcv.marketdata_provider.services.quote_providers.dsx.currency_graph.DsxSupportedInstrumentsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DsxSupportedInstrumentsRepositoryTest {

    @Autowired
    private DsxSupportedInstrumentsRepository dsxSupportedInstrumentsRepository;

    @Test
    public void getSupportedInstruments() {

        var result = dsxSupportedInstrumentsRepository.getSupportedInstruments();

        System.out.println(result);
    }
}