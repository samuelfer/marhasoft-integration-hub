package br.com.marhasoft.integrationhub.core.dependencies.registry;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;

public record DependencyKey(
        IntegrationModule module,
        IntegrationOperation operation,
        IntegrationAction action) {
}