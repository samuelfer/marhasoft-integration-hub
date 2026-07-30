package br.com.marhasoft.integrationhub.core.execution;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.pipeline.IntegrationPipeline;
import br.com.marhasoft.integrationhub.core.pipeline.PipelineStep;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Implementação padrão responsável por executar uma integração.
 *
 * <p>Este executor cria o contexto da integração, monta o pipeline com todas
 * as etapas registradas na aplicação, executa o fluxo de processamento e
 * retorna o resultado da integração.</p>
 *
 * <p>Os conectores permanecem responsáveis apenas por implementar as regras de
 * negócio específicas da integração (validação, mapeamento e envio), enquanto
 * este executor coordena a execução do pipeline.</p>
 */
@Component
@RequiredArgsConstructor
public class DefaultIntegrationExecutor
        implements IntegrationExecutor {

    /**
     * Etapas do pipeline registradas na aplicação.
     *
     * <p>As etapas são injetadas automaticamente pelo Spring e ordenadas pelo
     * próprio {@link IntegrationPipeline} de acordo com a fase de execução.</p>
     */
    private final List<PipelineStep<?, ?>> steps;

    /**
     * Executa uma integração utilizando o connector informado.
     *
     * <p>O método valida os parâmetros obrigatórios, cria o contexto da
     * integração, executa o pipeline completo e retorna o resultado da
     * execução.</p>
     *
     * @param connector responsável pela integração a ser executada.
     * @param request requisição recebida da aplicação consumidora.
     * @param configuration configuração utilizada durante a integração.
     * @param <T> tipo da requisição.
     * @param <P> tipo do payload enviado ao sistema externo.
     * @return resultado da execução da integração.
     * @throws NullPointerException caso algum parâmetro obrigatório seja nulo.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, P> IntegrationResult execute(
            IntegrationConnector<T, P> connector,
            T request,
            IntegrationConfiguration configuration) {

        Objects.requireNonNull(connector, "O connector é obrigatório.");
        Objects.requireNonNull(request, "A requisição é obrigatória.");
        Objects.requireNonNull(configuration,
                "A configuração da integração é obrigatória.");

        IntegrationContext<T, P> context =
                new IntegrationContext<>(
                        request,
                        connector,
                        configuration);

        IntegrationPipeline<T, P> pipeline =
                new IntegrationPipeline<>(
                        (List<PipelineStep<T, P>>) (List<?>) steps);

        pipeline.execute(context);
        return context.getResult();
    }
}