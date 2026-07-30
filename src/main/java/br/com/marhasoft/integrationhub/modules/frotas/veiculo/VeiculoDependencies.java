package br.com.marhasoft.integrationhub.modules.frotas.veiculo;

import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClientResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoDependencies {

    private PessoaClientResponse proprietario;

    private PessoaClientResponse locador;

}
