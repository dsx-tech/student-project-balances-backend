package dsx.bcv.marketdata_provider.services.quote_providers.dsx_provider.currency_graph;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DsxCurrencyGraph {

    private final Graph<DsxCurrencyVertex, DsxInstrumentEdge> currencyGraph = new SimpleDirectedGraph<>(DsxInstrumentEdge.class);

    private DsxSupportedCurrenciesRepository dsxSupportedCurrenciesRepository;
    private DsxSupportedInstrumentsRepository dsxSupportedInstrumentsRepository;

    public DsxCurrencyGraph(
            DsxSupportedCurrenciesRepository dsxSupportedCurrenciesRepository,
            DsxSupportedInstrumentsRepository dsxSupportedInstrumentsRepository)
    {
        this.dsxSupportedCurrenciesRepository = dsxSupportedCurrenciesRepository;
        this.dsxSupportedInstrumentsRepository = dsxSupportedInstrumentsRepository;

        fillGraph();
    }

    private void fillGraph() {

        dsxSupportedCurrenciesRepository.getSupportedCurrencies()
                .forEach(currencyGraph::addVertex);

        dsxSupportedInstrumentsRepository.getSupportedInstruments()
                .forEach(dsxInstrumentEdge -> currencyGraph.addEdge(
                        dsxInstrumentEdge.getBaseCurrency(),
                        dsxInstrumentEdge.getQuotedCurrency(),
                        dsxInstrumentEdge
                ));

        dsxSupportedInstrumentsRepository.getReversedInstruments()
                .forEach(dsxInstrumentEdge -> currencyGraph.addEdge(
                        dsxInstrumentEdge.getQuotedCurrency(),
                        dsxInstrumentEdge.getBaseCurrency(),
                        dsxInstrumentEdge
                ));
    }

    public List<DsxInstrumentEdge> getShortestPath(DsxCurrencyVertex v1, DsxCurrencyVertex v2) {
        var path = DijkstraShortestPath.findPathBetween(currencyGraph, v1, v2);
        return path.getEdgeList();
    }
}
