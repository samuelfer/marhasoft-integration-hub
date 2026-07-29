package br.com.marhasoft.integrationhub.modules.frotas.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VeiculoValidator {

    public ValidationResult validate(
            VeiculoRequest request,
            IntegrationContext<?, ?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarCamposObrigatorios(request, result);
        validarTipoFrota(request, result);
        validarProprietarioUnidadeGestora(request, context, result);

        return result;
    }

    private void validarCamposObrigatorios(
            VeiculoRequest request,
            ValidationResult result) {

        if (isBlank(request.getPlaca())) {
            result.addError(
                    VeiculoValidationCode.PLACA_OBRIGATORIA,
                    "A placa é obrigatória.");
        }

        if (isBlank(request.getAnoModelo())) {
            result.addError(
                    VeiculoValidationCode.ANO_MODELO_OBRIGATORIO,
                    "O ano do modelo é obrigatório.");
        }

        if (isBlank(request.getNumeroRenavan())) {
            result.addError(
                    VeiculoValidationCode.RENAVAM_OBRIGATORIO,
                    "O RENAVAM é obrigatório.");
        }

        if (isBlank(request.getNumeroModelo())) {
            result.addError(
                    VeiculoValidationCode.MODELO_OBRIGATORIO,
                    "O modelo é obrigatório.");
        }

        if (isBlank(request.getTipoFrota())) {
            result.addError(
                    VeiculoValidationCode.TIPO_FROTA_OBRIGATORIO,
                    "O tipo da frota é obrigatório.");
        }

        if (isBlank(request.getCpfCnpjProprietario())) {
            result.addError(
                    VeiculoValidationCode.PROPRIETARIO_OBRIGATORIO,
                    "O proprietário é obrigatório.");
        }
    }

    private void validarTipoFrota(
            VeiculoRequest request,
            ValidationResult result) {

        List<String> tiposValidos = List.of("1", "2", "3", "4");

        if (!isBlank(request.getTipoFrota())
                && !tiposValidos.contains(request.getTipoFrota())) {

            result.addError(
                    VeiculoValidationCode.TIPO_FROTA_INVALIDO,
                    "Tipo de frota inválido.");
        }
    }

    private void validarProprietarioUnidadeGestora(
            VeiculoRequest request,
            IntegrationContext<?, ?, ?> context,
            ValidationResult result) {

        if (!"1".equals(request.getTipoFrota())) {
            return;
        }

        String cnpjUG =
                context.getAttribute(
                        "cnpjUnidadeGestora",
                        String.class);

        if (!Objects.equals(
                request.getCpfCnpjProprietario(),
                cnpjUG)) {

            result.addError(
                    VeiculoValidationCode.VEICULO_PROPRIO_CNPJ_UNIDADE_GESTORA,
                    "Nos veículos próprios o proprietário deve ser a Unidade Gestora.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

}
