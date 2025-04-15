// src/main/java/com/springfield/cidade/solicitacao/SolicitacaoService.java
package com.springfield.cidade.solicitacao;

import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {

    @Autowired
    private HistoricoSolicitacaoRepository historicoRepository;

    @Autowired
    private StateMachineFactory<EstadoSolicitacao, EventoSolicitacao> stateMachineFactory;

    public HistoricoSolicitacao criarSolicitacao(Integer cidadaoId, String descricaoDemanda) {
        StateMachine<EstadoSolicitacao, EventoSolicitacao> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.start();

        HistoricoSolicitacao historico = new HistoricoSolicitacao();
        historico.setCidadaoId(cidadaoId);
        historico.setDataEstado(LocalDateTime.now());
        historico.setDescricaoDemanda(descricaoDemanda);
        historico.setEstado(stateMachine.getState().getId());

        return historicoRepository.save(historico);
    }

    public HistoricoSolicitacao transitarEstado(Integer historicoId, EventoSolicitacao evento) {
        Optional<HistoricoSolicitacao> optionalHistorico = historicoRepository.findById(historicoId);
        if (optionalHistorico.isEmpty()) {
            throw new RuntimeException("Solicitação não encontrada");
        }

        HistoricoSolicitacao historico = optionalHistorico.get();

        StateMachine<EstadoSolicitacao, EventoSolicitacao> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
            .doWithAllRegions(access -> access.resetStateMachine(
                new org.springframework.statemachine.support.DefaultStateMachineContext<>(
                    historico.getEstado(), null, null, null
                )
            ));
        stateMachine.start();

        boolean transicaoValida = stateMachine.sendEvent(MessageBuilder.withPayload(evento).build());
        if (transicaoValida) {
            historico.setEstado(stateMachine.getState().getId());
            historico.setDataEstado(LocalDateTime.now());
            return historicoRepository.save(historico);
        } else {
            throw new RuntimeException("Transição de estado inválida");
        }
    }

    public List<HistoricoSolicitacao> listarHistoricoPorCidadao(Integer cidadaoId) {
        return historicoRepository.findByCidadaoId(cidadaoId);
    }
}
