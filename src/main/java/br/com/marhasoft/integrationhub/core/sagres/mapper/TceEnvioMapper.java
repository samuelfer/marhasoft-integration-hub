package br.com.marhasoft.integrationhub.core.sagres.mapper;

import br.com.marhasoft.integrationhub.core.sagres.model.*;
import org.springframework.stereotype.Component;

@Component
public class TceEnvioMapper {


    /**
     * Converte a resposta do TCE para o modelo interno.
     */
    public SagresEnvio toModel(SagresEnvioResponse response) {

        return SagresEnvio.builder()
                .codigoUnidadeGestora(response.getCodigoUnidadeGestora())
                .tipoEnvio(response.getTipoEnvio())
                .competencia(response.getCompetencia())
                .statusEnvio(response.getStatusEnvio())
                .protocoloEnvio(response.getProtocoloEnvio())
                .build();
    }

}