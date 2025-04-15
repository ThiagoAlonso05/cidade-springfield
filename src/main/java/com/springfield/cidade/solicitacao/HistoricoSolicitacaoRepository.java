package com.springfield.cidade.solicitacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoSolicitacaoRepository extends JpaRepository<HistoricoSolicitacao, Integer> {
    List<HistoricoSolicitacao> findByCidadaoId(Integer cidadaoId);
}
