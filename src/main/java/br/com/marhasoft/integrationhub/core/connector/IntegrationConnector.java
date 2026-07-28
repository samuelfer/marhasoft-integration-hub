package br.com.marhasoft.integrationhub.core.connector;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public interface IntegrationConnector<T, R> {

    ConnectorMetadata getMetadata();

    ValidationResult validate(IntegrationContext<T, R> context);

    R map(IntegrationContext<T, R> context);

    void send(IntegrationContext<T, R> context);

}