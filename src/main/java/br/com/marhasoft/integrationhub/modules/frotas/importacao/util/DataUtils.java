package br.com.marhasoft.integrationhub.modules.frotas.importacao.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DataUtils {

    private static final DateTimeFormatter FORMATO_DDMMAAAA = DateTimeFormatter.ofPattern("ddMMyyyy");

    private DataUtils() {
    }

    public static LocalDate parseDataDDMMAAAA(String bruto) {
        if (bruto == null || bruto.isBlank()) {
            return null;
        }
        return LocalDate.parse(bruto.trim(), FORMATO_DDMMAAAA);
    }
}
