package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReceitaOrcamentariaValidator {

    public ValidationResult validate(
            ReceitaOrcamentariaRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        // TODO Validar se o tipo de lançamento é válido.
        // Regra: RECEITA_ORCAMENTARIA_TIPO_LANCAMENTO_VALIDO

        // TODO Validar se o tipo de receita lançada é válido.
        // Regra: RECEITA_ORCAMENTARIA_TIPO_VALIDO

        // TODO Validar se o código da receita orçamentária
        // existe na tabela da STN (MSC).
        // Regra: RECEITA_ORCAMENTARIA_CODIGO_RECEITA_ORCAMENTARIA_VALIDO

        // TODO Validar se a fonte de recurso existe
        // na tabela da STN (MSC).
        // Regra: RECEITA_ORCAMENTARIA_CODIGO_FONTE_RECURSO_VALIDO

        // TODO Validar se o código CO existe
        // na tabela da STN (MSC).
        // Regra: RECEITA_ORCAMENTARIA_CODIGO_CO_VALIDO

        // TODO Validar se a conta bancária está cadastrada
        // para a Unidade Gestora no exercício.
        // Regra: RECEITA_ORCAMENTARIA_CONTA_BANCARIA_NAO_CADASTRADA

        // TODO Validar que o exercício da fonte de recurso
        // seja sempre ATUAL.
        // Regra: RECEITA_ORCAMENTARIA_EXERCICIO_FONTE_RECURSO_VALIDO

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            ReceitaOrcamentariaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        ReceitaOrcamentariaBatchRequest batch =
                context.getRequest(ReceitaOrcamentariaBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {

            result.addError(
                    ReceitaOrcamentariaValidationCode.RECEITA_ORCAMENTARIA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma receita orçamentária com o número %s.",
                            request.getNumeroReceita()));
        }
    }

    /**
     * Verifica se duas receitas possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            ReceitaOrcamentariaRequest primeira,
            ReceitaOrcamentariaRequest segunda) {

        return Objects.equals(primeira.getNumeroReceita(), segunda.getNumeroReceita())
               && Objects.equals(primeira.getCodigoReceitaOrcamentaria(), segunda.getCodigoReceitaOrcamentaria())
               && Objects.equals(primeira.getTipoLancamentoReceita(), segunda.getTipoLancamentoReceita())
               && Objects.equals(primeira.getTipoReceitaLancada(), segunda.getTipoReceitaLancada())
               && Objects.equals(primeira.getCodigoFonteRecurso(), segunda.getCodigoFonteRecurso())
               && Objects.equals(primeira.getExercicioFonteRecurso(), segunda.getExercicioFonteRecurso())
               && Objects.equals(primeira.getCodigoCO(), segunda.getCodigoCO())
               && Objects.equals(primeira.getCodigoBancoContaBancaria(), segunda.getCodigoBancoContaBancaria())
               && Objects.equals(primeira.getNumeroContaBancaria(), segunda.getNumeroContaBancaria())
               && Objects.equals(primeira.getNumeroAgenciaContaBancaria(), segunda.getNumeroAgenciaContaBancaria())
               && Objects.equals(primeira.getTipoContaBancaria(), segunda.getTipoContaBancaria())
               && Objects.equals(primeira.getCnpjGerenciaContaBancaria(), segunda.getCnpjGerenciaContaBancaria());
    }
}