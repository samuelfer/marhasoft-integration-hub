package br.com.marhasoft.integrationhub.modules.frotas.proprietario.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProprietarioItemRequest {

    private String cpfCnpj;

    private String nome;

    /**
     * Operação da integração.
     */
    private IntegrationAction action;
}
