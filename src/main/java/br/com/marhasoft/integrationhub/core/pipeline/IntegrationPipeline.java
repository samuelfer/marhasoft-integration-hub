package br.com.marhasoft.integrationhub.core.pipeline;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;

@Component
public class IntegrationPipeline {

    private final List<PipelineStep> steps;

    public IntegrationPipeline(List<PipelineStep> steps) {
        this.steps = steps;
    }

    public void execute(IntegrationContext<?, ?> context) {
        steps.stream()
                .sorted(Comparator.comparingInt(PipelineStep::getOrder))
                .forEach(step -> step.execute(context));
    }
}