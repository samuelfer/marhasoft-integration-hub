package br.com.marhasoft.integrationhub.modules.contabilidade;

public enum EntidadeContabilidadeEnum {

    ACOES("acoes"),
    RECEITAS_ORCAMENTARIAS("receitas-orcamentarias"),
    CONTAS_BANCARIAS("contas-bancarias"),
    CONCILIACOES_BANCARIAS("conciliacoes-bancarias"),
    ATUALIZACOES_ORCAMENTARIAS("atualizacoes-orcamentarias");

    private final String endpoint;

    EntidadeContabilidadeEnum(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}