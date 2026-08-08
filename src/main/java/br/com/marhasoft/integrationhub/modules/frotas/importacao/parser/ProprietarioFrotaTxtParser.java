package br.com.marhasoft.integrationhub.modules.frotas.importacao.parser;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.DocumentoUtils;
import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.FixedWidthUtils;
import br.com.marhasoft.integrationhub.modules.frotas.proprietario.model.ProprietarioRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser da tabela 4.50 - ProprietarioFrota.
 *
 * Layout:
 * codUnidadeGestora  1-6
 * cpfcnpj            7-20
 * nome               21-100
 */
public final class ProprietarioFrotaTxtParser {

    private ProprietarioFrotaTxtParser() {
    }

    public static ProprietarioRequest parseLinha(String linha) {
        String cpfCnpj = DocumentoUtils.normalizarCpfCnpj(FixedWidthUtils.extract(linha, 7, 20));
        String nome = FixedWidthUtils.extract(linha, 21, 100);

        return ProprietarioRequest.builder()
                .cpfCnpj(cpfCnpj)
                .nome(nome)
                .build();
    }

    /**
     * Lê o arquivo inteiro (ex: PROPRIETARIOFROTA.txt) e retorna a lista de proprietários.
     */
    public static List<ProprietarioRequest> parseArquivo(Path caminho) throws IOException {
        List<ProprietarioRequest> resultado = new ArrayList<>();
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
