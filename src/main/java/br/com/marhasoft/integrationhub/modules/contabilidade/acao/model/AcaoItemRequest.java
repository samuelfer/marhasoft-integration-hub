package br.com.marhasoft.integrationhub.modules.contabilidade.acao.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcaoItemRequest {

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
