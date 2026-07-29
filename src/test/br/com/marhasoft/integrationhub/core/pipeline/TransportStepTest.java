package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TransportStepTest {

    private final TransportStep<String, Object, Object> step =
            new TransportStep<>();

    @Test
    @DisplayName("Deve retornar a fase de transporte")
    void deveRetornarPhase() {

        assertThat(step.phase())
                .isEqualTo(PipelinePhase.TRANSPORT);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve delegar o envio para o connector")
    void deveDelegarEnvioParaConnector() {

        IntegrationConnector<String, Object, Object> connector =
                mock(IntegrationConnector.class);

        IntegrationContext<String, Object, Object> context =
                new IntegrationContext<>(
                        "request",
                        connector,
                        null);

        step.execute(context);

        verify(connector).send(context);
        verifyNoMoreInteractions(connector);
    }

}