package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model;

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
public class ReceitaOrcamentariaRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Receita " + numeroReceita;
    }

    @NotBlank(message = "O número da receita é obrigatório.")
    @Size(min = 7, max = 7,
            message = "O número da receita deve conter 8 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O número da receita deve conter apenas números.")
    private String numeroReceita;

    @NotBlank(message = "O código da receita orçamentária é obrigatório.")
    @Size(min = 8, max = 8,
            message = "O código da receita orçamentária deve conter 8 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código da receita orçamentária deve conter apenas números.")
    private String codigoReceitaOrcamentaria;

    @NotBlank(message = "O tipo de lançamento da receita é obrigatório.")
    @Size(min = 1, max = 1,
            message = "O tipo de lançamento da receita deve conter 1 dígito.")
    @Pattern(regexp = "\\d",
            message = "O tipo de lançamento da receita deve conter apenas números.")
    private String tipoLancamentoReceita;

    @NotBlank(message = "O tipo da receita lançada é obrigatório.")
    @Size(min = 1, max = 1,
            message = "O tipo da receita lançada deve conter 1 dígito.")
    @Pattern(regexp = "\\d",
            message = "O tipo da receita lançada deve conter apenas números.")
    private String tipoReceitaLancada;

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

    @NotBlank(message = "O código CO é obrigatório.")
    @Size(min = 4, max = 4,
            message = "O código CO deve conter 4 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código CO deve conter apenas números.")
    private String codigoCO;

    @NotNull(message = "O valor da receita orçamentária é obrigatório.")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "O valor da receita orçamentária deve ser maior ou igual a zero.")
    private BigDecimal valorReceitaOrcamentaria;

    @NotBlank(message = "O código do banco é obrigatório.")
    @Size(min = 3, max = 3,
            message = "O código do banco deve conter 3 dígitos.")
    @Pattern(regexp = "\\d+",
            message = "O código do banco deve conter apenas números.")
    private String codigoBancoContaBancaria;

    @NotBlank(message = "O número da conta bancária é obrigatório.")
    @Size(min = 1, max = 13,
            message = "O número da conta bancária deve conter entre 1 e 13 caracteres.")
    @Pattern(regexp = "^[A-Z0-9]+$",
            message = "O número da conta bancária deve conter apenas letras maiúsculas e números.")
    private String numeroContaBancaria;

    @NotBlank(message = "O número da agência é obrigatório.")
    @Size(min = 1, max = 6,
            message = "O número da agência deve conter entre 1 e 6 caracteres.")
    @Pattern(regexp = "^[A-Z0-9]+$",
            message = "O número da agência deve conter apenas letras maiúsculas e números.")
    private String numeroAgenciaContaBancaria;

    @NotBlank(message = "O tipo da conta bancária é obrigatório.")
    @Size(min = 1, max = 1,
            message = "O tipo da conta bancária deve conter 1 dígito.")
    @Pattern(regexp = "\\d",
            message = "O tipo da conta bancária deve conter apenas números.")
    private String tipoContaBancaria;

    @NotBlank(message = "O CNPJ da gerência da conta bancária é obrigatório.")
    @Size(min = 14, max = 14,
            message = "O CNPJ da gerência da conta bancária deve conter 14 caracteres.")
    @Pattern(regexp = "^[A-Z0-9]+$",
            message = "O CNPJ da gerência da conta bancária deve conter apenas letras maiúsculas e números.")
    private String cnpjGerenciaContaBancaria;
}