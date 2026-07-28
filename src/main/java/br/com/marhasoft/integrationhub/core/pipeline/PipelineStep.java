package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public interface PipelineStep<T, R> {

    PipelinePhase phase();

    void execute(IntegrationContext<T, R> context);

}