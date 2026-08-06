package br.com.marhasoft.integrationhub.exception;

public class InvalidIntegrationKeyException extends IntegrationException {

    public InvalidIntegrationKeyException(String mensagem) {
        super(
                ErrorCode.INVALID_INTEGRATION_KEY,
                mensagem
        );
    }

}