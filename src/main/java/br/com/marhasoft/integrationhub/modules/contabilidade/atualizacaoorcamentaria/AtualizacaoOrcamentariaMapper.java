package br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria;

import br.com.marhasoft.integrationhub.core.model.IntegrationAction;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaBatchRequest;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaItemPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaPayload;
import br.com.marhasoft.integrationhub.modules.contabilidade.atualizacaoorcamentaria.model.AtualizacaoOrcamentariaRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AtualizacaoOrcamentariaMapper {

    /**
     * Converte a requisição recebida pela API para o payload esperado pelo TCE.
     *
     * @param request requisição recebida pela API.
     * @param action operação da integração.
     * @return payload pronto para envio ao TCE.
     */
    public AtualizacaoOrcamentariaPayload toPayload(AtualizacaoOrcamentariaBatchRequest request,
                                                    IntegrationAction action) {

        AtualizacaoOrcamentariaPayload payload = AtualizacaoOrcamentariaPayload.builder()
                .timestamp(LocalDateTime.now())
                .build();

        request.getElementos()
                .stream()
                .map(atualizacao -> mapItem(atualizacao, action))
                .forEach(payload.getElementos()::add);

        return payload;
    }
    /**
     * Converte a requisição em um item do payload.
     */
    private AtualizacaoOrcamentariaItemPayload mapItem(
            AtualizacaoOrcamentariaRequest request,
            IntegrationAction action) {

        return AtualizacaoOrcamentariaItemPayload.builder()
                .codigoUnidadeGestora(request.getCodigoUnidadeGestora())
                .codigoUnidadeOrcamentaria(request.getCodigoUnidadeOrcamentaria())
                .codigoFuncao(request.getCodigoFuncao())
                .codigoSubfuncao(request.getCodigoSubfuncao())
                .codigoPrograma(request.getCodigoPrograma())
                .codigoAcao(request.getCodigoAcao())
                .codigoCategoriaEconomica(request.getCodigoCategoriaEconomica())
                .codigoNaturezaDespesa(request.getCodigoNaturezaDespesa())
                .codigoModalidadeDespesa(request.getCodigoModalidadeDespesa())
                .codigoElementoDespesa(request.getCodigoElementoDespesa())
                .codigoFonteRecurso(request.getCodigoFonteRecurso())
                .exercicioFonteRecurso(request.getExercicioFonteRecurso())
                .numeroDecretoOficio(request.getNumeroDecretoOficio())
                .tipoDecretoOficio(request.getTipoDecretoOficio())
                .tipoAlteracao(request.getTipoAlteracao())
                .valorAtualizacao(request.getValorAtualizacao())
                .action(action)
                .build();
    }

}