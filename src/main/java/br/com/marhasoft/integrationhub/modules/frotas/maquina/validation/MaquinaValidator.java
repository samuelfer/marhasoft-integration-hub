package br.com.marhasoft.integrationhub.modules.frotas.maquina.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.enums.TipoFrotaEnum;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MaquinaValidator {

    public ValidationResult validate(
            MaquinaRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarTipoFrota(request, result);
        validarDuplicidade(request, context, result);
        validarProprietarioUnidadeGestora(request, context, result);

        // TODO Validar MAQUINA_PROPRIETARIO_CADASTRADO
        // TODO Validar MAQUINA_LOCADOR_CADASTRADO
        // TODO Validar MAQUINA_PROPRIETARIO_LOCADOR_MUDANCA

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            MaquinaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        MaquinaBatchRequest batch =
                context.getRequest(MaquinaBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item ->
                        Objects.equals(item.getCodigo(), request.getCodigo()))
                .count();

        if (quantidade > 1) {
            result.addError(
                    MaquinaValidationCode.MAQUINA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma máquina cadastrada com o código %s.",
                            request.getCodigo()));
        }
    }

    private void validarTipoFrota(
            MaquinaRequest request,
            ValidationResult result) {

        if (!TipoFrotaEnum.isValido(request.getTipoFrota())) {
            result.addError(
                    MaquinaValidationCode.MAQUINA_TIPO_FROTA_VALIDO,
                    String.format(
                            "O tipo de frota '%s' é inválido.",
                            request.getTipoFrota()));
        }
    }

    /**
     * Nos bens próprios, o proprietário deve ser a Unidade Gestora.
     */
    private void validarProprietarioUnidadeGestora(
            MaquinaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        // Tipo 1 = Próprio
        if (!TipoFrotaEnum.PROPRIO.getCodigo().equals(request.getTipoFrota())) {
            return;
        }

        String cnpjUG = context.getConfiguration()
                .getOrganization()
                .getCnpj();

        if (!Objects.equals(request.getCpfCnpjProprietario(), cnpjUG)) {

            result.addError(
                    MaquinaValidationCode.MAQUINA_PROPRIO_CNPJ_UNIDADE_GESTORA,
                    String.format(
                            "Na máquina %s, sendo do tipo próprio, o proprietário deve ser a Unidade Gestora.",
                            request.getCodigo()));
        }
    }

}