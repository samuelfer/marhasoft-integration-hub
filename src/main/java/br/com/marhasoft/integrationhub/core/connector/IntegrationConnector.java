package br.com.marhasoft.integrationhub.core.connector;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public interface IntegrationConnector<T, R> {

    ConnectorMetadata metadata();

    ValidationResult validate(T request);

    R map(T request);

    void send(IntegrationContext<T, R> context);

}