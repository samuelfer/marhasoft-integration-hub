package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoFrotaItemPayload {

    private LocalDate dataSituacao;

    private String tipoSituacao;

    private String tipoCategoriaFrota;

    private String codigo;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}