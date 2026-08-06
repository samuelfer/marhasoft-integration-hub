package br.com.marhasoft.integrationhub.core.sagres.validation;

import br.com.marhasoft.integrationhub.core.sagres.model.SagresTipoEnvio;
import br.com.marhasoft.integrationhub.exception.InvalidIntegrationProtocolException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitário responsável por validar formatos utilizados pelo SAGRES.
 */
public final class SagresFormatValidator {

    /**
     * Formato do protocolo:
     *
     * <pre>
     * 201001.00.00001-2025
     * </pre>
     */
    private static final Pattern PROTOCOLO_PATTERN =
            Pattern.compile("^(\\d{6})\\.(\\d{2})\\.(\\d{5})-(\\d{4})$");

    private SagresFormatValidator() {
        // Classe utilitária.
    }

    /**
     * Valida o formato do protocolo de envio.
     *
     * @param protocolo protocolo informado.
     * @throws InvalidIntegrationProtocolException caso o protocolo seja inválido.
     */
    public static void validateProtocoloEnvio(String protocolo) {

        if (protocolo == null || protocolo.isBlank()) {
            throw new InvalidIntegrationProtocolException(
                    "O protocolo de envio é obrigatório.");
        }

        Matcher matcher = PROTOCOLO_PATTERN.matcher(protocolo);

        if (!matcher.matches()) {
            throw new InvalidIntegrationProtocolException(
                    "O protocolo de envio deve estar no formato '201001.00.00001-2025'.");
        }

        String codigoTipoEnvio = matcher.group(2);

        if (!SagresTipoEnvio.containsCodigo(codigoTipoEnvio)) {
            throw new InvalidIntegrationProtocolException(
                    "O código do tipo de envio informado no protocolo é inválido.");
        }
    }

}