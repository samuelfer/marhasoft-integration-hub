package br.com.marhasoft.integrationhub.modules.frotas.application.mapper;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.VeiculoMapper;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoPayload;
import br.com.marhasoft.integrationhub.support.VeiculoTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VeiculoMapperTest {

    private static final IntegrationAction ACTION = IntegrationAction.CREATE;

    private VeiculoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new VeiculoMapper();
    }

    @Test
    @DisplayName("Deve mapear todos os campos da requisição")
    void deveMapearTodosOsCampos() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        VeiculoPayload payload = mapper.toPayload(request, ACTION);

        assertThat(payload).isNotNull();
        assertThat(payload.getElementos())
                .singleElement()
                .satisfies(item -> {
                    assertThat(item.getPlaca()).isEqualTo(request.getPlaca());
                    assertThat(item.getAnoModelo()).isEqualTo(request.getAnoModelo());
                    assertThat(item.getNumeroRenavan()).isEqualTo(request.getNumeroRenavan());
                    assertThat(item.getNumeroModelo()).isEqualTo(request.getNumeroModelo());
                    assertThat(item.getTipoFrota()).isEqualTo(request.getTipoFrota());
                    assertThat(item.getCpfCnpjProprietario()).isEqualTo(request.getCpfCnpjProprietario());
                    assertThat(item.getCpfCnpjLocador()).isEqualTo(request.getCpfCnpjLocador());
                    assertThat(item.getAction()).isEqualTo(ACTION);
                });
    }

    @Test
    @DisplayName("Deve preencher o timestamp")
    void devePreencherTimestamp() {

        LocalDateTime antes = LocalDateTime.now();

        VeiculoPayload payload =
                mapper.toPayload(VeiculoTestDataFactory.umVeiculo(), ACTION);

        LocalDateTime depois = LocalDateTime.now();

        assertThat(payload.getTimestamp())
                .isNotNull()
                .isBetween(antes, depois);
    }

    @Test
    @DisplayName("Deve permitir locador nulo")
    void devePermitirLocadorNulo() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setCpfCnpjLocador(null);

        VeiculoPayload payload = mapper.toPayload(request, ACTION);

        assertThat(payload.getElementos())
                .singleElement()
                .extracting(VeiculoItemPayload::getCpfCnpjLocador)
                .isNull();
    }

    @ParameterizedTest
    @EnumSource(IntegrationAction.class)
    @DisplayName("Deve mapear corretamente todas as ações")
    void deveMapearTodasAsActions(IntegrationAction action) {

        VeiculoPayload payload =
                mapper.toPayload(VeiculoTestDataFactory.umVeiculo(), action);

        assertThat(payload.getElementos())
                .singleElement()
                .extracting(VeiculoItemPayload::getAction)
                .isEqualTo(action);
    }
}