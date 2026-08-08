package br.com.marhasoft.integrationhub.modules.frotas.importacao.parser;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.DocumentoUtils;
import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.FixedWidthUtils;
import br.com.marhasoft.integrationhub.modules.frotas.locador.model.LocadorRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser da tabela 4.51 - LocadorPrestador.
 *
 * Layout:
 * codUnidadeGestora  1-6
 * cpfcnpj            7-20
 * nome               21-100
 */
public final class LocadorPrestadorTxtParser {

    private LocadorPrestadorTxtParser() {
    }

    public static LocadorRequest parseLinha(String linha) {
        String cpfCnpj = DocumentoUtils.normalizarCpfCnpj(FixedWidthUtils.extract(linha, 7, 20));
        String nome = FixedWidthUtils.extract(linha, 21, 100);

        return LocadorRequest.builder()
                .cpfCnpj(cpfCnpj)
                .nome(nome)
                .build();
    }

    /**
     * Lê o arquivo inteiro (ex: LOCADORPRESTADOR.txt) e retorna a lista de locadores/prestadores.
     */
    public static List<LocadorRequest> parseArquivo(Path caminho) throws IOException {
        List<LocadorRequest> resultado = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(caminho, StandardCharsets.ISO_8859_1)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) {
                    continue;
                }
                resultado.add(parseLinha(linha));
            }
        }
        return resultado;
    }
}
