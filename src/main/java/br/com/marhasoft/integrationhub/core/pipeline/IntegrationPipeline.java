package br.com.marhasoft.integrationhub.core.pipeline;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

@Component
public class IntegrationPipeline<T, R> {

    private final List<PipelineStep<T, R>> steps;

    public IntegrationPipeline(List<PipelineStep<T, R>> steps) {
        this.steps = steps;
    }

    public IntegrationContext<T, R> execute(
            IntegrationContext<T, R> context) {

        steps.stream()
                .sorted(Comparator.comparing(PipelineStep::phase))
                .forEach(step -> step.execute(context));

        return context;
    }
}