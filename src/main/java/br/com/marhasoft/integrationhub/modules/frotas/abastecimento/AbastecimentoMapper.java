package br.com.marhasoft.integrationhub.modules.frotas.abastecimento;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.abastecimento.model.AbastecimentoPayload;
import br.com.marhasoft.integrationhub.modules.frotas.situacaofrota.model.AbastecimentoRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AbastecimentoMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public AbastecimentoPayload toPayload(AbastecimentoBatchRequest request,
                                          IntegrationAction action) {

        AbastecimentoPayload payload = AbastecimentoPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(abastecimento -> mapItem(abastecimento, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private AbastecimentoItemPayload mapItem(
            AbastecimentoRequest request,
            IntegrationAction action) {

        return AbastecimentoItemPayload.builder()
                .tipoCombustivel(request.getTipoCombustivel())
                .tipoCategoriaFrota(request.getTipoCategoriaFrota())
                .codigo(request.getTipoCategoriaFrota())
                .quantidade(request.getQuantidade())
                .action(action)
                .build();
    }

}