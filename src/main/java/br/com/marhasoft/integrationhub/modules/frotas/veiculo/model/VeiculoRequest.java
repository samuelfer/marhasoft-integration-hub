package br.com.marhasoft.integrationhub.modules.frotas.veiculo.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Veículo de placa " + placa;
    }

    @NotBlank(message = "A placa é obrigatória.")
    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "A placa deve conter exatamente 7 caracteres alfanuméricos em maiúsculo.")
    private String placa;

    @NotBlank(message = "O ano do modelo é obrigatório.")
    @Pattern(
            regexp = "^\\d{4}$",
            message = "O ano do modelo deve conter exatamente 4 dígitos.")
    private String anoModelo;

    @NotBlank(message = "O RENAVAM é obrigatório.")
    @Pattern(
            regexp = "^\\d{11}$",
            message = "O RENAVAM deve conter exatamente 11 dígitos.")
    private String numeroRenavan;

    /**
     * Código do modelo conforme tabela do TCE.
     */
    @NotBlank(message = "O modelo é obrigatório.")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "O código do modelo deve conter exatamente 6 dígitos.")
    private String numeroModelo;

    /**
     * Tipo da frota conforme tabela do TCE.
     */
    @NotBlank(message = "O tipo da frota é obrigatório.")
    @Pattern(
            regexp = "^[1-4]$",
            message = "O tipo da frota deve ser um dos valores: 1, 2, 3 ou 4.")
    private String tipoFrota;

    @NotBlank(message = "O proprietário é obrigatório.")
    @Pattern(
            regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF/CNPJ do proprietário deve conter 11 ou 14 dígitos.")
    private String cpfCnpjProprietario;

    @Pattern(
            regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF/CNPJ do locador deve conter 11 ou 14 dígitos.")
    private String cpfCnpjLocador;
}