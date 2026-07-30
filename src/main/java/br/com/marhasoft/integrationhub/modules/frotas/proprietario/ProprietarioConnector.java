package br.com.marhasoft.integrationhub.modules.frotas.proprietario;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioPayload;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioResponse;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.validation.ProprietarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProprietarioConnector implements IntegrationConnector<
        ProprietarioBatchRequest,
        ProprietarioPayload,
        ProprietarioResponse> {

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final ProprietarioMapper mapper;
    private final ProprietarioValidator validator;
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
    public ValidationResult validate(IntegrationContext<ProprietarioBatchRequest,
                    ProprietarioPayload, ProprietarioResponse> context) {
        return validateProprietarios(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<ProprietarioBatchRequest, ProprietarioPayload,
                    ProprietarioResponse> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(IntegrationContext<ProprietarioBatchRequest, ProprietarioPayload,
                    ProprietarioResponse> context) {

        context.setResponse(
                client.cadastrarProprietario(
                        context.getMappedPayload()));
    }

    /**
     * Executa as validações de negócio para todos os veículos do lote.
     */
    private ValidationResult validateProprietarios(ProprietarioBatchRequest request,
            IntegrationContext<?, ?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        request.getElementos() // ou getProprietarios(), conforme sua classe
                .forEach(proprietario ->
                        result.merge(
                                validator.validate(
                                        proprietario,
                                        context)));

        return result;
    }
}