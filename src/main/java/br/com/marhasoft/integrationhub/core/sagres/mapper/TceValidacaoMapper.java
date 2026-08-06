package br.com.marhasoft.integrationhub.core.sagres.mapper;

import br.com.marhasoft.integrationhub.core.sagres.SagresValidacaoDetalhesResponse;
import br.com.marhasoft.integrationhub.core.sagres.SagresValidacaoResponse;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresValidacao;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresValidacaoDetalhes;
import org.springframework.stereotype.Component;

@Component
public class TceValidacaoMapper {

    /**
     * Converte a resposta do TCE para o modelo interno.
     */
    public SagresValidacao toModel(SagresValidacaoResponse response) {

        return SagresValidacao.builder()
                .protocoloEnvio(response.getProtocoloEnvio())
                .competencia(response.getCompetencia())
                .chaveValidacao(response.getChaveValidacao())
                .statusValidacao(response.getStatusValidacao())
                .detalhes(toModel(response.getDetalhes()))
                .build();
    }

    private SagresValidacaoDetalhes toModel(
            SagresValidacaoDetalhesResponse response) {

        if (response == null) {
            return null;
        }

        return SagresValidacaoDetalhes.builder()
                .build();
    }

    // demais métodos...
}