package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoBancariaItemRequest {

    private String numeroContaBancaria;
    private String numeroAgenciaContaBancaria;
    private String codigoBancoContaBancaria;
    private String numero;
    private String tipoContaBancaria;
    private String cnpjGerenciaContaBancaria;
    private String numeroConciliacao;
    private String tipoConciliacao;
    private String descricao;
    private LocalDate data;
    private String numeroCheque;
    private String numeroDocumentoDebito;
    private BigDecimal valorConciliacao;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;
}
