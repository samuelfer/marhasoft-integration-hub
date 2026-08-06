package br.com.marhasoft.integrationhub.core.sagres.model;

import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagresEnvioResult {

    private SagresEnvio envio;

    private IntegrationResult result;

}