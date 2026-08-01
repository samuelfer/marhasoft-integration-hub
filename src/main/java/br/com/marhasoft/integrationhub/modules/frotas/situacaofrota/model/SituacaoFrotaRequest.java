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
public class SituacaoFrotaRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Situação frota " + codigo;
    }

    /**
     * Data da situação da frota.
     */
    @NotNull(message = "A data da situação é obrigatória.")
    private LocalDate dataSituacao;

    /**
     * Tipo da situação.
     */
    @NotBlank(message = "O tipo da situação é obrigatório.")
    @Pattern(
            regexp = "^\\d$",
            message = "O tipo da situação deve conter exatamente 1 dígito.")
    private String tipoSituacao;

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

}