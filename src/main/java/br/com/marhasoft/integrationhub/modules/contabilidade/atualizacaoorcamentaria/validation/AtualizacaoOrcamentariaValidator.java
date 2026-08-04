package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.validation;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.validation.ValidationResult;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AtualizacaoOrcamentariaValidator {

    public ValidationResult validate(
            AtualizacaoOrcamentariaRequest request,
            IntegrationContext<?, ?> context) {

        ValidationResult result = ValidationResult.valid();

        validarDuplicidade(request, context, result);

        // TODO Validar se a unidade gestora pertence ao mesmo município.
        // Regra: ATUALIZACAO_ORCAMENTARIA_UNIDADE_GESTORA_MUNICIPIO

        // TODO Validar se apenas Prefeituras e Consórcios podem enviar
        // atualizações orçamentárias.
        // Regra: ATUALIZACAO_ORCAMENTARIA_RESPONSABILIDADE_PROTOCOLO

        // TODO Validar se a unidade orçamentária está cadastrada.
        // Regra: ATUALIZACAO_ORCAMENTARIA_UNIDADE_ORCAMENTARIA_CADASTRADA

        // TODO Validar se o programa está cadastrado.
        // Regra: ATUALIZACAO_ORCAMENTARIA_PROGRAMA_CADASTRADO

        // TODO Validar se a ação está cadastrada.
        // Regra: ATUALIZACAO_ORCAMENTARIA_ACAO_CADASTRADA

        // TODO Validar Função STN.
        // Regra: ATUALIZACAO_ORCAMENTARIA_FUNCAO_STN

        // TODO Validar Subfunção STN.
        // Regra: ATUALIZACAO_ORCAMENTARIA_SUBFUNCAO_STN

        // TODO Validar Categoria Econômica STN.
        // Regra: ATUALIZACAO_ORCAMENTARIA_CATEGORIA_ECONOMICA_STN

        // TODO Validar Natureza da Despesa STN.
        // Regra: ATUALIZACAO_ORCAMENTARIA_NATUREZA_DESPESA_STN

        // TODO Validar Elemento da Despesa STN.
        // Regra: ATUALIZACAO_ORCAMENTARIA_ELEMENTO_DESPESA_STN

        // TODO Validar Fonte de Recurso STN.
        // Regra: ATUALIZACAO_ORCAMENTARIA_FONTE_RECURSO_STN

        // TODO Implementar as demais regras de negócio descritas
        // na documentação do TCE.

        return result;
    }

    /**
     * Não permite registros duplicados pela chave de unicidade.
     */
    private void validarDuplicidade(
            AtualizacaoOrcamentariaRequest request,
            IntegrationContext<?, ?> context,
            ValidationResult result) {

        AtualizacaoOrcamentariaBatchRequest batch =
                context.getRequest(AtualizacaoOrcamentariaBatchRequest.class);

        long quantidade = batch.getElementos()
                .stream()
                .filter(item -> mesmaChaveUnicidade(item, request))
                .count();

        if (quantidade > 1) {
            result.addError(
                    AtualizacaoOrcamentariaValidationCode.ATUALIZACAO_ORCAMENTARIA_DUPLICIDADE_NAO_PERMITIDA,
                    String.format(
                            "Já existe uma atualização orçamentária para a ação %s da unidade gestora %s.",
                            request.getCodigoAcao(),
                            request.getCodigoUnidadeGestora()));
        }
    }

    /**
     * Verifica se duas atualizações possuem a mesma chave de unicidade.
     */
    private boolean mesmaChaveUnicidade(
            AtualizacaoOrcamentariaRequest primeira,
            AtualizacaoOrcamentariaRequest segunda) {

        return Objects.equals(primeira.getCodigoUnidadeGestora(), segunda.getCodigoUnidadeGestora())
               && Objects.equals(primeira.getCodigoUnidadeOrcamentaria(), segunda.getCodigoUnidadeOrcamentaria())
               && Objects.equals(primeira.getCodigoFuncao(), segunda.getCodigoFuncao())
               && Objects.equals(primeira.getCodigoSubfuncao(), segunda.getCodigoSubfuncao())
               && Objects.equals(primeira.getCodigoPrograma(), segunda.getCodigoPrograma())
               && Objects.equals(primeira.getCodigoAcao(), segunda.getCodigoAcao())
               && Objects.equals(primeira.getCodigoCategoriaEconomica(), segunda.getCodigoCategoriaEconomica())
               && Objects.equals(primeira.getCodigoNaturezaDespesa(), segunda.getCodigoNaturezaDespesa())
               && Objects.equals(primeira.getCodigoModalidadeDespesa(), segunda.getCodigoModalidadeDespesa())
               && Objects.equals(primeira.getCodigoElementoDespesa(), segunda.getCodigoElementoDespesa())
               && Objects.equals(primeira.getCodigoFonteRecurso(), segunda.getCodigoFonteRecurso())
               && Objects.equals(primeira.getExercicioFonteRecurso(), segunda.getExercicioFonteRecurso())
               && Objects.equals(primeira.getNumeroDecretoOficio(), segunda.getNumeroDecretoOficio())
               && Objects.equals(primeira.getTipoDecretoOficio(), segunda.getTipoDecretoOficio())
               && Objects.equals(primeira.getTipoAlteracao(), segunda.getTipoAlteracao());
    }
}