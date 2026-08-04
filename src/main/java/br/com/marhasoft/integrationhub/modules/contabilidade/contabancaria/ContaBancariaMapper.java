package br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaItemPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.contabancaria.model.ContaBancariaRequest;import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContaBancariaMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public ContaBancariaPayload toPayload(ContaBancariaBatchRequest request,
                                                IntegrationAction action) {

        ContaBancariaPayload payload = ContaBancariaPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(conciliacao -> mapItem(conciliacao, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }

    /**
     * Converte a requisição em um item do payload.
     */
    private ContaBancariaItemPayload mapItem(
            ContaBancariaRequest request,
            IntegrationAction action) {

        return ContaBancariaItemPayload.builder()
                .numeroContaBancaria(request.getNumeroContaBancaria())
                .codigoBancoContaBancaria(request.getCodigoBancoContaBancaria())
                .numeroAgenciaContaBancaria(request.getNumeroAgenciaContaBancaria())
                .descricaoContaBancaria(request.getDescricaoContaBancaria())
                .tipoContaBancaria(request.getTipoContaBancaria())
                .cnpjGerenciaContaBancaria(request.getCnpjGerenciaContaBancaria())
                .action(action)
                .build();
    }

}