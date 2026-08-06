package br.com.marhasoft.integrationhub.core.sagres.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum SagresTipoEnvio {

    ORCAMENTO("00"),
    DIARIO("10"),
    BALANCETE("20"),
    BALANCETE_CONTABILIDADE("21"),
    BALANCETE_FOLHA("22"),
    BALANCETE_FARMACIA("23"),
    BALANCETE_FROTA("24");

    /**
     * Código utilizado na composição do protocolo de envio.
     */
    private final String codigo;

    /**
     * Retorna o tipo de envio correspondente ao código informado.
     *
     * @param codigo código presente no protocolo de envio.
     * @return tipo de envio correspondente.
     */
    public static Optional<SagresTipoEnvio> fromCodigo(String codigo) {

        return Arrays.stream(values())
                .filter(tipo -> tipo.codigo.equals(codigo))
                .findFirst();
    }

    /**
     * Verifica se o código informado representa um tipo de envio válido.
     *
     * @param codigo código presente no protocolo de envio.
     * @return {@code true} quando o código for válido.
     */
    public static boolean containsCodigo(String codigo) {

        return fromCodigo(codigo).isPresent();
    }

}