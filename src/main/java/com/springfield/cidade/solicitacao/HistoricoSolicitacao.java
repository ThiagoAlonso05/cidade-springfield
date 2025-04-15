package com.springfield.cidade.solicitacao;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CAD_HISTORICO_SOLICITACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoSolicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cidadao_id", nullable = false)
    private Integer cidadaoId;

    @Column(nullable = false)
    private LocalDateTime dataEstado;

    @Column(nullable = false)
    private String descricaoDemanda;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitacao estado;
}