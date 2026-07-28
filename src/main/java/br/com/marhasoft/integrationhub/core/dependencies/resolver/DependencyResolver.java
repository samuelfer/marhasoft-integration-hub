package br.com.marhasoft.integrationhub.core.dependencies.resolver;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;

public interface DependencyResolver<T, R> {

    DependencyKey key();

    default boolean supports(IntegrationContext<T, R> context) {
        return true;
    }

    void resolve(IntegrationContext<T, R> context);

}