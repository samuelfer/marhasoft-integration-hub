package br.com.marhasoft.integrationhub.core.dependencies.registry;

import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;

public record RegisteredDependencyResolver(

        DependencyKey key,

        int order,

        DependencyResolver<?, ?> resolver

) {
}