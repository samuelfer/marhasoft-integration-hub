package br.com.marhasoft.integrationhub.core.tce.mapper;

import br.com.marhasoft.integrationhub.core.tce.model.*;
import org.springframework.stereotype.Component;

@Component
public class TceEnvioMapper {


    /**
     * Converte a resposta do TCE para o modelo interno.
     */
    public TceEnvio toModel(TceEnvioResponse response) {

        return TceEnvio.builder()
                .codigoUnidadeGestora(response.getCodigoUnidadeGestora())
                .tipoEnvio(response.getTipoEnvio())
                .competencia(response.getCompetencia())
                .statusEnvio(response.getStatusEnvio())
                .protocoloEnvio(response.getProtocoloEnvio())
                .build();
    }

}