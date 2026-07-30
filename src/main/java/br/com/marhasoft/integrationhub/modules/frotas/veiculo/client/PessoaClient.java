package br.com.marhasoft.integrationhub.modules.frotas.veiculo.client;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PessoaClient {

    public Optional<PessoaClientResponse> buscar(String cpfCnpj) {

        // TODO Implementar consulta real

        return Optional.of(
                PessoaClientResponse.builder()
                        .id(1L)
                        .cpfCnpj(cpfCnpj)
                        .nome("Pessoa de Teste")
                        .cadastrada(true)
                        .build()
        );
    }
}