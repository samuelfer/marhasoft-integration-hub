package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public interface PipelineStep<T, P, R> {

    PipelinePhase phase();

    void execute(IntegrationContext<T, P, R> context);

}