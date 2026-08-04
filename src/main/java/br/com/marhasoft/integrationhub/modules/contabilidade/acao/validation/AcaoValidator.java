package br.com.marhasoft.integrationhub.modules.contabilidade.acao.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AcaoValidator {

    public ValidationResult validate(
            AcaoRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        // TODO Validar se a unidade gestora está homologada no TCE.
        // Regra: UNIDADE_GESTORA_HOMOLOGADA

        // TODO Validar se o tipo da ação é válido para o exercício informado.
        // Regra: ACAO_TIPO_VALIDO

        // TODO Validar se apenas Prefeituras e Consórcios estão realizando
        // o envio das ações.
        // Regra: RESPONSABILIDADE_PROTOCOLO

        // TODO Validar se as unidades gestoras pertencem ao mesmo município.
        // Para Consórcio, permitir apenas a própria unidade gestora.
        // Regra: ACAO_ESCOPO_CADASTRO

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(AcaoRequest request, IntegrationContext<?, ?> context,
            ValidationResult result) {

        AcaoBatchRequest batch = context.getRequest(AcaoBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {
            result.addError(
                    AcaoValidationCode.ACAO_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma ação com o código %s para a unidade gestora %s.",
                            request.getCodigoAcao(),
                            request.getCodigoUnidadeGestora()));
        }
    }

    /**
     * Verifica se duas ações possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(AcaoRequest primeira, AcaoRequest segunda) {

        return Objects.equals(primeira.getCodigoUnidadeGestora(), segunda.getCodigoUnidadeGestora())
               && Objects.equals(primeira.getCodigoAcao(), segunda.getCodigoAcao());
    }
}