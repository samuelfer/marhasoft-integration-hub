package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizacaoOrcamentariaRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return String.format(
                "UG %s - Ação %s - Programa %s",
                codigoUnidadeGestora,
                codigoAcao,
                codigoPrograma
        );
    }

    @NotBlank(message = "O código da unidade gestora é obrigatório.")
    @Size(min = 6, max = 6,
            message = "O código da unidade gestora deve conter 6 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da unidade gestora deve conter apenas números.")
    private String codigoUnidadeGestora;

    @NotBlank(message = "O código da unidade orçamentária é obrigatório.")
    @Size(min = 5, max = 5,
            message = "O código da unidade orçamentária deve conter 5 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da unidade orçamentária deve conter apenas números.")
    private String codigoUnidadeOrcamentaria;

    @NotBlank(message = "O código da função é obrigatório.")
    @Size(min = 2, max = 2,
            message = "O código da função deve conter 2 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da função deve conter apenas números.")
    private String codigoFuncao;

    @NotBlank(message = "O código da subfunção é obrigatório.")
    @Size(min = 3, max = 3,
            message = "O código da subfunção deve conter 3 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da subfunção deve conter apenas números.")
    private String codigoSubfuncao;

    @NotBlank(message = "O código do programa é obrigatório.")
    @Size(min = 4, max = 4,
            message = "O código do programa deve conter 4 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código do programa deve conter apenas números.")
    private String codigoPrograma;

    @NotBlank(message = "O código da ação é obrigatório.")
    @Size(min = 4, max = 4,
            message = "O código da ação deve conter 4 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da ação deve conter apenas números.")
    private String codigoAcao;

    @NotBlank(message = "O código da categoria econômica é obrigatório.")
    @Size(min = 1, max = 1,
            message = "O código da categoria econômica deve conter 1 dígito.")
    @Pattern(regexp = "\\d+",
            message = "O código da categoria econômica deve conter apenas números.")
    private String codigoCategoriaEconomica;

    @NotBlank(message = "O código da natureza da despesa é obrigatório.")
    @Size(min = 1, max = 1,
            message = "O código da natureza da despesa deve conter 1 dígito.")
    @Pattern(regexp = "\\d+",
            message = "O código da natureza da despesa deve conter apenas números.")
    private String codigoNaturezaDespesa;

    @NotBlank(message = "O código da modalidade da despesa é obrigatório.")
    @Size(min = 2, max = 2,
            message = "O código da modalidade da despesa deve conter 2 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da modalidade da despesa deve conter apenas números.")
    private String codigoModalidadeDespesa;

    @NotBlank(message = "O código do elemento da despesa é obrigatório.")
    @Size(min = 2, max = 2,
            message = "O código do elemento da despesa deve conter 2 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código do elemento da despesa deve conter apenas números.")
    private String codigoElementoDespesa;

    @NotBlank(message = "O código da fonte de recurso é obrigatório.")
    @Size(min = 3, max = 3,
            message = "O código da fonte de recurso deve conter 3 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da fonte de recurso deve conter apenas números.")
    private String codigoFonteRecurso;

    @NotBlank(message = "O exercício da fonte de recurso é obrigatório.")
    @Pattern(
            regexp = "ATUAL|ANTERIOR",
            message = "O exercício da fonte de recurso deve ser ATUAL ou ANTERIOR.")
    private String exercicioFonteRecurso;

    @NotBlank(message = "O número do decreto/ofício é obrigatório.")
    @Size(min = 9, max = 9,
            message = "O número do decreto/ofício deve conter 9 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O número do decreto/ofício deve conter apenas números.")
    private String numeroDecretoOficio;

    @NotBlank(message = "O tipo do decreto/ofício é obrigatório.")
    @Pattern(
            regexp = "DECRETO|OFICIO",
            message = "O tipo do decreto/ofício deve ser DECRETO ou OFICIO.")
    private String tipoDecretoOficio;

    @NotBlank(message = "O tipo da alteração é obrigatório.")
    @Size(min = 1, max = 1,
            message = "O tipo da alteração deve conter 1 dígito.")
    @Pattern(regexp = "\\d+",
            message = "O tipo da alteração deve conter apenas números.")
    private String tipoAlteracao;

    @NotNull(message = "O valor da atualização é obrigatório.")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "O valor da atualização deve ser maior que zero.")
    private BigDecimal valorAtualizacao;
}