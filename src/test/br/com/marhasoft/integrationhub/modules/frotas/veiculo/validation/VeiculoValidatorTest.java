package br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation;

import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.configuration.Organization;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationError;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation.VeiculoValidationCode;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation.VeiculoValidator;
import br.com.marhasoft.integrationhub.support.VeiculoTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VeiculoValidatorTest {

    private VeiculoValidator validator;

    @BeforeEach
    void setUp() {
        validator = new VeiculoValidator();
    }

    @Test
    @DisplayName("Deve validar com sucesso quando o veículo for válido")
    void deveValidarComSucesso() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        ValidationResult result =
                validator.validate(request, criarContexto(request));

        assertThat(result.isValid()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro quando existir veículo duplicado no lote")
    void deveRetornarErroQuandoExistirDuplicidade() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        VeiculoBatchRequest batch = VeiculoBatchRequest.builder()
                .elementos(List.of(request, request))
                .build();

        IntegrationContext<?, ?> context = criarContexto(request);
        context = mock(IntegrationContext.class);

        when(context.getRequest(VeiculoBatchRequest.class))
                .thenReturn(batch);

        when(context.getConfiguration())
                .thenReturn(criarConfiguracao(request.getCpfCnpjProprietario()));

        ValidationResult result =
                validator.validate(request, context);

        assertErro(
                result,
                VeiculoValidationCode.VEICULO_DUPLICIDADE_NAO_PERMITIDA);
    }

    @Test
    @DisplayName("Deve retornar erro quando veículo próprio não pertencer à Unidade Gestora")
    void deveRetornarErroQuandoVeiculoProprioNaoForDaUG() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();

        IntegrationContext<?, ?> context =
                criarContextoComCnpj("99999999999999", request);

        ValidationResult result =
                validator.validate(request, context);

        assertErro(
                result,
                VeiculoValidationCode.VEICULO_PROPRIO_CNPJ_UNIDADE_GESTORA);
    }

    @Test
    @DisplayName("Não deve validar CNPJ da UG quando o veículo não for próprio")
    void naoDeveValidarCnpjDaUGQuandoNaoForProprio() {

        VeiculoRequest request = VeiculoTestDataFactory.umVeiculo();
        request.setTipoFrota("2");

        IntegrationContext<?, ?> context =
                criarContextoComCnpj("99999999999999", request);

        ValidationResult result =
                validator.validate(request, context);

        assertThat(result.isValid()).isTrue();
    }

    private IntegrationContext<?, ?> criarContexto(VeiculoRequest request) {
        return criarContextoComCnpj(
                request.getCpfCnpjProprietario(),
                request);
    }

    @SuppressWarnings("unchecked")
    private IntegrationContext<?, ?> criarContextoComCnpj(
            String cnpj,
            VeiculoRequest request) {

        IntegrationContext<?, ?> context =
                mock(IntegrationContext.class);

        VeiculoBatchRequest batch = VeiculoBatchRequest.builder()
                .elementos(List.of(request))
                .build();

        when(context.getRequest(VeiculoBatchRequest.class))
                .thenReturn(batch);

        when(context.getConfiguration())
                .thenReturn(criarConfiguracao(cnpj));

        return context;
    }

    private IntegrationConfiguration criarConfiguracao(String cnpj) {

        Organization organization =
                Organization.builder()
                        .cnpj(cnpj)
                        .build();

        return IntegrationConfiguration.builder()
                .organization(organization)
                .build();
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