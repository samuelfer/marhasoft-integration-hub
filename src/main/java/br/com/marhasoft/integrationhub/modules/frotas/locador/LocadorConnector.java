package br.com.marhasoft.integrationhub.modules.frotas.locador;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorPayload;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorResponse;
import br.com.marhasoft.integrationhub.modules.frotas.locador.validation.LocadorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocadorConnector implements IntegrationConnector<
        LocadorBatchRequest,
        LocadorPayload,
        LocadorResponse> {

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final LocadorMapper mapper;
    private final LocadorValidator validator;
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
    public ValidationResult validate(IntegrationContext<LocadorBatchRequest,
            LocadorPayload, LocadorResponse> context) {
        return validateLocadores(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<LocadorBatchRequest, LocadorPayload,
                    LocadorResponse> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<LocadorBatchRequest, LocadorPayload,
            LocadorResponse> context) {

        context.setResponse(
                client.cadastrarLocador(
                        context.getMappedPayload()));
    }

    /**
     * Executa as validações de negócio para todos os locadores do lote.
     */
    private ValidationResult validateLocadores(LocadorBatchRequest request,
                                                   IntegrationContext<?, ?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos()
                .forEach(locador ->
                        result.merge(
                                validator.validate(
                                        locador,
                                        context)));

        return result;
    }
}