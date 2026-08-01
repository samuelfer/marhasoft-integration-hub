package br.com.marhasoft.integrationhub.modules.frotas.situacaofrota;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.SituacaoFrotaRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SituacaoFrotaMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public SituacaoFrotaPayload toPayload(SituacaoFrotaBatchRequest request,
                                          IntegrationAction action) {

        SituacaoFrotaPayload payload = SituacaoFrotaPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(situacaoFrota -> mapItem(situacaoFrota, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private SituacaoFrotaItemPayload mapItem(
            SituacaoFrotaRequest request,
            IntegrationAction action) {

        return SituacaoFrotaItemPayload.builder()
                .dataSituacao(request.getDataSituacao())
                .tipoSituacao(request.getTipoSituacao())
                .tipoCategoriaFrota(request.getTipoCategoriaFrota())
                .codigo(request.getCodigo())
                .action(action)
                .build();
    }

}