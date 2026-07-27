package br.com.marhasoft.integrationhub.core.metadata;

import org.springframework.http.HttpMethod;

public record ConnectorMetadata(
        String module,
        String entity,
        String endpoint,
        HttpMethod method,
        String version) {
}