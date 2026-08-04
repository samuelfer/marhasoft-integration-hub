package br.com.marhasoft.integrationhub.modules.contabilidade.acao.model;

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
public class AcaoItemPayload {

    private String codigoUnidadeGestora;

    private String codigoAcao;

    private String descricaoAcao;

    private String tipoAcao;

    private String descricaoMeta;

    private String unidadeMedida;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}