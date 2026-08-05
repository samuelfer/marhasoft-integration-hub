package br.com.marhasoft.integrationhub.core.tce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TceErrorResponse {

    /**
     * Mensagem resumida.
     */
    private String mensagem;

    /**
     * Descrição detalhada do erro.
     */
    private String descricao;

}