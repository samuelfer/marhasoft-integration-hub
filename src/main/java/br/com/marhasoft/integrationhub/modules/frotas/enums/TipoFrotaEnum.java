package br.com.marhasoft.integrationhub.modules.frotas.enums;

import java.util.Arrays;

public enum TipoFrotaEnum {

    PROPRIO("1", "Próprio"),
    LOCADO("2", "Locado"),
    PRESTACAO_SERVICOS("3", "Prestação de Serviços"),
    CEDIDO("4", "Cedido");

    private final String codigo;
    private final String descricao;

    TipoFrotaEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static boolean isValido(String codigo) {
        return Arrays.stream(values())
                .anyMatch(tipo -> tipo.codigo.equals(codigo));
    }
}