package br.com.marhasoft.integrationhub.modules.frotas.locador.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.validation.ProprietarioValidationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LocadorValidator {

    public ValidationResult validate(
            LocadorRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            LocadorRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        LocadorBatchRequest batch =
                context.getRequest(LocadorBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {
            result.addError(
                    LocadorValidationCode.LOCADOR_PRESTADOR_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe um locador cadastrado com o CPF/CNPJ %s.",
                            request.getCpfCnpj()));
        }
    }

    /**
     * Verifica se dois locadores possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            LocadorRequest locador1,
            LocadorRequest locador2) {

        return Objects.equals(locador1.getCpfCnpj(), locador2.getCpfCnpj())
               && Objects.equals(locador1.getNome(), locador2.getNome());

        // TODO Adicionar o Código da Unidade Gestora quando este campo
        // passar a ser informado pela API.
    }

}