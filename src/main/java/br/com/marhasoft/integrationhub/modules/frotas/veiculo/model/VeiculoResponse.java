package br.com.marhasoft.integrationhub.modules.frotas.veiculo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoResponse {

    /**
     * Indica se a integração foi realizada com sucesso.
     */
    private boolean sucesso;

    /**
     * Mensagem retornada pela integração.
     */
    private String mensagem;

    /**
     * Identificador da integração, quando existir.
     */
    private String protocolo;

}
