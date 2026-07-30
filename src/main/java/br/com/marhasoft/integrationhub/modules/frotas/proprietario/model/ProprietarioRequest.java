package br.com.marhasoft.integrationhub.modules.frotas.proprietario.model;

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
public class ProprietarioRequest implements Identificavel {

    @Override
    public String getIdentificador() {
        return "Proprietário CPF/CNPJ " + cpfCnpj;
    }

    @NotBlank(message = "O CPF/CNPJ é obrigatório.")
    @Pattern(
            regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF/CNPJ do proprietário deve conter 11 ou 14 dígitos.")
    private String cpfCnpj;

    @NotBlank(message = "O nome do proprietário é obrigatório.")
    @Size(min = 10, max = 150, message = "O nome do proprietário deve possuir entre 10 e 150 caracteres.")
    @Pattern(regexp = ".*\\S.*", message = "O nome do proprietário deve conter ao menos um caractere diferente de espaço.")
    private String nome;

}