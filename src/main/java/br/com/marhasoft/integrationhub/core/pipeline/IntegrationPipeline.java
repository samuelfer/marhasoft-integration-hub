package br.com.marhasoft.integrationhub.core.pipeline;

import java.util.Comparator;
import java.util.List;

import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public class IntegrationPipeline<T, P> {

    private final List<PipelineStep<T, P>> steps;

    public IntegrationPipeline(List<PipelineStep<T, P>> steps) {
        this.steps = steps.stream()
                .sorted(Comparator.comparing(PipelineStep::phase))
                .toList();
    }

    public IntegrationContext<T, P> execute(
            IntegrationContext<T, P> context) {

        for (PipelineStep<T, P> step : steps) {

            step.execute(context);

            if (context.getResult().hasErrors()) {
                context.getResult().setStatus(IntegrationStatus.ERROR);
                return context;
            }
        }

        context.getResult().setStatus(IntegrationStatus.SUCCESS);

        return context;
    }
}