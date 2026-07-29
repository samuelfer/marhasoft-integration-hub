package br.com.marhasoft.integrationhub.modules.frotas.domain.model;

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
public class VeiculoItemPayload {

    private String placa;

    private String anoModelo;

    private String numeroRenavan;

    private String cpfCnpjProprietario;

    private String cpfCnpjLocador;

    /**
     * Código do modelo do veículo conforme tabela do TCE.
     */
    private String numeroModelo;

    /**
     * Tipo da frota conforme tabela do TCE.
     */
    private String tipoFrota;

    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}