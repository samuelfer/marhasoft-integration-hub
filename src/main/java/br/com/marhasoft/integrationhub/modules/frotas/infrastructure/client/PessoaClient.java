package br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client;

import br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client.response.PessoaResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PessoaClient {

    public Optional<PessoaResponse> buscar(String cpfCnpj) {

        // TODO Implementar consulta real

        return Optional.of(
                PessoaResponse.builder()
                        .id(1L)
                        .cpfCnpj(cpfCnpj)
                        .nome("Pessoa de Teste")
                        .cadastrada(true)
                        .build()
        );
    }
}