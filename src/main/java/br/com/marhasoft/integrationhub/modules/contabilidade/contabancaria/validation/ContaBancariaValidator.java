package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ContaBancariaValidator {

    public ValidationResult validate(
            ContaBancariaRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        // TODO Validar se o tipo da conta bancária é válido.
        // Regra: CONTA_BANCARIA_TIPO_VALIDO

        // TODO Validar se o código do banco é válido conforme
        // a tabela FEBRABAN.
        // Regra: CONTA_BANCARIA_CODIGO_BANCO_VALIDO

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            ContaBancariaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        ContaBancariaBatchRequest batch =
                context.getRequest(ContaBancariaBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {

            result.addError(
                    ContaBancariaValidationCode.CONTA_BANCARIA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma conta bancária cadastrada com o número %s.",
                            request.getNumeroContaBancaria()));
        }
    }

    /**
     * Verifica se duas contas bancárias possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            ContaBancariaRequest primeira,
            ContaBancariaRequest segunda) {

        return Objects.equals(
                primeira.getNumeroContaBancaria(),
                segunda.getNumeroContaBancaria())
               && Objects.equals(
                primeira.getCodigoBancoContaBancaria(),
                segunda.getCodigoBancoContaBancaria())
               && Objects.equals(
                primeira.getNumeroAgenciaContaBancaria(),
                segunda.getNumeroAgenciaContaBancaria())
               && Objects.equals(
                primeira.getTipoContaBancaria(),
                segunda.getTipoContaBancaria())
               && Objects.equals(
                primeira.getCnpjGerenciaContaBancaria(),
                segunda.getCnpjGerenciaContaBancaria());
    }
}