package br.com.marhasoft.integrationhub.exception;

/**
 * Exceção lançada quando o protocolo de envio informado
 * não atende ao formato esperado pelo SAGRES.
 */
public class InvalidIntegrationProtocolException extends RuntimeException {

    public InvalidIntegrationProtocolException(String message) {
        super(message);
    }

}