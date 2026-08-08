package br.com.marhasoft.integrationhub.modules.frotas.importacao.util;

/**
 * Utilitário para extração de campos de linhas de arquivos posicionais (largura fixa),
 * conforme layout do TCE-PB (ex: tabelas 4.50 a 4.55 - Frota).
 *
 * As posições (posicaoInicial / posicaoFinal) seguem a convenção da documentação:
 * 1-based e INCLUSIVAS (ex: campo de 1 a 6 tem 6 caracteres).
 */
public final class FixedWidthUtils {

    private FixedWidthUtils() {
    }

    /**
     * Extrai um trecho da linha com base nas posições informadas (1-based, inclusivas),
     * já removendo espaços em branco nas bordas.
     *
     * Não lança exceção se a linha for menor que o esperado; retorna "" nesse caso,
     * para tolerar arquivos gerados com trim de espaços à direita.
     */
    public static String extract(String linha, int posicaoInicial, int posicaoFinal) {
        if (linha == null || linha.isEmpty()) {
            return "";
        }
        int start = posicaoInicial - 1;
        if (start >= linha.length() || start < 0) {
            return "";
        }
        int end = Math.min(posicaoFinal, linha.length());
        if (start >= end) {
            return "";
        }
        return linha.substring(start, end).trim();
    }

    /**
     * Extrai um trecho sem aplicar trim (útil quando espaços internos importam).
     */
    public static String extractRaw(String linha, int posicaoInicial, int posicaoFinal) {
        if (linha == null || linha.isEmpty()) {
            return "";
        }
        int start = posicaoInicial - 1;
        if (start >= linha.length() || start < 0) {
            return "";
        }
        int end = Math.min(posicaoFinal, linha.length());
        if (start >= end) {
            return "";
        }
        return linha.substring(start, end);
    }

    /**
     * Retorna null caso o valor extraído seja vazio/branco - útil para campos opcionais
     * (ex: cpfCnpjLocador em Veiculos, que é "Sim/Não" na documentação).
     */
    public static String extractOrNull(String linha, int posicaoInicial, int posicaoFinal) {
        String valor = extract(linha, posicaoInicial, posicaoFinal);
        return valor.isBlank() ? null : valor;
    }
}
