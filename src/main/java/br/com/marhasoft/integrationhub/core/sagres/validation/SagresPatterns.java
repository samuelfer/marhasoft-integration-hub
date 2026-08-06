package br.com.marhasoft.integrationhub.core.sagres.validation;

import java.util.regex.Pattern;

public final class SagresPatterns {

    public static final Pattern PROTOCOLO_ENVIO =
            Pattern.compile("^\\d{6}\\.\\d{2}\\.\\d{5}-\\d{4}$");

    private SagresPatterns() {
    }
}