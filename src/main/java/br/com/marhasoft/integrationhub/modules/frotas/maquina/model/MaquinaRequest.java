package br.com.marhasoft.integrationhub.modules.frotas.maquina.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaquinaRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Máquina " + codigo;
    }

    /**
     * Código da máquina.
     */
    @NotBlank(message = "O código é obrigatório.")
    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "O código deve conter exatamente 7 caracteres alfanuméricos em maiúsculo.")
    private String codigo;

    /**
     * Ano de fabricação.
     */
    @NotBlank(message = "O ano de fabricação é obrigatório.")
    @Pattern(
            regexp = "^\\d{4}$",
            message = "O ano de fabricação deve conter exatamente 4 dígitos.")
    private String anoFabricacao;

    /**
     * CPF ou CNPJ do proprietário.
     */
    @NotBlank(message = "O CPF/CNPJ do proprietário é obrigatório.")
    @Pattern(
            regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF/CNPJ do proprietário deve conter 11 ou 14 dígitos.")
    private String cpfCnpjProprietario;

    /**
     * CPF ou CNPJ do locador.
     */
    @Pattern(
            regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF/CNPJ do locador deve conter 11 ou 14 dígitos.")
    private String cpfCnpjLocador;

    /**
     * Descrição da máquina.
     */
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(
            min = 10,
            max = 150,
            message = "A descrição deve possuir entre 10 e 150 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A descrição deve conter ao menos um caractere diferente de espaço.")
    private String descricao;

    /**
     * Tipo da frota.
     */
    @NotBlank(message = "O tipo da frota é obrigatório.")
    @Pattern(
            regexp = "^[1-4]$",
            message = "O tipo da frota deve ser um dos valores: 1 (Próprio), 2 (Locado), 3 (Prestação de Serviços) ou 4 (Cedido).")
    private String tipoFrota;

}