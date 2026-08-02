package br.com.marhasoft.integrationhub.modules.frotas.proprietario.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProprietarioValidator {

    public ValidationResult validate(
            ProprietarioRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            ProprietarioRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        ProprietarioBatchRequest batch =
                context.getRequest(ProprietarioBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {
            result.addError(
                    ProprietarioValidationCode.PROPRIETARIO_FROTA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe um proprietário cadastrado com o CPF/CNPJ %s.",
                            request.getCpfCnpj()));
        }
    }

    /**
     * Verifica se dois proprietários possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            ProprietarioRequest proprietario1,
            ProprietarioRequest proprietario2) {

        return Objects.equals(proprietario1.getCpfCnpj(), proprietario2.getCpfCnpj())
               && Objects.equals(proprietario1.getNome(), proprietario2.getNome());

        // TODO Adicionar o Código da Unidade Gestora quando este campo
        // passar a ser informado pela API.
    }


}