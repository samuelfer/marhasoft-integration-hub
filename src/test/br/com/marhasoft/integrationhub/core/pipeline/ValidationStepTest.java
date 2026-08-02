package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.connector.IntegrationConnector;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.result.MessageType;
import br.com.marhasoft.integrationhub.core.validation.BeanValidationService;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationStepTest {

    @Mock
    private BeanValidationService beanValidationService;

    @Mock
    private IntegrationConnector<String, Object> connector;

    @InjectMocks
    private ValidationStep<String, Object> step;

    private IntegrationContext<String, Object> context;

    @BeforeEach
    void setUp() {
        context = new IntegrationContext<>(
                "request",
                connector,
                null);
    }

    @Test
    @DisplayName("Deve retornar a fase de validação")
    void deveRetornarPhase() {

        assertThat(step.phase())
                .isEqualTo(PipelinePhase.VALIDATION);
    }

    @Test
    @DisplayName("Deve adicionar os erros da Bean Validation e do Connector ao contexto")
    void deveAdicionarErrosAoContexto() {

        ValidationResult beanValidation = ValidationResult.valid();

        ValidationResult connectorValidation = ValidationResult.valid();
        connectorValidation.addError(TestValidationCode.CODIGO, "Mensagem");

        when(beanValidationService.validate("request"))
                .thenReturn(beanValidation);

        when(connector.validate(context))
                .thenReturn(connectorValidation);

        step.execute(context);

        assertThat(context.getResult().hasErrors())
                .isTrue();

        assertThat(context.getResult().getMessages())
                .singleElement()
                .satisfies(message -> {
                    assertThat(message.type()).isEqualTo(MessageType.ERROR);
                    assertThat(message.code()).isEqualTo(TestValidationCode.CODIGO.name());
                    assertThat(message.message()).isEqualTo("Mensagem");
                });

        verify(beanValidationService).validate("request");
        verify(connector).validate(context);
        verifyNoMoreInteractions(beanValidationService, connector);
    }

    @Test
    @DisplayName("Não deve adicionar erros quando todas as validações forem válidas")
    void naoDeveAdicionarErrosQuandoValidacaoValida() {

        when(beanValidationService.validate("request"))
                .thenReturn(ValidationResult.valid());

        when(connector.validate(context))
                .thenReturn(ValidationResult.valid());

        step.execute(context);

        assertThat(context.getResult().hasErrors())
                .isFalse();

        assertThat(context.getResult().getMessages())
                .isEmpty();

        verify(beanValidationService).validate("request");
        verify(connector).validate(context);
        verifyNoMoreInteractions(beanValidationService, connector);
    }

    @Test
    @DisplayName("Deve mesclar erros da Bean Validation e do Connector")
    void deveMesclarErros() {

        ValidationResult beanValidation = ValidationResult.valid();
        beanValidation.addError(TestValidationCode.CODIGO, "Erro Bean");

        ValidationResult connectorValidation = ValidationResult.valid();
        connectorValidation.addError(TestValidationCode.CODIGO, "Erro Connector");

        when(beanValidationService.validate("request"))
                .thenReturn(beanValidation);

        when(connector.validate(context))
                .thenReturn(connectorValidation);

        step.execute(context);

        assertThat(context.getResult().getMessages())
                .hasSize(2);

        verify(beanValidationService).validate("request");
        verify(connector).validate(context);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o connector retornar ValidationResult nulo")
    void deveLancarExcecaoQuandoValidationResultForNulo() {

        when(beanValidationService.validate("request"))
                .thenReturn(ValidationResult.valid());

        when(connector.validate(context))
                .thenReturn(null);

        assertThatThrownBy(() -> step.execute(context))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("O conector retornou um ValidationResult nulo.");

        verify(beanValidationService).validate("request");
        verify(connector).validate(context);
        verifyNoMoreInteractions(beanValidationService, connector);
    }

    private enum TestValidationCode {
        CODIGO
    }
}