package br.com.marhasoft.integrationhub.modules.frotas.veiculo;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoResponse;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation.VeiculoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeiculoConnector implements IntegrationConnector<
        VeiculoRequest,
        VeiculoPayload,
        VeiculoResponse> {

    private static final ConnectorMetadata METADATA =
            new ConnectorMetadata(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    private final VeiculoMapper mapper;
    private final VeiculoValidator validator;
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
    public ValidationResult validate(
            IntegrationContext<
                    VeiculoRequest,
                    VeiculoPayload,
                    VeiculoResponse> context) {

        return validator.validate(
                context.getRequest(),
                context);
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<
                    VeiculoRequest,
                    VeiculoPayload,
                    VeiculoResponse> context) {

        context.setMappedPayload(
                mapper.toPayload(
                        context.getRequest(),
                        getMetadata().action()));
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(
            IntegrationContext<
                    VeiculoRequest,
                    VeiculoPayload,
                    VeiculoResponse> context) {

        context.setResponse(
                client.cadastrarVeiculo(
                        context.getMappedPayload()));
    }
}