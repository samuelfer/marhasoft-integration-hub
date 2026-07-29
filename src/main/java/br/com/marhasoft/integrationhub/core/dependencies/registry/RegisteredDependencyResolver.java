package br.com.marhasoft.integrationhub.core.dependencies.registry;

import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;

public record RegisteredDependencyResolver(
        int order,

        DependencyResolver<?, ?, ?> resolver
) {

    @SuppressWarnings("unchecked")
    public <T, P, R> DependencyResolver<T, P, R> resolverTyped() {
        return (DependencyResolver<T, P, R>) resolver;
    }
}