package br.com.marhasoft.integrationhub.core.validation;

public record ValidationError(
        String code,
        String message) {
}