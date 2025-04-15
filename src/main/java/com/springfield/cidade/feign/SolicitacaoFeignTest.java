package com.springfield.cidade.feign;

import com.springfield.cidade.solicitacao.EventoSolicitacao;
import com.springfield.cidade.solicitacao.HistoricoSolicitacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoFeignTest implements CommandLineRunner {

    @Autowired
    private SolicitacaoFeignClient solicitacaoFeign;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔎 Testando operações com Feign...");

        // Criar nova solicitação
        HistoricoSolicitacao nova = solicitacaoFeign
                .novaSolicitacao(10000, "Solicitação via Feign Teste")
                .getBody();
        System.out.println("🆕 Solicitação criada: " + nova.getId() + " - Estado: " + nova.getEstado());

        // Transição de estado: ANALISAR
        HistoricoSolicitacao analisada = solicitacaoFeign
                .transitarEstado(nova.getId(), EventoSolicitacao.ANALISAR)
                .getBody();
        System.out.println("✅ Após ANALISAR: Estado = " + analisada.getEstado());

        // Transição de estado: CONCLUIR
        HistoricoSolicitacao concluida = solicitacaoFeign
                .transitarEstado(nova.getId(), EventoSolicitacao.CONCLUIR)
                .getBody();
        System.out.println("🏁 Após CONCLUIR: Estado = " + concluida.getEstado());

        // Consultar histórico
        HistoricoSolicitacao[] historico = solicitacaoFeign.listarHistorico(10000).getBody();
        System.out.println("📜 Histórico do cidadão 10000:");
        for (HistoricoSolicitacao h : historico) {
            System.out.println("➡️ " + h.getId() + ": " + h.getEstado() + " - " + h.getDescricaoDemanda());
        }
    }
}
