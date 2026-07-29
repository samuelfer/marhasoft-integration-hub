package br.com.marhasoft.integrationhub.core.metadata;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;

public record ConnectorMetadata(

        IntegrationModule module,

        IntegrationOperation operation,

        IntegrationAction action
) {
}