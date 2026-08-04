package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancariaRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return String.format(
                "Conta %s",
                numeroContaBancaria
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

    @NotBlank(message = "O código do banco é obrigatório.")
    @Size(
            min = 3,
            max = 3,
            message = "O código do banco deve conter 3 dígitos.")
    @Pattern(
            regexp = "\\d+",
            message = "O código do banco deve conter apenas números.")
    private String codigoBancoContaBancaria;

    @NotBlank(message = "O número da agência é obrigatório.")
    @Size(
            min = 1,
            max = 6,
            message = "O número da agência deve conter entre 1 e 6 caracteres.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "O número da agência deve conter apenas letras maiúsculas e números.")
    private String numeroAgenciaContaBancaria;

    @NotBlank(message = "A descrição da conta bancária é obrigatória.")
    @Size(
            min = 10,
            max = 100,
            message = "A descrição da conta bancária deve conter entre 10 e 100 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A descrição da conta bancária não pode conter apenas espaços.")
    private String descricaoContaBancaria;

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
            message = "O CNPJ da gerência da conta bancária deve conter 14 caracteres.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "O CNPJ da gerência da conta bancária deve conter apenas letras maiúsculas e números.")
    private String cnpjGerenciaContaBancaria;
}