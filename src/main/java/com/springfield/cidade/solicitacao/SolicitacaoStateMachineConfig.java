package com.springfield.cidade.solicitacao;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class SolicitacaoStateMachineConfig extends EnumStateMachineConfigurerAdapter<EstadoSolicitacao, EventoSolicitacao> {

    @Override
    public void configure(StateMachineStateConfigurer<EstadoSolicitacao, EventoSolicitacao> states) throws Exception {
        states
            .withStates()
            .initial(EstadoSolicitacao.SOLICITADO)
            .state(EstadoSolicitacao.AGUARDANDO_ANALISE)
            .end(EstadoSolicitacao.CONCLUIDO);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EstadoSolicitacao, EventoSolicitacao> transitions) throws Exception {
        transitions
            .withExternal()
                .source(EstadoSolicitacao.SOLICITADO).target(EstadoSolicitacao.AGUARDANDO_ANALISE).event(EventoSolicitacao.ANALISAR)
            .and()
            .withExternal()
                .source(EstadoSolicitacao.AGUARDANDO_ANALISE).target(EstadoSolicitacao.CONCLUIDO).event(EventoSolicitacao.CONCLUIR);
    }
}