package br.com.marhasoft.integrationhub.core.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<ValidationError> errors = new ArrayList<>();

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public void addError(Enum<?>  code, String message) {
        errors.add(new ValidationError(code.name(), message));
    }

    public void merge(ValidationResult validationResult) {
        this.errors.addAll(validationResult.getErrors());
    }

    public void addError(String message) {
        errors.add(new ValidationError(null, message));
    }

    public void addError(String field, String message) {
        errors.add(new ValidationError(field, message));
    }

    public static ValidationResult valid() {
        return new ValidationResult();
    }

}