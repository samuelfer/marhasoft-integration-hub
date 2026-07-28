package br.com.marhasoft.integrationhub.core.dependencies.step;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyRegistry;
import br.com.marhasoft.integrationhub.core.pipeline.AbstractPipelineStep;
import br.com.marhasoft.integrationhub.core.pipeline.PipelinePhase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DependencyResolutionStep<T, R> extends AbstractPipelineStep<T, R> {

    private final DependencyRegistry dependencyRegistry;

    @Override
    public PipelinePhase phase() {
        return PipelinePhase.DEPENDENCY_RESOLUTION;
    }

    @Override
    protected void doExecute(IntegrationContext<T, R> context) {
        dependencyRegistry.resolve(context);
    }
}