package dsx.bcv.server.services.api_connectors;

import dsx.bcv.server.services.api_connectors.tinkoff.TinkoffApiConnector;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ApiConnectorRepository {

    private final Map<ApiConnectorName, ApiConnector> apiConnectors;

    public ApiConnectorRepository(TinkoffApiConnector tinkoffApiConnector) {

        this.apiConnectors = Map.of(
                ApiConnectorName.Tinkoff, tinkoffApiConnector
        );
    }

    public ApiConnector getApiConnectorByName(ApiConnectorName apiConnectorName) {
        return apiConnectors.get(apiConnectorName);
    }
}
