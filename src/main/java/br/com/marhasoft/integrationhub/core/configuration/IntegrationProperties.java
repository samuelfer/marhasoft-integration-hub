package br.com.marhasoft.integrationhub.core.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntegrationProperties {

    private final Map<String, Object> properties = new HashMap<>();

    public <T> void put(String key, T value) {
        properties.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {

        Object value = properties.get(key);

        if (value == null) {
            return null;
        }

        return type.cast(value);
    }

    public boolean contains(String key) {
        return properties.containsKey(key);
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(properties);
    }

}