package br.com.marhasoft.integrationhub.core.tce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TceValidacaoDetalhes {

    private TceValidacaoSumario sumario;

    private List<TceValidacaoErroEntidade> errosPorEntidade;
}