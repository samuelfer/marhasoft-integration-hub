package br.com.marhasoft.integrationhub.core.tce.model;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TceEnvioResult {

    private TceEnvio envio;

    private IntegrationResult result;

}