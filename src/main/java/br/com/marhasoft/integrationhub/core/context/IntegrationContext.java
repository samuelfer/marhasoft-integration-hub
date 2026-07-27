package br.com.marhasoft.integrationhub.core.context;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntegrationContext<T, R> {

    public IntegrationContext(
            T request,
            IntegrationConnector<T, R> connector) {

        this.request = request;
        this.connector = connector;
        this.metadata = connector.metadata();
    }

    private T request;

    private R mappedRequest;

    private IntegrationConnector<T, R> connector;

    private ConnectorMetadata metadata;

    private final IntegrationResult result = new IntegrationResult();

    private final Map<String, Object> attributes = new HashMap<>();

    public T getRequest() {
        return request;
    }

    public IntegrationContext<T, R> setRequest(T request) {
        this.request = request;
        return this;
    }

    public R getMappedRequest() {
        return mappedRequest;
    }

    public IntegrationContext<T, R> setMappedRequest(R mappedRequest) {
        this.mappedRequest = mappedRequest;
        return this;
    }

    public IntegrationConnector<T, R> getConnector() {
        return connector;
    }

    public IntegrationContext<T, R> setConnector(IntegrationConnector<T, R> connector) {
        this.connector = connector;
        return this;
    }

    public ConnectorMetadata getMetadata() {
        return metadata;
    }

    public IntegrationContext<T, R> setMetadata(ConnectorMetadata metadata) {
        this.metadata = metadata;
        return this;
    }

    public IntegrationResult getResult() {
        return result;
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public <V> IntegrationContext<T, R> putAttribute(String key, V value) {
        attributes.put(key, value);
        return this;
    }

    public <V> V getAttribute(String key, Class<V> type) {
        Object value = attributes.get(key);

        if (value == null) {
            return null;
        }

        return type.cast(value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

}