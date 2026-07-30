package br.com.marhasoft.integrationhub.exception;

import lombok.Getter;

@Getter
public abstract class IntegrationException extends RuntimeException {

    private final ErrorCode errorCode;

    protected IntegrationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}