package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria;


import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.validation.ReceitaOrcamentariaValidator;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceitaOrcamentariaConnector implements IntegrationConnector<ReceitaOrcamentariaBatchRequest, ReceitaOrcamentariaPayload> {

    private final IntegrationExecutor executor;

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final ReceitaOrcamentariaMapper mapper;
    private final ReceitaOrcamentariaValidator validator;
    private final TceFrotasClient client;

    /**
     * Retorna os metadados que identificam esta integração.
     */
    @Override
    public ConnectorMetadata getMetadata() {
        return METADATA;
    }

    /**
     * Valida a requisição antes da execução da integração.
     */
    @Override
    public ValidationResult validate(IntegrationContext<ReceitaOrcamentariaBatchRequest,
            ReceitaOrcamentariaPayload> context) {
        return validateReceitas(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<ReceitaOrcamentariaBatchRequest, ReceitaOrcamentariaPayload> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<ReceitaOrcamentariaBatchRequest, ReceitaOrcamentariaPayload> context) {

        context.getResult().merge(
                client.cadastrarReceitaOrcamentaria(
                        context.getMappedPayload()));
    }

    /**
     * Executa as validações de negócio para todos as ações do lote.
     */
    private ValidationResult validateReceitas(ReceitaOrcamentariaBatchRequest request,
                                           IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos()
                .forEach(receita ->
                        result.merge(
                                validator.validate(
                                        receita,
                                        context)));

        return result;
    }
}