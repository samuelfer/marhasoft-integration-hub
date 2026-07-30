package br.com.marhasoft.integrationhub.modules.frotas.veiculo.resolver;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProprietarioDependencyResolver
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
        return true;
    }

    @Override
    public void resolve(
            IntegrationContext<VeiculoRequest, Object, Object> context) {

        pessoaClient.buscar(context.getRequest().getCpfCnpjProprietario())
                .ifPresent(proprietario ->
                        context.putAttribute("proprietario", proprietario));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}