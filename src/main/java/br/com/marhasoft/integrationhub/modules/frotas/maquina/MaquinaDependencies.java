package br.com.marhasoft.integrationhub.modules.frotas.maquina;

import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClientResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaquinaDependencies {

    private PessoaClientResponse proprietario;

    private PessoaClientResponse locador;

}
