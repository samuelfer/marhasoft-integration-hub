package br.com.marhasoft.integrationhub.exception;

import br.com.marhasoft.integrationhub.core.model.BatchRequest;
import br.com.marhasoft.integrationhub.core.model.Identificavel;
import br.com.marhasoft.oauth.client.exception.OAuthClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(OAuthClientException.class);


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
        log.error("Erro  de cabeçalho obrigatório", ex);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code("MIH-004")
                .message("Método HTTP não suportado.")
                .details(List.of(ex.getMessage()))
                .path(request.getRequestURI())
                .build();

        log.error("Método não suportado", ex);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(error);
    }

    @ExceptionHandler(InvalidIntegrationKeyException.class)
    public ResponseEntity<ApiError> handleInvalidIntegrationKey(
            InvalidIntegrationKeyException ex,
            HttpServletRequest request) {

        ApiError error =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .code("MIH-004")
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        log.error("Chave de integração inválida", ex);

        return ResponseEntity.badRequest()
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String message = "O valor informado é inválido.";

        if ("competencia".equals(ex.getName())) {

            message = "O parâmetro 'competencia' deve estar no formato yyyy-MM-dd.";

        } else if ("tipoEnvio".equals(ex.getName())) {

            message = "O tipo de envio informado é inválido.";
        }

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("MIH-005")
                .message("Erro de validação.")
                .details(List.of(message))
                .path(request.getRequestURI())
                .build();

        log.error("Formato do parâmetro é inválido.", ex);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidIntegrationProtocolException.class)
    public ResponseEntity<ApiError> handleInvalidIntegrationProtocolException(
            InvalidIntegrationProtocolException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("MIH-005")
                .message("Erro de validação.")
                .details(List.of(ex.getMessage()))
                .path(request.getRequestURI())
                .build();

        log.error("Protocolo de envio inválido.", ex);

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
        log.error("Erro  de integração", ex);
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
        log.error("Erro  de validação", ex);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(OAuthClientException.class)
    public ResponseEntity<ApiError> handleOAuthClientException(
            OAuthClientException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_GATEWAY.value())
                .code(ErrorCode.OAUTH_AUTHENTICATION_ERROR.getCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        log.error("Erro ao obter Access Token do Authorization Server.", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .code("MIH-404")
                .message("O recurso solicitado não foi encontrado.")
                .path(request.getRequestURI())
                .build();

        log.error("Recurso solicitado não encontrado", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCode.INTERNAL_ERROR.getCode())
                .message("Ocorreu um erro interno inesperado.")
                .path(request.getRequestURI())
                .build();

        log.error("Erro inesperado", ex);

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
