package br.com.marhasoft.integrationhub.modules.contabilidade.acao;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;

import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoItemPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.acao.model.AcaoRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AcaoMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public AcaoPayload toPayload(AcaoBatchRequest request,
                                 IntegrationAction action) {

        AcaoPayload payload = AcaoPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(acao -> mapItem(acao, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private AcaoItemPayload mapItem(
            AcaoRequest request,
            IntegrationAction action) {

        return AcaoItemPayload.builder()
                .codigoUnidadeGestora(request.getCodigoUnidadeGestora())
                .codigoAcao(request.getCodigoAcao())
                .descricaoAcao(request.getDescricaoAcao())
                .tipoAcao(request.getTipoAcao())
                .descricaoMeta(request.getDescricaoMeta())
                .unidadeMedida(request.getUnidadeMedida())
                .action(action)
                .build();
    }

}