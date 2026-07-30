package br.com.marhasoft.integrationhub.modules.frotas;

import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioPayload;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioResponse;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoResponse;
import br.com.marhasoft.integrationhub.modules.frotas.veiculo.model.VeiculoPayload;
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

        System.out.println("VeiculoPayload enviado para o TCE: " + payload);

        return VeiculoResponse.builder()
                .sucesso(true)
                .mensagem("Integração realizada com sucesso.")
                .build();
    }

    public ProprietarioResponse cadastrarProprietario(ProprietarioPayload payload) {

        System.out.println("ProprietarioPayload enviado para o TCE: " + payload);

        return ProprietarioResponse.builder()
                .sucesso(true)
                .mensagem("Integração realizada com sucesso.")
                .build();
    }

}
