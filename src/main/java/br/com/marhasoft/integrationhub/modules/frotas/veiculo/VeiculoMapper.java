package br.com.marhasoft.integrationhub.modules.frotas.veiculo;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoBatchRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoRequest;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoItemPayload;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoPayload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VeiculoMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public VeiculoPayload toPayload(VeiculoBatchRequest request,
                                    IntegrationAction action) {

        VeiculoPayload payload = VeiculoPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(veiculo -> mapItem(veiculo, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private VeiculoItemPayload mapItem(
            VeiculoRequest request,
            IntegrationAction action) {

        return VeiculoItemPayload.builder()
                .placa(request.getPlaca())
                .anoModelo(request.getAnoModelo())
                .numeroRenavan(request.getNumeroRenavan())
                .cpfCnpjProprietario(request.getCpfCnpjProprietario())
                .cpfCnpjLocador(request.getCpfCnpjLocador())
                .numeroModelo(request.getNumeroModelo())
                .tipoFrota(request.getTipoFrota())
                .action(action)
                .build();
    }

}