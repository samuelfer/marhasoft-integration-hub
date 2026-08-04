package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizacaoOrcamentariaItemRequest {

    private String codigoUnidadeGestora;
    private String codigoUnidadeOrcamentaria;
    private String codigoFuncao;
    private String codigoSubfuncao;
    private String codigoPrograma;
    private String codigoAcao;
    private String codigoCategoriaEconomica;
    private String codigoNaturezaDespesa;
    private String codigoModalidadeDespesa;
    private String codigoElementoDespesa;
    private String codigoFonteRecurso;
    private String exercicioFonteRecurso;
    private String numeroDecretoOficio;
    private String tipoDecretoOficio;
    private String tipoAlteracao;
    private BigDecimal valorAtualizacao;
    /**
     * Operação da integração.
     */
    private IntegrationAction action;
}
