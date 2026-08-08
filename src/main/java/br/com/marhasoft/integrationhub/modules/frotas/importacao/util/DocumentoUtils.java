package br.com.marhasoft.integrationhub.modules.frotas.importacao.util;

/**
 * Utilitário para normalizar CPF/CNPJ extraídos de campos "Numérico(14)" do layout.
 *
 * Nos arquivos do TCE-PB, o campo cpfcnpj tem largura fixa de 14 posições, comportando
 * tanto CPF (11 dígitos) quanto CNPJ (14 dígitos). Quando o documento é um CPF, o campo
 * costuma vir preenchido com zeros à esquerda (ex: "00012345678901" -> CPF "12345678901").
 *
 * ATENÇÃO: como o layout não indica explicitamente se o documento é CPF ou CNPJ, esta
 * normalização assume a convenção mais comum (zero-padding à esquerda para CPF). Caso o
 * órgão gerador do arquivo NÃO faça esse padding, ajuste este método antes de usar em produção.
 */
public final class DocumentoUtils {

    private DocumentoUtils() {
    }

    public static String normalizarCpfCnpj(String bruto) {
        if (bruto == null) {
            return null;
        }
        String digits = bruto.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return null;
        }
        if (digits.length() == 14) {
            String semZerosEsquerda = digits.replaceFirst("^0+", "");
            if (semZerosEsquerda.length() == 11) {
                return semZerosEsquerda;
            }
        }
        return digits;
    }
}
