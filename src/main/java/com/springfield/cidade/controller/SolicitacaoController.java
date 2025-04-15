package com.springfield.cidade.controller;

import com.springfield.cidade.solicitacao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin(origins = "*")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    // Criar nova solicitação
    @PostMapping
    public ResponseEntity<HistoricoSolicitacao> novaSolicitacao(
            @RequestParam Integer cidadaoId,
            @RequestParam String descricao) {
        HistoricoSolicitacao historico = solicitacaoService.criarSolicitacao(cidadaoId, descricao);
        return ResponseEntity.ok(historico);
    }

    // Transitar de estado (ex: ANALISAR ou CONCLUIR)
    @PostMapping("/{id}/transicao")
    public ResponseEntity<HistoricoSolicitacao> transitarEstado(
            @PathVariable Integer id,
            @RequestParam EventoSolicitacao evento) {
        HistoricoSolicitacao atualizado = solicitacaoService.transitarEstado(id, evento);
        return ResponseEntity.ok(atualizado);
    }

    // Listar histórico por cidadão
    @GetMapping("/cidadao/{cidadaoId}")
    public ResponseEntity<List<HistoricoSolicitacao>> listarPorCidadao(
            @PathVariable Integer cidadaoId) {
        List<HistoricoSolicitacao> historico = solicitacaoService.listarHistoricoPorCidadao(cidadaoId);
        return ResponseEntity.ok(historico);
    }
}
