package br.com.marhasoft.integrationhub.core.result;

import br.com.marhasoft.integrationhub.core.validation.ValidationError;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegrationResult {

    private IntegrationStatus status = IntegrationStatus.PENDING;

    private final List<IntegrationMessage> messages = new ArrayList<>();

    @JsonIgnore
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

    public void addInfo(IntegrationMessage message) {
        messages.add(message);
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

    public void addWarning(IntegrationMessage message) {
        messages.add(message);
    }

    public void addError(String code, String message) {
        messages.add(new IntegrationMessage(
                MessageType.ERROR,
                code,
                message));
    }

    public void addError(IntegrationMessage message) {
        messages.add(message);
    }

    public void addError(ValidationError error) {
        addError(error.code(), error.message());
    }

    public void addErrors(List<ValidationError> errors) {
        errors.forEach(this::addError);
    }

    public boolean hasErrors() {
        return messages.stream()
                .anyMatch(message -> message.type() == MessageType.ERROR);
    }

    public boolean hasWarnings() {
        return messages.stream()
                .anyMatch(message -> message.type() == MessageType.WARNING);
    }

    public boolean hasError(String code) {
        return hasMessage(MessageType.ERROR, code);
    }

    public boolean hasWarning(String code) {
        return hasMessage(MessageType.WARNING, code);
    }

    public boolean hasInfo(String code) {
        return hasMessage(MessageType.INFO, code);
    }

    public boolean hasError(Enum<?> code) {
        return hasError(code.name());
    }

    public boolean hasWarning(Enum<?> code) {
        return hasWarning(code.name());
    }

    public boolean hasInfo(Enum<?> code) {
        return hasInfo(code.name());
    }

    private boolean hasMessage(MessageType type, String code) {
        return messages.stream()
                .anyMatch(message ->
                        message.type() == type
                                && code.equals(message.code()));
    }

    public void clear() {
        status = IntegrationStatus.PENDING;
        messages.clear();
        exception = null;
    }

    public void merge(IntegrationResult other) {

        this.status = other.getStatus();

        this.messages.addAll(other.getMessages());

        if (other.getException() != null) {
            this.exception = other.getException();
        }
    }

}