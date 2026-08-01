package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota;

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
import br.com.marhasoft.integrationhub.modules.frotas.locador.LocadorConnector;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.ProprietarioConnector;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.validation.SituacaoFrotaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class SituacaoFrotaConnector implements IntegrationConnector<
        SituacaoFrotaBatchRequest,
        SituacaoFrotaPayload> {

    private final IntegrationExecutor executor;

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final SituacaoFrotaMapper mapper;
    private final SituacaoFrotaValidator validator;
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
    public ValidationResult validate(IntegrationContext<SituacaoFrotaBatchRequest,
            SituacaoFrotaPayload> context) {
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
            IntegrationContext<SituacaoFrotaBatchRequest, SituacaoFrotaPayload> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<SituacaoFrotaBatchRequest, SituacaoFrotaPayload> context) {

        IntegrationResult result =
                client.cadastrarSituacaoFrota(context.getMappedPayload());

        context.getResult().merge(result);
    }

    /**
     * Executa as validações de negócio para todos os veículos do lote.
     */
    private ValidationResult validateSituacaoFrota(SituacaoFrotaBatchRequest request,
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