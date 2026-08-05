package br.com.marhasoft.integrationhub.core.tce.mapper;

import br.com.marhasoft.integrationhub.core.tce.TceValidacaoDetalhesResponse;
import br.com.marhasoft.integrationhub.core.tce.TceValidacaoResponse;
import br.com.marhasoft.integrationhub.core.tce.model.TceValidacao;
import br.com.marhasoft.integrationhub.core.tce.model.TceValidacaoDetalhes;
import org.springframework.stereotype.Component;

@Component
public class TceValidacaoMapper {

    /**
     * Converte a resposta do TCE para o modelo interno.
     */
    public TceValidacao toModel(TceValidacaoResponse response) {

        return TceValidacao.builder()
                .protocoloEnvio(response.getProtocoloEnvio())
                .competencia(response.getCompetencia())
                .chaveValidacao(response.getChaveValidacao())
                .statusValidacao(response.getStatusValidacao())
                .detalhes(toModel(response.getDetalhes()))
                .build();
    }

    private TceValidacaoDetalhes toModel(
            TceValidacaoDetalhesResponse response) {

        if (response == null) {
            return null;
        }

        return TceValidacaoDetalhes.builder()
                .build();
    }

    // demais métodos...
}