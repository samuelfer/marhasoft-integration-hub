package br.com.marhasoft.integrationhub.core.dependencies.resolver;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;

public interface DependencyResolver<T, R> {

    /**
     * Retorna a chave que identifica para qual integração este resolvedor
     * deve ser executado.
     */
    DependencyKey getDependencyKey();

    /**
     * Define a ordem de execução quando houver mais de um resolvedor para a
     * mesma integração.
     */
    default int getOrder() {
        return 0;
    }

    /**
     * Indica se este resolvedor deve ser executado para o contexto informado.
     */
    default boolean supports(IntegrationContext<T, R> context) {
        return true;
    }

    /**
     * Resolve a dependência.
     */
    void resolve(IntegrationContext<T, R> context);

}