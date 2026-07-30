package br.com.marhasoft.integrationhub.core.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class BeanValidationService {

    private final Validator validator;

    public BeanValidationService(Validator validator) {
        this.validator = validator;
    }

    public <T> ValidationResult validate(T object) {

        if (object == null) {
            throw new IllegalArgumentException("O objeto a ser validado não pode ser nulo.");
        }

        ValidationResult result = ValidationResult.valid();

        for (ConstraintViolation<T> violation : validator.validate(object)) {
            result.addError(
                    violation.getPropertyPath().toString(),
                    violation.getMessage());
        }

        return result;
    }

}