package br.com.marhasoft.integrationhub.modules.frotas.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationError;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoRequest;
import br.com.marhasoft.integrationhub.support.VeiculoTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class VeiculoValidatorTest {

    private VeiculoValidator validator;

    @BeforeEach
    void setUp() {
        validator = new VeiculoValidator();
    }

    @Test
    @DisplayName("Deve validar com sucesso quando a requisição for válida")
    void deveValidarComSucessoQuandoRequestForValido() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertThat(result.isValid()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro quando a placa não for informada")
    void deveRetornarErroQuandoPlacaNaoInformada() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setPlaca(null);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.PLACA_OBRIGATORIA);
    }

    @Test
    @DisplayName("Deve retornar erro quando o ano do modelo não for informado")
    void deveRetornarErroQuandoAnoModeloNaoInformado() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setAnoModelo(null);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.ANO_MODELO_OBRIGATORIO);
    }

    @Test
    @DisplayName("Deve retornar erro quando o RENAVAM não for informado")
    void deveRetornarErroQuandoNumeroRenavanNaoInformado() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setNumeroRenavan(null);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.RENAVAM_OBRIGATORIO);
    }

    @Test
    @DisplayName("Deve retornar erro quando o modelo não for informado")
    void deveRetornarErroQuandoNumeroModeloNaoInformado() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setNumeroModelo(null);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.MODELO_OBRIGATORIO);
    }

    @Test
    @DisplayName("Deve retornar erro quando o tipo da frota não for informado")
    void deveRetornarErroQuandoTipoFrotaNaoInformado() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setTipoFrota(null);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.TIPO_FROTA_OBRIGATORIO);
    }

    @Test
    @DisplayName("Deve retornar erro quando o proprietário não for informado")
    void deveRetornarErroQuandoProprietarioNaoInformado() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setCpfCnpjProprietario(null);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.PROPRIETARIO_OBRIGATORIO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "5", "A", "10"})
    @DisplayName("Deve retornar erro quando o tipo da frota for inválido")
    void deveRetornarErroQuandoTipoFrotaForInvalido(String tipo) {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setTipoFrota(tipo);

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertErro(result, VeiculoValidationCode.TIPO_FROTA_INVALIDO);
    }

    @Test
    @DisplayName("Deve permitir veículo próprio da Unidade Gestora")
    void devePermitirVeiculoProprioDaUnidadeGestora() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        ValidationResult result = validator.validate(request, criarContextoValido(request));

        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar erro quando o proprietário do veículo próprio não for a Unidade Gestora")
    void deveRetornarErroQuandoVeiculoProprioNaoPertencerAUnidadeGestora() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        IntegrationContext<?, ?, ?> context = new IntegrationContext<>(null, null, null);
        context.putAttribute("cnpjUnidadeGestora", "99999999999999");

        ValidationResult result = validator.validate(request, context);

        assertErro(result, VeiculoValidationCode.VEICULO_PROPRIO_CNPJ_UNIDADE_GESTORA);
    }

    private IntegrationContext<?, ?, ?> criarContextoValido(VeiculoRequest request) {

        IntegrationContext<?, ?, ?> context =
                new IntegrationContext<>(null, null, null);

        context.putAttribute(
                "cnpjUnidadeGestora",
                request.getCpfCnpjProprietario());

        return context;
    }

    private void assertErro(
            ValidationResult result,
            VeiculoValidationCode codigoEsperado) {

        assertThat(result.isValid()).isFalse();

        assertThat(result.getErrors())
                .extracting(ValidationError::code)
                .contains(codigoEsperado.name());
    }

}