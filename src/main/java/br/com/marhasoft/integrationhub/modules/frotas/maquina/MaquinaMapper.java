package br.com.marhasoft.integrationhub.modules.frotas.maquina;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaPayload;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MaquinaMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public MaquinaPayload toPayload(MaquinaBatchRequest request,
                                    IntegrationAction action) {

        MaquinaPayload payload = MaquinaPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(maquina -> mapItem(maquina, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private MaquinaItemPayload mapItem(
            MaquinaRequest request,
            IntegrationAction action) {

        return MaquinaItemPayload.builder()
                .codigo(request.getCodigo())
                .anoFabricacao(request.getAnoFabricacao())
                .cpfCnpjProprietario(request.getCpfCnpjProprietario())
                .cpfCnpjLocador(request.getCpfCnpjLocador())
                .descricao(request.getDescricao())
                .tipoFrota(request.getTipoFrota())
                .action(action)
                .build();
    }

}