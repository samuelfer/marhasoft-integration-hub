package br.com.marhasoft.integrationhub.core.pipeline.dependencies.registry;

import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyKey;
import br.com.marhasoft.integrationhub.core.dependencies.registry.DependencyRegistry;
import br.com.marhasoft.integrationhub.core.dependencies.resolver.DependencyResolver;
import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.core.model.IntegrationModule;
import br.com.marhasoft.integrationhub.core.model.IntegrationOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DependencyRegistryTest {

    private static final DependencyKey KEY =
            new DependencyKey(
                    IntegrationModule.FROTAS,
                    IntegrationOperation.VEICULO,
                    IntegrationAction.CREATE);

    @Test
    @DisplayName("Deve executar o resolver suportado")
    void deveExecutarResolverSuportado() {

        DependencyResolver<String, Object> resolver = mockResolver(1);

        DependencyRegistry registry =
                new DependencyRegistry(List.of(resolver));

        IntegrationContext<String, Object> context =
                mockContext();

        when(resolver.supports(context)).thenReturn(true);

        registry.resolve(context);

        verify(resolver).getDependencyKey();
        verify(resolver).getOrder();
        verify(resolver).supports(context);
        verify(resolver).resolve(context);
        verifyNoMoreInteractions(resolver);
    }

    @Test
    @DisplayName("Não deve executar resolver que não suporta o contexto")
    void naoDeveExecutarResolverNaoSuportado() {

        DependencyResolver<String, Object> resolver = mockResolver(1);

        DependencyRegistry registry =
                new DependencyRegistry(List.of(resolver));

        IntegrationContext<String, Object> context =
                mockContext();

        when(resolver.supports(context)).thenReturn(false);

        registry.resolve(context);

        verify(resolver).getDependencyKey();
        verify(resolver).getOrder();
        verify(resolver).supports(context);
        verify(resolver, never()).resolve(any());
        verifyNoMoreInteractions(resolver);
    }

    @Test
    @DisplayName("Deve executar os resolvedores na ordem configurada")
    void deveExecutarResolversNaOrdem() {

        DependencyResolver<String, Object> resolver1 = mockResolver(2);
        DependencyResolver<String, Object> resolver2 = mockResolver(1);

        DependencyRegistry registry =
                new DependencyRegistry(List.of(resolver1, resolver2));

        IntegrationContext<String, Object> context =
                mockContext();

        when(resolver1.supports(context)).thenReturn(true);
        when(resolver2.supports(context)).thenReturn(true);

        registry.resolve(context);

        InOrder inOrder = inOrder(resolver2, resolver1);

        inOrder.verify(resolver2).supports(context);
        inOrder.verify(resolver2).resolve(context);

        inOrder.verify(resolver1).supports(context);
        inOrder.verify(resolver1).resolve(context);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando não existir resolver para a chave")
    void naoDeveFalharQuandoNaoExistirResolver() {

        DependencyRegistry registry =
                new DependencyRegistry(List.of());

        IntegrationContext<String, Object> context =
                mockContext();

        registry.resolve(context);
    }

    @Test
    @DisplayName("Deve ignorar resolvedores registrados para outra chave")
    void deveIgnorarResolversDeOutraChave() {

        DependencyResolver<String, Object> resolver =
                mock(DependencyResolver.class);

        when(resolver.getDependencyKey())
                .thenReturn(new DependencyKey(
                        IntegrationModule.PESSOAS,
                        IntegrationOperation.SERVIDOR,
                        IntegrationAction.CREATE));

        DependencyRegistry registry =
                new DependencyRegistry(List.of(resolver));

        IntegrationContext<String, Object> context =
                mockContext();

        registry.resolve(context);

        verify(resolver).getDependencyKey();
        verify(resolver).getOrder();
        verifyNoMoreInteractions(resolver);
    }

    @Test
    @DisplayName("Deve interromper a resolução quando um resolver lançar exceção")
    void deveInterromperResolucaoQuandoResolverFalhar() {

        DependencyResolver<String, Object> resolver1 = mockResolver(1);
        DependencyResolver<String, Object> resolver2 = mockResolver(2);

        DependencyRegistry registry =
                new DependencyRegistry(List.of(resolver1, resolver2));

        IntegrationContext<String, Object> context =
                mockContext();

        when(resolver1.supports(context)).thenReturn(true);

        doThrow(new IllegalStateException("Erro"))
                .when(resolver1)
                .resolve(context);

        assertThatThrownBy(() -> registry.resolve(context))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Erro");

        verify(resolver1).supports(context);
        verify(resolver1).resolve(context);

        verify(resolver2, never()).supports(any());
        verify(resolver2, never()).resolve(any());
    }

    @Test
    @DisplayName("Deve manter a ordem de registro quando a prioridade for igual")
    void deveManterOrdemQuandoPrioridadeIgual() {

        DependencyResolver<String, Object> resolver1 = mockResolver(1);
        DependencyResolver<String, Object> resolver2 = mockResolver(1);

        DependencyRegistry registry =
                new DependencyRegistry(List.of(resolver1, resolver2));

        IntegrationContext<String, Object> context =
                mockContext();

        when(resolver1.supports(context)).thenReturn(true);
        when(resolver2.supports(context)).thenReturn(true);

        registry.resolve(context);

        InOrder inOrder = inOrder(resolver1, resolver2);

        inOrder.verify(resolver1).supports(context);
        inOrder.verify(resolver1).resolve(context);

        inOrder.verify(resolver2).supports(context);
        inOrder.verify(resolver2).resolve(context);
    }

    @SuppressWarnings("unchecked")
    private DependencyResolver<String, Object> mockResolver(int order) {

        DependencyResolver<String, Object> resolver =
                mock(DependencyResolver.class);

        when(resolver.getDependencyKey()).thenReturn(KEY);
        when(resolver.getOrder()).thenReturn(order);

        return resolver;
    }

    @SuppressWarnings("unchecked")
    private IntegrationContext<String, Object> mockContext() {

        IntegrationContext<String, Object> context =
                mock(IntegrationContext.class);

        when(context.getDependencyKey()).thenReturn(KEY);

        return context;
    }

}