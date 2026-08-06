package br.com.marhasoft.integrationhub.core.sagres.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagresErrorResponse {

    /**
     * Mensagem resumida.
     */
    private String mensagem;

    /**
     * Descrição detalhada do erro.
     */
    private String descricao;

}