package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model;

import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Abastecimento " + codigo;
    }

    /**
     * Tipo de combustível.
     */
    @NotNull(message = "O tipo de combustível é obrigatório.")
    private String tipoCombustivel;

    /**
     * Tipo da categoria da frota.
     */
    @NotBlank(message = "O tipo da categoria da frota é obrigatório.")
    @Pattern(
            regexp = "^\\d$",
            message = "O tipo da categoria da frota deve conter exatamente 1 dígito.")
    private String tipoCategoriaFrota;

    /**
     * Código da situação da frota.
     */
    @NotBlank(message = "O código é obrigatório.")
    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "O código deve conter exatamente 7 caracteres alfanuméricos em maiúsculo.")
    private String codigo;

    private int quantidade;

}