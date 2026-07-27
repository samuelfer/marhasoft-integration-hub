package br.com.marhasoft.integrationhub.core.context;

import java.util.HashMap;
import java.util.Map;

public class IntegrationContext<T,R> {

    private T request;
    private R mappedRequest;
    private final Map<String,Object> attributes = new HashMap<>();

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public R getMappedRequest() {
        return mappedRequest;
    }

    public void setMappedRequest(R mappedRequest) {
        this.mappedRequest = mappedRequest;
    }

    public <V> void putAttribute(String key, V value){
        attributes.put(key, value);
    }

    public <V> V getAttribute(String key, Class<V> type){
        return type.cast(attributes.get(key));
    }
}