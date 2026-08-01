package br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoItemRequest {

    private String tipoCombustivel;

    private String tipoCategoriaFrota;

    private String codigo;

    private int quantidade;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;
}
