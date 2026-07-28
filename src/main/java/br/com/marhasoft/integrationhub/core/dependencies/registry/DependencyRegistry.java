package br.com.marhasoft.integrationhub.core.dependencies.registry;

import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DependencyRegistry {

    private final Map<DependencyKey, List<RegisteredDependencyResolver>> registry;

    public DependencyRegistry(List<DependencyResolver<?, ?>> resolvers) {
        this.registry = buildRegistry(resolvers);
    }

    private Map<DependencyKey, List<RegisteredDependencyResolver>> buildRegistry(
            List<DependencyResolver<?, ?>> resolvers) {

        Map<DependencyKey, List<RegisteredDependencyResolver>> registry = new HashMap<>();

        // TODO implementar

        return registry;
    }
}