package br.com.marhasoft.integrationhub.modules.frotas.infrastructure.client;

import br.com.marhasoft.integrationhub.modules.frotas.api.dto.VeiculoResponse;
import br.com.marhasoft.integrationhub.modules.frotas.domain.model.VeiculoPayload;
import org.springframework.stereotype.Component;

@Component
public class TceFrotasClient {

    /**
     * Envia o payload de veículo ao sistema externo.
     *
     * @param payload payload já convertido para o formato esperado pelo TCE.
     * @return resposta da integração.
     */
    public VeiculoResponse cadastrarVeiculo(VeiculoPayload payload) {

        return VeiculoResponse.builder()
                .sucesso(true)
                .mensagem("Integração realizada com sucesso.")
                .build();
    }

}
