package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoFrotaItemRequest {

    private LocalDate dataSituacao;

    private String tipoSituacao;

    private String tipoCategoriaFrota;

    private String codigo;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;
}
