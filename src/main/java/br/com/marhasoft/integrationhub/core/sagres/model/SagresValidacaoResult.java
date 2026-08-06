package br.com.marhasoft.integrationhub.core.sagres.model;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagresValidacaoResult {

    private SagresValidacao validacao;

    private IntegrationResult result;
}