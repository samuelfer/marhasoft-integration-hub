package br.com.marhasoft.integrationhub.modules.frotas.locador;

import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClientResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocadorDependencies {

    private PessoaClientResponse locador;

}
