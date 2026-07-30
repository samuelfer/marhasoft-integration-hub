package br.com.marhasoft.integrationhub.modules.frotas.locador;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorPayload;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocadorMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public LocadorPayload toPayload(LocadorBatchRequest request,
                                    IntegrationAction action) {

        LocadorPayload payload = LocadorPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(locador -> mapItem(locador, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private LocadorItemPayload mapItem(
            LocadorRequest request,
            IntegrationAction action) {

        return LocadorItemPayload.builder()
                .cpfCnpj(request.getCpfCnpj())
                .nome(request.getNome())
                .action(action)
                .build();
    }

}