package br.com.marhasoft.integrationhub.core.tce;

import br.com.marhasoft.integrationhub.core.tce.model.TipoEnvio;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class SagresUris {

    public String envios() {
        return SagresEndpoint.ENVIOS.toString();
    }

    public String envio(String protocolo) {
        return envios() + "/" + protocolo;
    }

    public String consolidacoes(String protocolo) {
        return envio(protocolo) + SagresEndpoint.CONSOLIDACOES;
    }

    public String entidade(String protocolo, String entidade) {
        return envio(protocolo)
               + SagresEndpoint.ENTIDADES
               + "/"
               + entidade;
    }

    public String envioPorUnidadeGestoraTipoCompetencia(
            String codigoUnidadeGestora,
            TipoEnvio tipoEnvio,
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