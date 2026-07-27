package br.com.marhasoft.integrationhub.core.result;

public enum IntegrationStatus {
    CREATED,
    VALIDATED,
    DEPENDENCIES_RESOLVED,
    MAPPED,
    AUTHENTICATED,
    SENT,
    SUCCESS,
    ERROR
}