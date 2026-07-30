package br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VeiculoValidator {

    public ValidationResult validate(
            VeiculoRequest request,
            IntegrationContext<?, ?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarProprietarioUnidadeGestora(request, context, result);

        return result;
    }

    private void validarProprietarioUnidadeGestora(
            VeiculoRequest request, IntegrationContext<?, ?, ?> context,
            ValidationResult result) {

        if (!"1".equals(request.getTipoFrota())) {
            return;
        }

        String cnpjUG = context.getConfiguration()
                .getOrganization()
                .getCnpj();

        if (!Objects.equals(request.getCpfCnpjProprietario(), cnpjUG)) {
            result.addError(
                    VeiculoValidationCode.VEICULO_PROPRIO_CNPJ_UNIDADE_GESTORA,
                    String.format(
                            "Veículo de placa %s: nos veículos próprios o proprietário deve ser a Unidade Gestora.",
                            request.getPlaca()
                    ));
        }
    }

}
