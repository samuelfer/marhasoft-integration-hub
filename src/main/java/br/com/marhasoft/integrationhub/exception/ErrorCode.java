package br.com.marhasoft.integrationhub.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MISSING_HEADER("MIH-001"),
    INVALID_INTEGRATION_KEY("MIH-002"),
    VALIDATION_ERROR("MIH-003"),
    RESOURCE_NOT_FOUND("MIH-004"),
    INTERNAL_ERROR("MIH-999"),
    OAUTH_AUTHENTICATION_ERROR("MI-OAUTH-001");
    private final String code;

}
