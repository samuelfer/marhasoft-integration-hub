package br.com.marhasoft.integrationhub.core.pipeline;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public abstract class AbstractPipelineStep<T, R>
        implements PipelineStep<T, R> {

    @Override
    public final void execute(IntegrationContext<T, R> context) {

        before(context);

        doExecute(context);

        after(context);
    }

    protected void before(IntegrationContext<T, R> context) {
    }

    protected void after(IntegrationContext<T, R> context) {
    }

    protected abstract void doExecute(IntegrationContext<T, R> context);

}