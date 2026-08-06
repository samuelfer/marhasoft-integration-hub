package br.com.marhasoft.integrationhub.core.sagres;

import br.com.marhasoft.integrationhub.core.sagres.model.SagresTipoEnvio;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class SagresUris {

    public String envios() {
        return SagresEndpoint.ENVIOS.getPath();
    }

    public String envio(String protocolo) {
        return envios() + "/" + protocolo;
    }

    public String consolidacoes(String protocolo) {
        return envio(protocolo) + SagresEndpoint.CONSOLIDACOES.getPath();
    }

    public String entidade(String protocolo, String entidade) {
        return envio(protocolo)
               + SagresEndpoint.ENTIDADES.getPath()
               + "/"
               + entidade;
    }

    public String envioPorUnidadeGestoraTipoCompetencia(
            String codigoUnidadeGestora,
            SagresTipoEnvio tipoEnvio,
            LocalDate competencia) {

        return envios()
               + "/"
               + codigoUnidadeGestora
               + "/"
               + tipoEnvio
               + "/"
               + competencia;
    }

    public static String validacoes(String protocolo, String chaveValidacao) {
        return envio(protocolo)
               + "/validacoes?chaveValidacao="
               + chaveValidacao;
    }
}