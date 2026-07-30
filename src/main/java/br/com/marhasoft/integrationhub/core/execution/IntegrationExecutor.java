package br.com.marhasoft.integrationhub.core.execution;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;

/**
 * Define o contrato responsável por executar uma integração.
 *
 * <p>O executor coordena a execução do pipeline, delegando ao connector as
 * regras específicas da integração, como validação, mapeamento e envio ao
 * sistema externo.</p>
 *
 * <p>Exemplo de utilização:</p>
 *
 * <pre>{@code
 * Organization organization =
 *         Organization.builder()
 *                 .codigo("123")
 *                 .cnpj("12345678000199")
 *                 .nome("Prefeitura Municipal")
 *                 .build();
 *
 * IntegrationConfiguration configuration =
 *         IntegrationConfiguration.builder()
 *                 .organization(organization)
 *                 .environment(EnvironmentType.PRODUCTION)
 *                 .exercicio(2026)
 *                 .build();
 *
 * IntegrationResult result =
 *         integrationExecutor.execute(
 *                 veiculoConnector,
 *                 request,
 *                 configuration);
 * }</pre>
 */
public interface IntegrationExecutor {

    /**
     * Executa uma integração utilizando o connector informado.
     *
     * @param connector connector responsável pela integração.
     * @param request requisição recebida da aplicação consumidora.
     * @param configuration configuração utilizada durante a execução da
     * integração.
     * @param <T> tipo da requisição.
     * @param <P> tipo do payload enviado ao sistema externo.
     * @return resultado da execução da integração.
     */
    <T, P> IntegrationResult execute(
            IntegrationConnector<T, P> connector,
            T request,
            IntegrationConfiguration configuration);

}