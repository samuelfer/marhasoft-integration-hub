package br.com.marhasoft.integrationhub.core.result;

public record IntegrationMessage(

        MessageType type,

        String code,

        String message) {
}