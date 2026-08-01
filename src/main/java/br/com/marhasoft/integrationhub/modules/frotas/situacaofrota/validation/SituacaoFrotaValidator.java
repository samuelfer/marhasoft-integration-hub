package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SituacaoFrotaValidator {

    public ValidationResult validate(
            SituacaoFrotaRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            SituacaoFrotaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        SituacaoFrotaBatchRequest batch =
                context.getRequest(SituacaoFrotaBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item ->
                        item.getCodigo().equals(request.getCodigo())
                        && item.getDataSituacao().equals(request.getDataSituacao()))
                .count();

        if (quantidade > 1) {
            result.addError(
                    SituacaoFrotaValidationCode.SITUACAO_FROTA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma situação para o código %s na data %s.",
                            request.getCodigo(),
                            request.getDataSituacao()));
        }
    }

}