package br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaItemPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.receitaorcamentaria.model.ReceitaOrcamentariaRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReceitaOrcamentariaMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public ReceitaOrcamentariaPayload toPayload(ReceitaOrcamentariaBatchRequest request,
                                                IntegrationAction action) {

        ReceitaOrcamentariaPayload payload = ReceitaOrcamentariaPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(receita -> mapItem(receita, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }

    /**
     * Converte a requisição em um item do payload.
     */
    private ReceitaOrcamentariaItemPayload mapItem(
            ReceitaOrcamentariaRequest request,
            IntegrationAction action) {

        return ReceitaOrcamentariaItemPayload.builder()
                .numeroReceita(request.getNumeroReceita())
                .codigoReceitaOrcamentaria(request.getCodigoReceitaOrcamentaria())
                .tipoLancamentoReceita(request.getTipoLancamentoReceita())
                .tipoReceitaLancada(request.getTipoReceitaLancada())
                .codigoFonteRecurso(request.getCodigoFonteRecurso())
                .exercicioFonteRecurso(request.getExercicioFonteRecurso())
                .codigoCO(request.getCodigoCO())
                .valorReceitaOrcamentaria(request.getValorReceitaOrcamentaria())
                .codigoBancoContaBancaria(request.getCodigoBancoContaBancaria())
                .numeroContaBancaria(request.getNumeroContaBancaria())
                .numeroAgenciaContaBancaria(request.getNumeroAgenciaContaBancaria())
                .tipoContaBancaria(request.getTipoContaBancaria())
                .cnpjGerenciaContaBancaria(request.getCnpjGerenciaContaBancaria())
                .action(action)
                .build();
    }

}