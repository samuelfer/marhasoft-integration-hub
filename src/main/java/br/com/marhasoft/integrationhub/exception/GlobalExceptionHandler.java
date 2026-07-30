package br.com.marhasoft.integrationhub.exception;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import br.com.marhasoft.integrationhub.core.model.Identificavel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiError> handleMissingHeader(
            MissingRequestHeaderException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.MISSING_HEADER.getCode())
                .message("O cabeçalho obrigatório '" + ex.getHeaderName() + "' não foi informado.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ApiError> handleIntegrationException(
            IntegrationException ex,
            HttpServletRequest request) {

        HttpStatus status = switch (ex.getErrorCode()) {
            case INVALID_INTEGRATION_KEY -> HttpStatus.UNAUTHORIZED;
            case RESOURCE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.BAD_REQUEST;
        };

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> formatError(error, ex))
                .toList();

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.VALIDATION_ERROR.getCode())
                .message("Erro de validação.")
                .details(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {
        System.out.println(
                "Erro inesperado: " + ex.getMessage() + " - " + request.getRequestURI()
        );
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCode.INTERNAL_ERROR.getCode())
                .message("Ocorreu um erro interno inesperado.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.internalServerError().body(error);
    }

    private String formatError(
            FieldError error,
            MethodArgumentNotValidException ex) {

        Object target = ex.getBindingResult().getTarget();

        if (!(target instanceof BatchRequest<?> batch)) {
            return defaultMessage(error);
        }

        Matcher matcher = Pattern.compile("elementos\\[(\\d+)]")
                .matcher(error.getField());

        if (!matcher.find()) {
            return defaultMessage(error);
        }

        int index = Integer.parseInt(matcher.group(1));

        if (index >= batch.getElementos().size()) {
            return defaultMessage(error);
        }

        Object elemento = batch.getElementos().get(index);

        if (elemento instanceof Identificavel identificavel) {
            return identificavel.getIdentificador() + ": " + error.getDefaultMessage();
        }

        return defaultMessage(error);
    }

    private String defaultMessage(FieldError error) {
        return String.format("%s: %s",
                error.getField(),
                error.getDefaultMessage());
    }
}
