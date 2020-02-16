package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.currency_graph;

import dsx.bcv.marketdata_provider.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DsxCurrencyGraphTest {

    @Autowired
    private DsxCurrencyGraph dsxCurrencyGraph;
    @Autowired
    private DsxSupportedInstrumentsRepository dsxSupportedInstrumentsRepository;

    @Test
    public void getShortestPath() {
        final var shortestPath = dsxCurrencyGraph.getShortestPath(
                new DsxCurrencyVertex("usd"),
                new DsxCurrencyVertex("eur")
        );

        for (var edge : shortestPath) {
            if (!edge.isReversed()) {
                System.out.println(edge + " Correct edge");
            } else {
                System.out.println(edge + " Reversed edge");
            }

        }

        System.out.println(shortestPath);
    }
}