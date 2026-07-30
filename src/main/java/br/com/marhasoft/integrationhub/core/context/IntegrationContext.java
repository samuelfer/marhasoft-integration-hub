package br.com.marhasoft.integrationhub.core.context;

import br.com.marhasoft.integrationhub.core.configuration.EnvironmentType;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.configuration.Organization;
import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class IntegrationContext<T, P> {

    private final T request;

    @Setter
    private P mappedPayload;

    private final IntegrationConnector<T, P> connector;

    private final IntegrationConfiguration configuration;

    private final IntegrationResult result = new IntegrationResult();

    private final Map<String, Object> attributes = new HashMap<>();

    public IntegrationContext(
            T request,
            IntegrationConnector<T, P> connector,
            IntegrationConfiguration configuration) {

        this.request = request;
        this.connector = connector;
        this.configuration = configuration;
    }

    public ConnectorMetadata getMetadata() {
        return connector.getMetadata();
    }

    public Organization getOrganization() {
        return configuration != null ? configuration.getOrganization() : null;
    }

    public Integer getExercicio() {
        return configuration != null ? configuration.getExercicio() : null;
    }

    public EnvironmentType getEnvironment() {
        return configuration != null ? configuration.getEnvironment() : null;
    }

    public void putAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public <V> V getAttribute(String key, Class<V> type) {
        return type.cast(attributes.get(key));
    }


    /**
     * Retorna a chave utilizada para localizar os resolvedores de dependência
     * aplicáveis à integração em execução.
     *
     * @return chave composta pelo módulo, operação e ação da integração.
     */
    public DependencyKey getDependencyKey() {

        return new DependencyKey(
                getMetadata().module(),
                getMetadata().operation(),
                getMetadata().action());

    }

    public T getRequest() {
        return request;
    }

    public P getMappedPayload() {
        return mappedPayload;
    }


    public IntegrationConnector<T, P> getConnector() {
        return connector;
    }

    public IntegrationConfiguration getConfiguration() {
        return configuration;
    }

    public IntegrationResult getResult() {
        return result;
    }
}