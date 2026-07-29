package br.com.marhasoft.integrationhub.modules.frotas.resolver;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client.PessoaClient;
import br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client.response.PessoaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocadorDependencyResolver
        implements DependencyResolver<
        VeiculoRequest,
        Object,
        Object> {

    private final PessoaClient pessoaClient;

    @Override
    public DependencyKey getDependencyKey() {
        return new DependencyKey(
                IntegrationModule.FROTAS,
                IntegrationOperation.VEICULO,
                IntegrationAction.CREATE);
    }

    @Override
    public boolean supports(
            IntegrationContext<VeiculoRequest, Object, Object> context) {
        return context.getRequest().getCpfCnpjLocador() != null;
    }

    @Override
    public void resolve(
            IntegrationContext<VeiculoRequest, Object, Object> context) {

        PessoaResponse locador = pessoaClient.buscar(
                        context.getRequest().getCpfCnpjLocador())
                .orElse(null);

        context.putAttribute("locador", locador);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}