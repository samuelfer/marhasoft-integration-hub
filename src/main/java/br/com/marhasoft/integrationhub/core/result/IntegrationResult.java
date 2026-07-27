package br.com.marhasoft.integrationhub.core.result;

import br.com.marhasoft.integrationhub.core.validation.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegrationResult {

    private IntegrationStatus status = IntegrationStatus.PENDING;

    private final List<IntegrationMessage> messages = new ArrayList<>();

    private Exception exception;

    public IntegrationStatus getStatus() {
        return status;
    }

    public void setStatus(IntegrationStatus status) {
        this.status = status;
    }

    public List<IntegrationMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void addInfo(String code, String message) {
        messages.add(new IntegrationMessage(
                MessageType.INFO,
                code,
                message));
    }

    public void addWarning(String code, String message) {
        messages.add(new IntegrationMessage(
                MessageType.WARNING,
                code,
                message));
    }

    public void addError(String code, String message) {
        messages.add(new IntegrationMessage(
                MessageType.ERROR,
                code,
                message));
    }

    public boolean hasErrors() {
        return messages.stream()
                .anyMatch(message -> message.type() == MessageType.ERROR);
    }

    public boolean hasWarnings() {
        return messages.stream()
                .anyMatch(message -> message.type() == MessageType.WARNING);
    }

    public void addError(IntegrationMessage message) {
        messages.add(message);
    }

    public void addError(ValidationError error) {
        addError(error.code(), error.message());
    }

}