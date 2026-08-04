package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaBancariaItemPayload {

    private String numeroContaBancaria;

    private String codigoBancoContaBancaria;

    private String numeroAgenciaContaBancaria;

    private String descricaoContaBancaria;

    private String tipoContaBancaria;

    private String cnpjGerenciaContaBancaria;

    /**
     * Operação da integração.
     */
    private IntegrationAction action;

}