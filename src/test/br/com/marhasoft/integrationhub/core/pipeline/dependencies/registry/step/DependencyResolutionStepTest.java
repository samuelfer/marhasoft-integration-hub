package br.com.marhasoft.integrationhub.core.pipeline.dependencies.registry.step;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyRegistry;
import br.com.marhasoft.integrationhub.core.dependencies.step.DependencyResolutionStep;
import br.com.marhasoft.integrationhub.core.pipeline.PipelinePhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DependencyResolutionStepTest {

    @Mock
    private DependencyRegistry dependencyRegistry;

    @InjectMocks
    private DependencyResolutionStep<String, Object, Object> step;

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve retornar a fase de resolução de dependências")
    void deveRetornarPhase() {

        assertThat(step.phase())
                .isEqualTo(PipelinePhase.DEPENDENCY_RESOLUTION);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve delegar a resolução das dependências para o registry")
    void deveDelegarResolucaoDependencias() {

        IntegrationContext<String, Object, Object> context =
                mock(IntegrationContext.class);

        step.execute(context);

        verify(dependencyRegistry).resolve(context);
        verifyNoMoreInteractions(dependencyRegistry);
    }

}