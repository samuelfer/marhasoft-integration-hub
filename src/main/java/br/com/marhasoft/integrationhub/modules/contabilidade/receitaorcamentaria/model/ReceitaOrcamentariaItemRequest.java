package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaOrcamentariaItemRequest {

    private String numeroReceita;
    private String codigoReceitaOrcamentaria;
    private String tipoLancamentoReceita;
    private String tipoReceitaLancada;
    private String codigoFonteRecurso;
    private String exercicioFonteRecurso;
    private String codigoCO;
    private BigDecimal valorReceitaOrcamentaria;
    private String codigoBancoContaBancaria;
    private String numeroContaBancaria;
    private String numeroAgenciaContaBancaria;
    private String tipoContaBancaria;
    private String cnpjGerenciaContaBancaria;

    /**
     * Operação da integração.
     */
    private IntegrationAction action;
}