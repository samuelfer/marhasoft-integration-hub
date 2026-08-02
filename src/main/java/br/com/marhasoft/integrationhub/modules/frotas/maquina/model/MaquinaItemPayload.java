package br.com.marhasoft.integrationhub.modules.frotas.maquina.model;

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
public class MaquinaItemPayload {

    private String codigo;

    /**
     * Ano de fabricação.
     */
    private String anoFabricacao;

    /**
     * CPF ou CNPJ do proprietário.
     */
    private String cpfCnpjProprietario;

    /**
     * CPF ou CNPJ do locador.
     */
    private String cpfCnpjLocador;

    /**
     * Descrição da máquina.
     */
    private String descricao;

    /**
     * Tipo da frota.
     */
    private String tipoFrota;

    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}