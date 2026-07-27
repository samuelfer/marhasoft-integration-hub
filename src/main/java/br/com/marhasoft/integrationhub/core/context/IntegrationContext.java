package br.com.marhasoft.integrationhub.core.context;

import br.com.marhasoft.integrationhub.core.configuration.EnvironmentType;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.configuration.Organization;
import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class IntegrationContext<T, R> {

    private T request;
    private R mappedRequest;
    private IntegrationConnector<T, R> connector;
    private ConnectorMetadata metadata;
    private IntegrationConfiguration configuration;

    private final IntegrationResult result = new IntegrationResult();

    private final Map<String, Object> attributes = new HashMap<>();

    public Organization getOrganization() {
        return configuration != null ? configuration.getOrganization() : null;
    }

    public Integer getExercicio() {
        return configuration != null ? configuration.getExercicio() : null;
    }

    public EnvironmentType getEnvironment() {
        return configuration != null ? configuration.getEnvironment() : null;
    }
}