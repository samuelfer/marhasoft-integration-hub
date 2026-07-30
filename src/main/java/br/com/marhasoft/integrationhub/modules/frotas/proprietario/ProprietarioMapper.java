package br.com.marhasoft.integrationhub.modules.frotas.proprietario;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioPayload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProprietarioMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public ProprietarioPayload toPayload(ProprietarioBatchRequest request,
                                    IntegrationAction action) {

        ProprietarioPayload payload = ProprietarioPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(proprietario -> mapItem(proprietario, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private ProprietarioItemPayload mapItem(
            ProprietarioRequest request,
            IntegrationAction action) {

        return ProprietarioItemPayload.builder()
                .cpfCnpj(request.getCpfCnpj())
                .nome(request.getNome())
                .action(action)
                .build();
    }

}