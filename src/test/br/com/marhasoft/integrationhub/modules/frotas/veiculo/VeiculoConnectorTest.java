package br.com.marhasoft.integrationhub.modules.frotas.veiculo;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.TceFrotasClient;
import br.com.marhasoft.integrationhub.modules.frotas.locador.LocadorConnector;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.ProprietarioConnector;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation.VeiculoValidator;
import br.com.marhasoft.integrationhub.support.VeiculoTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoConnectorTest {

    @Mock
    private IntegrationExecutor executor;

    @Mock
    private ProprietarioConnector proprietarioConnector;

    @Mock
    private LocadorConnector locadorConnector;

    @Mock
    private VeiculoMapper mapper;

    @Mock
    private VeiculoValidator validator;

    @Mock
    private TceFrotasClient client;

    @InjectMocks
    private VeiculoConnector connector;

    private VeiculoRequest request;
    private VeiculoBatchRequest batchRequest;

    @BeforeEach
    void setUp() {

        request = VeiculoTestDataFactory.umVeiculo();

        batchRequest = VeiculoBatchRequest.builder()
                .elementos(List.of(request))
                .build();
    }

    @Test
    @DisplayName("Deve retornar os metadados do connector")
    void deveRetornarMetadata() {

        var metadata = connector.getMetadata();

        assertThat(metadata.module()).isEqualTo(IntegrationModule.FROTAS);
        assertThat(metadata.operation()).isEqualTo(IntegrationOperation.VEICULO);
        assertThat(metadata.action()).isEqualTo(IntegrationAction.CREATE);
    }

    @Test
    @DisplayName("Deve validar todos os veículos do lote")
    void deveValidarLote() {

        IntegrationContext<VeiculoBatchRequest, VeiculoPayload> context =
                criarContexto();

        ValidationResult validation = ValidationResult.valid();

        when(validator.validate(request, context))
                .thenReturn(validation);

        ValidationResult result = connector.validate(context);

        assertThat(result.isValid()).isTrue();

        verify(validator).validate(request, context);
        verifyNoInteractions(mapper);
        verifyNoInteractions(client);
        verifyNoInteractions(executor);
    }

    @Test
    @DisplayName("Deve mapear o payload")
    void deveMapearPayload() {

        IntegrationContext<VeiculoBatchRequest, VeiculoPayload> context =
                criarContexto();

        VeiculoPayload payload = new VeiculoPayload();

        when(mapper.toPayload(batchRequest, IntegrationAction.CREATE))
                .thenReturn(payload);

        connector.map(context);

        assertThat(context.getMappedPayload())
                .isSameAs(payload);

        verify(mapper)
                .toPayload(batchRequest, IntegrationAction.CREATE);

        verifyNoInteractions(validator);
        verifyNoInteractions(client);
        verifyNoInteractions(executor);
    }

    @Test
    @DisplayName("Deve enviar o payload quando não houver dependências")
    void deveEnviarPayload() {

        IntegrationContext<VeiculoBatchRequest, VeiculoPayload> context =
                criarContexto();

        VeiculoPayload payload = new VeiculoPayload();

        context.setMappedPayload(payload);

        IntegrationResult response = new IntegrationResult();
        response.setStatus(IntegrationStatus.SUCCESS);

        when(client.cadastrarVeiculo(payload))
                .thenReturn(response);

        connector.send(context);

        assertThat(context.getResult().getStatus())
                .isEqualTo(IntegrationStatus.SUCCESS);

        verify(client).cadastrarVeiculo(payload);

        verifyNoInteractions(executor);
    }

    private IntegrationContext<VeiculoBatchRequest, VeiculoPayload> criarContexto() {

        return new IntegrationContext<>(
                batchRequest,
                connector,
                null);
    }

}