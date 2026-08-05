//package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria;
//
//import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
//import br.com.marhasoft.integrationhub.core.execution.IntegrationExecutor;
//import br.com.marhasoft.integrationhub.core.result.IntegrationResult;
//import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaBatchRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ContaBancariaService {
//
//    private final ContaBancariaConnector connector;
//    private final IntegrationExecutor executor;
//
//    public IntegrationResult create(ContaBancariaBatchRequest request,
//                                    IntegrationConfiguration configuration) {
//        return executor.execute(connector, request, configuration);
//    }
//}