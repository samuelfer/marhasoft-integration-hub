package br.com.marhasoft.integrationhub.modules.frotas.proprietario.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProprietarioResponse {

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
