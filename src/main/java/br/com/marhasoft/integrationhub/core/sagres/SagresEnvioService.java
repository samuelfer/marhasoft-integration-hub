package br.com.marhasoft.integrationhub.core.sagres;

import br.com.marhasoft.integrationhub.core.authentication.IntegrationClient;
import br.com.marhasoft.integrationhub.core.configuration.IntegrationConfiguration;
import br.com.marhasoft.integrationhub.core.context.IntegrationContext;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresEnvioResult;
import br.com.marhasoft.integrationhub.core.sagres.model.SagresTipoEnvio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SagresEnvioService {

    private final SagresEnvioClient sagresEnvioClient;

    public SagresEnvioResult consultarEnvioPorUnidadeGestoraTipoCompetencia(
            IntegrationConfiguration configuration,
            IntegrationClient integrationClient,
            String codigoUnidadeGestora,
            SagresTipoEnvio tipoEnvio,
            LocalDate competencia) {

        IntegrationContext<Void, Void> context =
                new IntegrationContext<>(
                        null,
                        null,
                        configuration);

        context.setAuthentication(integrationClient);

        return sagresEnvioClient.consultarEnvioPorUnidadeGestoraTipoCompetencia(
                context,
                codigoUnidadeGestora,
                tipoEnvio,
                competencia);
    }

    public SagresEnvioResult consultarEnvioPorProtocolo(
            IntegrationConfiguration configuration,
            IntegrationClient integrationClient,
            String protocolo) {

        IntegrationContext<Void, Void> context =
                new IntegrationContext<>(
                        null,
                        null,
                        configuration);

        context.setAuthentication(integrationClient);

        return sagresEnvioClient.consultarEnvioPorProtocolo(
                context, protocolo);
    }

    public SagresEnvioResult deletarProtocolo(
            IntegrationConfiguration configuration,
            IntegrationClient integrationClient,
            String protocolo) {

        IntegrationContext<Void, Void> context =
                new IntegrationContext<>(
                        null,
                        null,
                        configuration);

        context.setAuthentication(integrationClient);

        return sagresEnvioClient.deletarEnvioPorProtocolo(context, protocolo);
    }
}