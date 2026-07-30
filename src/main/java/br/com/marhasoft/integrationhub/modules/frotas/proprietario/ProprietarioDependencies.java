package br.com.marhasoft.integrationhub.modules.frotas.proprietario;

import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClientResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProprietarioDependencies {

    private PessoaClientResponse proprietario;

    private PessoaClientResponse locador;

}
