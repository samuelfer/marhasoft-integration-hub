package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MappingStepTest {

    private final MappingStep<String, Object> step =
            new MappingStep<>();

    @Test
    @DisplayName("Deve retornar a fase de mapeamento")
    void deveRetornarPhase() {

        assertThat(step.phase())
                .isEqualTo(PipelinePhase.MAPPING);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve delegar o mapeamento para o connector")
    void deveDelegarMapeamentoParaConnector() {

        IntegrationConnector<String, Object> connector =
                mock(IntegrationConnector.class);

        IntegrationContext<String, Object> context =
                new IntegrationContext<>(
                        "request",
                        connector,
                        null);

        step.execute(context);

        verify(connector).map(context);
        verifyNoMoreInteractions(connector);
    }

}