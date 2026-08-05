package br.com.marhasoft.integrationhub.core.tce.model;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TceValidacaoResult {

    private TceValidacao validacao;

    private IntegrationResult result;
}