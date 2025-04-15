package com.springfield.cidade.feign;

import com.springfield.cidade.solicitacao.EventoSolicitacao;
import com.springfield.cidade.solicitacao.HistoricoSolicitacao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "solicitacao", url = "http://localhost:8080")
public interface SolicitacaoFeignClient {

    @GetMapping("/solicitacoes/cidadao/{cidadaoId}")
    ResponseEntity<HistoricoSolicitacao[]> listarHistorico(@PathVariable Integer cidadaoId);

    @PostMapping("/solicitacoes")
    ResponseEntity<HistoricoSolicitacao> novaSolicitacao(
            @RequestParam Integer cidadaoId,
            @RequestParam String descricao);

    @PostMapping("/solicitacoes/{id}/transicao")
    ResponseEntity<HistoricoSolicitacao> transitarEstado(
            @PathVariable Integer id,
            @RequestParam EventoSolicitacao evento);
}
