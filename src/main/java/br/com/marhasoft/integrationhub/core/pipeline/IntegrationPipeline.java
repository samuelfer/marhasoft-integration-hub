package br.com.marhasoft.integrationhub.core.pipeline;

import java.util.Comparator;
import java.util.List;

import br.com.marhasoft.integrationhub.core.result.IntegrationStatus;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

public class IntegrationPipeline<T, R> {

    private final List<PipelineStep<T, R>> steps;

    public IntegrationPipeline(List<PipelineStep<T, R>> steps) {
        this.steps = steps.stream()
                .sorted(Comparator.comparing(PipelineStep::phase))
                .toList();
    }

    public IntegrationContext<T, R> execute(
            IntegrationContext<T, R> context) {

        for (PipelineStep<T, R> step : steps) {

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