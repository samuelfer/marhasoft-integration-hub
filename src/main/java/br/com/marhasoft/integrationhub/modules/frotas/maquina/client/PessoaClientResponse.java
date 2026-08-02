package br.com.marhasoft.integrationhub.modules.frotas.maquina.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaClientResponse {

    /**
     * Identificador da pessoa no sistema do TCE ou sistema intermediário.
     */
    private Long id;

    /**
     * CPF ou CNPJ da pessoa.
     */
    private String cpfCnpj;

    /**
     * Nome ou razão social.
     */
    private String nome;

    /**
     * Indica se a pessoa está cadastrada.
     */
    private boolean cadastrada;

}