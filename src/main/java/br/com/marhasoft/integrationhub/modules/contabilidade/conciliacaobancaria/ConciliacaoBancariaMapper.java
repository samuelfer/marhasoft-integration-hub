package br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaItemPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.conciliacaobancaria.model.ConciliacaoBancariaRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConciliacaoBancariaMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public ConciliacaoBancariaPayload toPayload(ConciliacaoBancariaBatchRequest request,
                                                IntegrationAction action) {

        ConciliacaoBancariaPayload payload = ConciliacaoBancariaPayload.builder()
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
    private ConciliacaoBancariaItemPayload mapItem(
            ConciliacaoBancariaRequest request,
            IntegrationAction action) {

        return ConciliacaoBancariaItemPayload.builder()
                .numeroContaBancaria(request.getNumeroContaBancaria())
                .numeroAgenciaContaBancaria(request.getNumeroAgenciaContaBancaria())
                .codigoBancoContaBancaria(request.getCodigoBancoContaBancaria())
                .numero(request.getNumero())
                .tipoContaBancaria(request.getTipoContaBancaria())
                .cnpjGerenciaContaBancaria(request.getCnpjGerenciaContaBancaria())
                .numeroConciliacao(request.getNumeroConciliacao())
                .tipoConciliacao(request.getTipoConciliacao())
                .descricao(request.getDescricao())
                .data(request.getData())
                .numeroCheque(request.getNumeroCheque())
                .numeroDocumentoDebito(request.getNumeroDocumentoDebito())
                .valorConciliacao(request.getValorConciliacao())
                .action(action)
                .build();
    }

}