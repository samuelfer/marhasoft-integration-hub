package br.com.marhasoft.integrationhub.modules.frotas.veiculo.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.enums.TipoFrotaEnum;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.validation.MaquinaValidationCode;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VeiculoValidator {

    public ValidationResult validate(
            VeiculoRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarTipoFrota(request, result);
        validarDuplicidade(request, context, result);
        validarProprietarioUnidadeGestora(request, context, result);

        return result;
    }

    private void validarTipoFrota(
            VeiculoRequest request,
            ValidationResult result) {

        if (!TipoFrotaEnum.isValido(request.getTipoFrota())) {
            result.addError(
                    VeiculoValidationCode.VEICULO_TIPO_FROTA_VALIDO,
                    String.format(
                            "O tipo de frota '%s' é inválido.",
                            request.getTipoFrota()));
        }
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            VeiculoRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        VeiculoBatchRequest batch =
                context.getRequest(VeiculoBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {
            result.addError(
                    VeiculoValidationCode.VEICULO_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe um veículo com a mesma chave de unicidade (placa %s).",
                            request.getPlaca()));
        }
    }

    private void validarProprietarioUnidadeGestora(
            VeiculoRequest request, IntegrationContext<?, ?> context,
            ValidationResult result) {

        if (!TipoFrotaEnum.PROPRIO.getCodigo().equals(request.getTipoFrota())) {
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

    /**
     * Verifica se dois veículos possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            VeiculoRequest veiculo1,
            VeiculoRequest veiculo2) {

        return Objects.equals(veiculo1.getPlaca(), veiculo2.getPlaca())
               && Objects.equals(veiculo1.getAnoModelo(), veiculo2.getAnoModelo())
               && Objects.equals(veiculo1.getNumeroRenavan(), veiculo2.getNumeroRenavan())
               && Objects.equals(veiculo1.getCpfCnpjProprietario(), veiculo2.getCpfCnpjProprietario())
               && Objects.equals(veiculo1.getCpfCnpjLocador(), veiculo2.getCpfCnpjLocador());

        // TODO Adicionar o Código da Unidade Gestora quando este campo
        // passar a ser informado pela API.
    }

}
