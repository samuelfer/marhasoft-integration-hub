package br.com.marhasoft.integrationhub.modules.frotas.locador.model;

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
public class LocadorRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Locador CPF/CNPJ " + cpfCnpj;
    }

    @NotBlank(message = "O CPF/CNPJ é obrigatório.")
    @Pattern(
            regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF/CNPJ do locador deve conter 11 ou 14 dígitos.")
    private String cpfCnpj;

    @NotBlank(message = "O nome do locador é obrigatório.")
    @Size(min = 10, max = 150, message = "O nome do locador deve possuir entre 10 e 150 caracteres.")
    @Pattern(regexp = ".*\\S.*", message = "O nome do locador deve conter ao menos um caractere diferente de espaço.")
    private String nome;

}