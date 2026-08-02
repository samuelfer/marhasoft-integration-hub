package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.result.MessageType;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationStepTest {

    private final ValidationStep<String, Object> step =
            new ValidationStep<>();

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve retornar a fase de validação")
    void deveRetornarPhase() {

        assertThat(step.phase())
                .isEqualTo(PipelinePhase.VALIDATION);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve adicionar os erros da validação ao contexto")
    void deveAdicionarErrosAoContexto() {

        IntegrationConnector<String, Object> connector =
                mock(IntegrationConnector.class);

        IntegrationContext<String, Object> context =
                new IntegrationContext<>(
                        "request",
                        connector,
                        null);

        ValidationResult validation = ValidationResult.valid();
        validation.addError(TestValidationCode.CODIGO, "Mensagem");

        when(connector.validate(context))
                .thenReturn(validation);

        // Executa o step
        step.execute(context);

        // Agora verifica o resultado
        assertThat(context.getResult().hasErrors())
                .isTrue();

        assertThat(context.getResult().getMessages())
                .singleElement()
                .satisfies(message -> {
                    assertThat(message.type()).isEqualTo(MessageType.ERROR);
                    assertThat(message.code()).isEqualTo(TestValidationCode.CODIGO.name());
                    assertThat(message.message()).isEqualTo("Mensagem");
                });

        verify(connector).validate(context);
        verifyNoMoreInteractions(connector);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Não deve adicionar erros quando a validação for válida")
    void naoDeveAdicionarErrosQuandoValidacaoValida() {

        IntegrationConnector<String, Object> connector =
                mock(IntegrationConnector.class);

        IntegrationContext<String, Object> context =
                new IntegrationContext<>(
                        "request",
                        connector,
                        null);

        when(connector.validate(context))
                .thenReturn(ValidationResult.valid());

        step.execute(context);

        assertThat(context.getResult().getMessages())
                .isEmpty();

        assertThat(context.getResult().hasErrors())
                .isFalse();

        verify(connector).validate(context);
        verifyNoMoreInteractions(connector);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Deve lançar exceção quando o connector retornar ValidationResult nulo")
    void deveLancarExcecaoQuandoValidationResultForNulo() {

        IntegrationConnector<String, Object> connector =
                mock(IntegrationConnector.class);

        IntegrationContext<String, Object> context =
                new IntegrationContext<>(
                        "request",
                        connector,
                        null);

        when(connector.validate(context))
                .thenReturn(null);

        assertThatThrownBy(() -> step.execute(context))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O conector retornou um ValidationResult nulo.");

        verify(connector).validate(context);
        verifyNoMoreInteractions(connector);
    }

    private enum TestValidationCode {
        CODIGO
    }
}