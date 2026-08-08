package br.com.marhasoft.integrationhub.modules.frotas.importacao.parser;

import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.DocumentoUtils;
import br.com.marhasoft.integrationhub.modules.frotas.importacao.util.FixedWidthUtils;
import br.com.marhasoft.integrationhub.modules.frotas.maquina.model.MaquinaRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser da tabela 4.53 - Maquinas.
 *
 * Layout:
 * codUnidadeGestora    1-6
 * codigo               7-13
 * anofabricacao        14-17
 * descricao            18-67
 * tipo_maquina         68-68
 * cpfcnpjProprietario  69-82
 * cpfcnpjLocador       83-96 (opcional)
 */
public final class MaquinaTxtParser {

    private MaquinaTxtParser() {
    }

    public static MaquinaRequest parseLinha(String linha) {
        String codigo = FixedWidthUtils.extract(linha, 7, 13);
        String anoFabricacao = FixedWidthUtils.extract(linha, 14, 17);
        String descricao = FixedWidthUtils.extract(linha, 18, 67);
        String tipoFrota = FixedWidthUtils.extract(linha, 68, 68);

        String cpfCnpjProprietario = DocumentoUtils.normalizarCpfCnpj(
                FixedWidthUtils.extract(linha, 69, 82));

        String cpfCnpjLocadorBruto = FixedWidthUtils.extractOrNull(linha, 83, 96);
        String cpfCnpjLocador = cpfCnpjLocadorBruto == null
                ? null
                : DocumentoUtils.normalizarCpfCnpj(cpfCnpjLocadorBruto);

        return MaquinaRequest.builder()
                .codigo(codigo)
                .anoFabricacao(anoFabricacao)
                .descricao(descricao)
                .tipoFrota(tipoFrota)
                .cpfCnpjProprietario(cpfCnpjProprietario)
                .cpfCnpjLocador(cpfCnpjLocador)
                .build();
    }

    /**
     * Lê o arquivo MAQUINAS.txt inteiro e retorna a lista de máquinas.
     */
    public static List<MaquinaRequest> parseArquivo(Path caminho) throws IOException {
        List<MaquinaRequest> resultado = new ArrayList<>();
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
