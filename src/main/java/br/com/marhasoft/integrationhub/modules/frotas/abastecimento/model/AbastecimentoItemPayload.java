package br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
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
public class AbastecimentoItemPayload {

    private String tipoCombustivel;

    private String tipoCategoriaFrota;

    private String codigo;

    private int quantidade;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}