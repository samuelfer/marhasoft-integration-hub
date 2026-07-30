package br.com.marhasoft.integrationhub.modules.frotas.veiculo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequest {

    private String placa;

    private String anoModelo;

    private String numeroRenavan;

    /**
     * Código do modelo conforme tabela do TCE.
     */
    private String numeroModelo;

    /**
     * Tipo da frota conforme tabela do TCE.
     */
    private String tipoFrota;

    private String cpfCnpjProprietario;

    private String cpfCnpjLocador;

}
