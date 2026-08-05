package br.com.marhasoft.integrationhub.modules.frotas.abastecimento;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.validation.AbastecimentoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AbastecimentoConnector implements IntegrationConnector<
        AbastecimentoBatchRequest,
        AbastecimentoPayload> {

    private final IntegrationExecutor executor;

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final AbastecimentoMapper mapper;
    private final AbastecimentoValidator validator;
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
    public ValidationResult validate(IntegrationContext<AbastecimentoBatchRequest,
            AbastecimentoPayload> context) {
        return validateSituacaoFrota(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<AbastecimentoBatchRequest, AbastecimentoPayload> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<AbastecimentoBatchRequest, AbastecimentoPayload> context) {

        IntegrationResult result =
                client.cadastrarAbastecimento(context.getMappedPayload());

        context.getResult().merge(result);
    }

    /**
     * Executa as validações de negócio para todos os veículos do lote.
     */
    private ValidationResult validateSituacaoFrota(AbastecimentoBatchRequest request,
                                                   IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos()
            .forEach(veiculo ->
                    result.merge(
                            validator.validate(
                                    veiculo,
                                    context)));

        return result;
    }
}