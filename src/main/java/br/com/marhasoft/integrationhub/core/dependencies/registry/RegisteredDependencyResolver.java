package br.com.marhasoft.integrationhub.core.dependencies.registry;

import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;

public record RegisteredDependencyResolver(
        int order,

        DependencyResolver<?, ?> resolver
) {

    @SuppressWarnings("unchecked")
    public <T, P> DependencyResolver<T, P> resolverTyped() {
        return (DependencyResolver<T, P>) resolver;
    }
}