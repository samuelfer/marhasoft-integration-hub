package br.com.marhasoft.integrationhub.core.dependencies.registry;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DependencyRegistry {

    private final Map<DependencyKey, List<RegisteredDependencyResolver>> registry;

    public DependencyRegistry(List<DependencyResolver<?, ?, ?>> resolvers) {
        this.registry = buildRegistry(resolvers);
    }

    /**
     * Resolve as dependências aplicáveis ao contexto informado.
     *
     * <p>Os resolvedores são localizados a partir da chave da integração e
     * executados na ordem previamente registrada. Antes da execução, cada
     * resolvedor é consultado por meio do método {@code supports()} para
     * verificar se ele deve atuar no contexto atual.</p>
     *
     * @param context contexto da integração em execução.
     * @param <T> tipo da requisição original.
     * @param <P> tipo do payload mapeado.
     * @param <R> tipo da resposta.
     */
    @SuppressWarnings("unchecked")
    public <T, P, R> void resolve(IntegrationContext<T, P, R> context) {

        List<RegisteredDependencyResolver> resolvers =
                registry.getOrDefault(
                        context.getDependencyKey(),
                        List.of());

        for (RegisteredDependencyResolver registered : resolvers) {

            var resolver = registered.<T, P, R>resolverTyped();

            if (!resolver.supports(context)) {
                continue;
            }

            resolver.resolve(context);
        }
    }

    /**
     * Constrói o registro de resolvedores de dependências agrupando-os pela
     * combinação de módulo, operação e ação representada por um {@link DependencyKey}.
     *
     * <p>Cada {@link DependencyResolver} é registrado na chave correspondente e,
     * ao final, os resolvedores de uma mesma chave são ordenados pelo valor
     * retornado por {@code getOrder()}, garantindo uma execução previsível durante
     * a resolução das dependências.</p>
     *
     * @param resolvers lista de resolvedores de dependências disponíveis no contexto
     *                  da aplicação.
     * @return mapa contendo os resolvedores agrupados e ordenados por
     *         {@link DependencyKey}.
     */
    private Map<DependencyKey, List<RegisteredDependencyResolver>> buildRegistry(
            List<DependencyResolver<?, ?, ?>> resolvers) {

        Map<DependencyKey, List<RegisteredDependencyResolver>> registry = new HashMap<>();

        for (DependencyResolver<?, ?, ?> resolver : resolvers) {

            DependencyKey key = resolver.getDependencyKey();

            registry.computeIfAbsent(key, k -> new ArrayList<>())
                    .add(new RegisteredDependencyResolver(
                            resolver.getOrder(),
                            resolver));
        }

        registry.values().forEach(list ->
                list.sort(Comparator.comparingInt(
                        RegisteredDependencyResolver::order)));

        return registry;
    }
}