package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoBancariaRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return String.format(
                "Conta %s - Conciliação %s",
                numeroContaBancaria,
                numeroConciliacao
        );
    }

    @NotBlank(message = "O número da conta bancária é obrigatório.")
    @Size(
            min = 1,
            max = 13,
            message = "O número da conta bancária deve conter entre 1 e 13 caracteres.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "O número da conta bancária deve conter apenas letras maiúsculas e números.")
    private String numeroContaBancaria;

    @NotBlank(message = "O número da agência é obrigatório.")
    @Size(
            min = 1,
            max = 6,
            message = "O número da agência deve conter entre 1 e 6 caracteres.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "O número da agência deve conter apenas letras maiúsculas e números.")
    private String numeroAgenciaContaBancaria;

    @NotBlank(message = "O código do banco é obrigatório.")
    @Size(
            min = 3,
            max = 3,
            message = "O código do banco deve conter 3 dígitos.")
    @Pattern(
            regexp = "\\d+",
            message = "O código do banco deve conter apenas números.")
    private String codigoBancoContaBancaria;

    @NotBlank(message = "O número é obrigatório.")
    @Size(
            min = 8,
            max = 8,
            message = "O número deve conter 8 dígitos.")
    @Pattern(
            regexp = "\\d+",
            message = "O número deve conter apenas números.")
    private String numero;

    @NotBlank(message = "O tipo da conta bancária é obrigatório.")
    @Size(
            min = 1,
            max = 1,
            message = "O tipo da conta bancária deve conter 1 dígito.")
    @Pattern(
            regexp = "\\d",
            message = "O tipo da conta bancária deve conter apenas números.")
    private String tipoContaBancaria;

    @NotBlank(message = "O CNPJ da gerência da conta bancária é obrigatório.")
    @Size(
            min = 14,
            max = 14,
            message = "O CNPJ da gerência deve conter 14 caracteres.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "O CNPJ da gerência deve conter apenas letras maiúsculas e números.")
    private String cnpjGerenciaContaBancaria;

    @NotBlank(message = "O número da conciliação é obrigatório.")
    private String numeroConciliacao;

    @NotBlank(message = "O tipo da conciliação é obrigatório.")
    @Size(
            min = 1,
            max = 1,
            message = "O tipo da conciliação deve conter 1 dígito.")
    @Pattern(
            regexp = "\\d",
            message = "O tipo da conciliação deve conter apenas números.")
    private String tipoConciliacao;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(
            min = 10,
            max = 150,
            message = "A descrição deve conter entre 10 e 150 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A descrição não pode conter apenas espaços.")
    private String descricao;

    @NotNull(message = "A data é obrigatória.")
    private LocalDate data;

    @NotBlank(message = "O número do cheque é obrigatório.")
    @Size(
            min = 6,
            max = 6,
            message = "O número do cheque deve conter 6 dígitos.")
    @Pattern(
            regexp = "\\d+",
            message = "O número do cheque deve conter apenas números.")
    private String numeroCheque;

    @NotBlank(message = "O número do documento de débito é obrigatório.")
    @Size(
            min = 11,
            max = 11,
            message = "O número do documento de débito deve conter 11 caracteres.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "O número do documento de débito deve conter apenas letras maiúsculas e números.")
    private String numeroDocumentoDebito;

    @NotNull(message = "O valor da conciliação é obrigatório.")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "O valor da conciliação deve ser maior ou igual a zero.")
    private BigDecimal valorConciliacao;
}