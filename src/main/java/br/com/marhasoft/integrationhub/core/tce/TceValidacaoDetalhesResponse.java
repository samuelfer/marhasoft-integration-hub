package br.com.marhasoft.integrationhub.core.tce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TceValidacaoDetalhesResponse {

    private TceValidacaoSumarioResponse sumario;

    private List<TceValidacaoErroEntidadeResponse> errosPorEntidade;
}