package br.com.marhasoft.integrationhub.modules.frotas.resolver;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClient;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClientResponse;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.resolver.LocadorDependencyResolver;
import br.com.marhasoft.integrationhub.support.VeiculoTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocadorDependencyResolverTest {

    @Mock
    private PessoaClient pessoaClient;

    @InjectMocks
    private LocadorDependencyResolver resolver;

    private VeiculoRequest request;

    @BeforeEach
    void setUp() {
        request = VeiculoTestDataFactory.umVeiculo();
    }

    @Test
    @DisplayName("Deve retornar a chave de dependência")
    void deveRetornarDependencyKey() {

        DependencyKey key = resolver.getDependencyKey();

        assertThat(key.module()).isEqualTo(IntegrationModule.FROTAS);
        assertThat(key.operation()).isEqualTo(IntegrationOperation.VEICULO);
        assertThat(key.action()).isEqualTo(IntegrationAction.CREATE);
    }

    @Test
    @DisplayName("Deve suportar quando existir locador")
    void deveSuportarQuandoExistirLocador() {

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        assertThat(resolver.supports(context)).isTrue();
    }

    @Test
    @DisplayName("Não deve suportar quando não existir locador")
    void naoDeveSuportarQuandoNaoExistirLocador() {

        request.setCpfCnpjLocador(null);

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        assertThat(resolver.supports(context)).isFalse();
    }

    @Test
    @DisplayName("Deve resolver o locador")
    void deveResolverLocador() {

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        PessoaClientResponse locador = new PessoaClientResponse();

        when(pessoaClient.buscar(request.getCpfCnpjLocador()))
                .thenReturn(Optional.of(locador));

        resolver.resolve(context);

        assertThat(
                context.getAttribute("locador", PessoaClientResponse.class))
                .isSameAs(locador);

        verify(pessoaClient)
                .buscar(request.getCpfCnpjLocador());

        verifyNoMoreInteractions(pessoaClient);
    }

    @Test
    @DisplayName("Deve armazenar nulo quando o locador não existir")
    void deveArmazenarNuloQuandoNaoEncontrarLocador() {

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        when(pessoaClient.buscar(request.getCpfCnpjLocador()))
                .thenReturn(Optional.empty());

        resolver.resolve(context);

        assertThat(
                context.getAttribute("locador", PessoaClientResponse.class))
                .isNull();

        verify(pessoaClient)
                .buscar(request.getCpfCnpjLocador());

        verifyNoMoreInteractions(pessoaClient);
    }

    private IntegrationContext<VeiculoRequest, Object, Object> criarContexto() {

        return new IntegrationContext<>(
                request,
                null,
                null);
    }
}