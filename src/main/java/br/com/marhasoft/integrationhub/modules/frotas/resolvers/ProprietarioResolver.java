package br.com.marhasoft.integrationhub.modules.frotas.resolvers;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.dependencies.resolver.AbstractDependencyResolver;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.domain.model.VeiculoPayload;
import org.springframework.stereotype.Component;

@Component
public class ProprietarioResolver
        extends AbstractDependencyResolver<VeiculoRequest, VeiculoPayload> {

    @Override
    public DependencyKey key() {
        return new DependencyKey(
                IntegrationModule.FROTAS,
                IntegrationOperation.VEICULO,
                IntegrationAction.CREATE);
    }

    @Override
    public void resolve(IntegrationContext<VeiculoRequest, VeiculoPayload> context) {
    }
}