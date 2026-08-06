package br.com.marhasoft.integrationhub.core.sagres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagresValidacaoDetalhes {

    private SagresValidacaoSumario sumario;

    private List<SagresValidacaoErroEntidade> errosPorEntidade;
}