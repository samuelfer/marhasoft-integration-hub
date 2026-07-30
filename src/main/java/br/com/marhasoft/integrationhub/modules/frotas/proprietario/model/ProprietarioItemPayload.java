package br.com.marhasoft.integrationhub.modules.frotas.proprietario.model;

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
public class ProprietarioItemPayload {

    private String cpfCnpj;

    private String nome;

    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}