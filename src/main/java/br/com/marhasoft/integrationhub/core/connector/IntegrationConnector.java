package br.com.marhasoft.integrationhub.core.connector;

import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public interface IntegrationConnector<T,R> {

    ValidationResult validate(T request);

    R map(T request);

    ConnectorMetadata metadata();
}