package br.com.marhasoft.integrationhub.modules.frotas.dependency;

import br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client.response.PessoaResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoDependencies {

    private PessoaResponse proprietario;

    private PessoaResponse locador;

}
