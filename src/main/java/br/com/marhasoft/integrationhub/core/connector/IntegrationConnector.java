package br.com.marhasoft.integrationhub.core.connector;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;

public interface IntegrationConnector<T, P, R> {

    /**
     * Retorna os metadados que identificam a integração implementada por este
     * connector.
     */
    ConnectorMetadata getMetadata();

    /**
     * Valida a requisição antes da execução da integração.
     */
    ValidationResult validate(IntegrationContext<T, P, R> context);

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    void map(IntegrationContext<T, P, R> context);

    /**
     * Envia o payload para o sistema externo e registra a resposta da
     * integração no contexto.
     */
    void send(IntegrationContext<T, P, R> context);

}