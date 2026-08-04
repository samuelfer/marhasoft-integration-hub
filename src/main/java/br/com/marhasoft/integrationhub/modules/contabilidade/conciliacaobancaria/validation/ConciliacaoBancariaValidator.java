package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ConciliacaoBancariaValidator {

    public ValidationResult validate(
            ConciliacaoBancariaRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        // TODO Validar se o tipo da conciliação é válido
        // para o exercício do envio.
        // Regra: CONCILIACAO_BANCARIA_TIPO_VALIDO

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            ConciliacaoBancariaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        ConciliacaoBancariaBatchRequest batch =
                context.getRequest(ConciliacaoBancariaBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {

            result.addError(
                    ConciliacaoBancariaValidationCode.CONCILIACAO_BANCARIA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma conciliação para a conta %s.",
                            request.getNumeroContaBancaria()));
        }
    }

    /**
     * Verifica se duas conciliações possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            ConciliacaoBancariaRequest primeira,
            ConciliacaoBancariaRequest segunda) {

        return Objects.equals(primeira.getNumeroContaBancaria(),
                segunda.getNumeroContaBancaria())
               && Objects.equals(primeira.getNumeroAgenciaContaBancaria(),
                segunda.getNumeroAgenciaContaBancaria())
               && Objects.equals(primeira.getCodigoBancoContaBancaria(),
                segunda.getCodigoBancoContaBancaria())
               && Objects.equals(primeira.getNumero(),
                segunda.getNumero())
               && Objects.equals(primeira.getTipoContaBancaria(),
                segunda.getTipoContaBancaria())
               && Objects.equals(primeira.getCnpjGerenciaContaBancaria(),
                segunda.getCnpjGerenciaContaBancaria());
    }
}