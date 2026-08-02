package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class IntegrationPipelineTest {

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve executar todas as etapas na ordem correta")
    void deveExecutarTodasEtapasNaOrdemCorreta() {

        PipelineStep<String, Object> validation = mock(PipelineStep.class);
        PipelineStep<String, Object> mapping = mock(PipelineStep.class);
        PipelineStep<String, Object> transport = mock(PipelineStep.class);

        when(validation.phase()).thenReturn(PipelinePhase.VALIDATION);
        when(mapping.phase()).thenReturn(PipelinePhase.MAPPING);
        when(transport.phase()).thenReturn(PipelinePhase.TRANSPORT);

        IntegrationPipeline<String, Object> pipeline =
                new IntegrationPipeline<>(List.of(
                        transport,
                        mapping,
                        validation));

        IntegrationContext<String, Object> context =
                mock(IntegrationContext.class, RETURNS_DEEP_STUBS);

        when(context.getResult().hasErrors()).thenReturn(false);

        pipeline.execute(context);

        InOrder inOrder =
                inOrder(validation, mapping, transport);

        inOrder.verify(validation).execute(context);
        inOrder.verify(mapping).execute(context);
        inOrder.verify(transport).execute(context);

        verify(context.getResult()).setStatus(IntegrationStatus.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve interromper a execução quando houver erro")
    void deveInterromperExecucaoQuandoHouverErro() {

        PipelineStep<String, Object> validation = mock(PipelineStep.class);
        PipelineStep<String, Object> mapping = mock(PipelineStep.class);
        PipelineStep<String, Object> transport = mock(PipelineStep.class);

        when(validation.phase()).thenReturn(PipelinePhase.VALIDATION);
        when(mapping.phase()).thenReturn(PipelinePhase.MAPPING);
        when(transport.phase()).thenReturn(PipelinePhase.TRANSPORT);

        IntegrationPipeline<String, Object> pipeline =
                new IntegrationPipeline<>(List.of(
                        validation,
                        mapping,
                        transport));

        IntegrationContext<String, Object> context =
                mock(IntegrationContext.class, RETURNS_DEEP_STUBS);

        when(context.getResult().hasErrors())
                .thenReturn(true);

        pipeline.execute(context);

        verify(validation).execute(context);

        verify(mapping, never()).execute(any());
        verify(transport, never()).execute(any());

        verify(context.getResult())
                .setStatus(IntegrationStatus.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve retornar o mesmo contexto")
    void deveRetornarMesmoContexto() {

        PipelineStep<String, Object> step = mock(PipelineStep.class);

        when(step.phase()).thenReturn(PipelinePhase.VALIDATION);

        IntegrationPipeline<String, Object> pipeline =
                new IntegrationPipeline<>(List.of(step));

        IntegrationContext<String, Object> context =
                mock(IntegrationContext.class, RETURNS_DEEP_STUBS);

        when(context.getResult().hasErrors())
                .thenReturn(false);

        IntegrationContext<String, Object> result =
                pipeline.execute(context);

        assertThat(result).isSameAs(context);
    }

}