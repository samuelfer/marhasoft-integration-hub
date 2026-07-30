package br.com.marhasoft.integrationhub.modules.frotas.resolver;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClient;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.client.PessoaClientResponse;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.resolver.ProprietarioDependencyResolver;
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
class ProprietarioDependencyResolverTest {

    @Mock
    private PessoaClient pessoaClient;

    @InjectMocks
    private ProprietarioDependencyResolver resolver;

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
    @DisplayName("Deve informar que suporta o contexto")
    void deveSuportarContexto() {

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        assertThat(resolver.supports(context)).isTrue();
    }

    @Test
    @DisplayName("Deve resolver o proprietário")
    void deveResolverProprietario() {

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        PessoaClientResponse proprietario = new PessoaClientResponse();

        when(pessoaClient.buscar(request.getCpfCnpjProprietario()))
                .thenReturn(Optional.of(proprietario));

        resolver.resolve(context);

        assertThat(
                context.getAttribute("proprietario", PessoaClientResponse.class))
                .isSameAs(proprietario);

        verify(pessoaClient)
                .buscar(request.getCpfCnpjProprietario());

        verifyNoMoreInteractions(pessoaClient);
    }

    @Test
    @DisplayName("Deve armazenar nulo quando o proprietário não existir")
    void deveArmazenarNuloQuandoNaoEncontrarProprietario() {

        IntegrationContext<VeiculoRequest, Object, Object> context =
                criarContexto();

        when(pessoaClient.buscar(request.getCpfCnpjProprietario()))
                .thenReturn(Optional.empty());

        resolver.resolve(context);

        assertThat(
                context.getAttribute("proprietario", PessoaClientResponse.class))
                .isNull();

        verify(pessoaClient)
                .buscar(request.getCpfCnpjProprietario());

        verifyNoMoreInteractions(pessoaClient);
    }

    private IntegrationContext<VeiculoRequest, Object, Object> criarContexto() {

        return new IntegrationContext<>(
                request,
                null,
                null);
    }
}