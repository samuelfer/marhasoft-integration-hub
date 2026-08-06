package br.com.marhasoft.integrationhub.core.sagres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagresValidacaoDetalhesResponse {

    private SagresValidacaoSumarioResponse sumario;

    private List<SagresValidacaoErroEntidadeResponse> errosPorEntidade;
}