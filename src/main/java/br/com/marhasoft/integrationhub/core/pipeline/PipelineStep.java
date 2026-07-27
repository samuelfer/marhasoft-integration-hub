package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public interface PipelineStep {

    int getOrder();

    void execute(IntegrationContext<?, ?> context);
}