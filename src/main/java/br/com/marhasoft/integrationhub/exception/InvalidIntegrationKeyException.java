package br.com.marhasoft.integrationhub.exception;

public class InvalidIntegrationKeyException extends IntegrationException {

    public InvalidIntegrationKeyException() {
        super(
                ErrorCode.INVALID_INTEGRATION_KEY,
                "Chave de integração inválida."
        );
    }

}