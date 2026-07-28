package br.com.marhasoft.integrationhub.modules.frotas.connector;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.metadata.ConnectorMetadata;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoResponse;
import br.com.marhasoft.integrationhub.modules.frotas.application.mapper.VeiculoMapper;
import br.com.marhasoft.integrationhub.modules.frotas.domain.model.VeiculoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client.TceFrotasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeiculoConnector
        implements IntegrationConnector<VeiculoRequest, VeiculoResponse> {

    private final VeiculoMapper mapper;
    private final TceFrotasClient client;

    @Override
    public ConnectorMetadata getMetadata() {
        return new ConnectorMetadata(
                IntegrationModule.FROTAS,
                IntegrationOperation.VEICULO,
                IntegrationAction.CREATE
        );
    }

    /**
     * Valida a requisição antes do processamento da integração.
     */
    @Override
    public ValidationResult validate(
            IntegrationContext<VeiculoRequest, VeiculoResponse> context) {

        // Inicialmente não existem validações.
        // Elas serão implementadas conforme as regras de negócio.
        return ValidationResult.valid();
    }

    /**
     * Converte a requisição recebida para o payload esperado pelo sistema
     * externo.
     */
    @Override
    public void map(
            IntegrationContext<VeiculoRequest, VeiculoResponse> context) {

        VeiculoPayload payload = mapper.toPayload(context.getRequest());

        context.setMappedPayload(payload);
    }

    /**
     * Envia o payload ao sistema externo e registra a resposta da integração.
     */
    @Override
    public void send(
            IntegrationContext<VeiculoRequest, VeiculoResponse> context) {

        VeiculoPayload payload = context.getMappedPayload();

        VeiculoResponse response = client.enviar(payload);

        context.setResponse(response);
    }
}