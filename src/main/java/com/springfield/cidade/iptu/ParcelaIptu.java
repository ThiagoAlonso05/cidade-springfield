package com.springfield.cidade.iptu;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CAD_PARCELA_IPTU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaIptu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cidadao_id", nullable = false)
    private Integer cidadaoId;

    @Column(nullable = false)
    private int mes;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private boolean paga;

    private LocalDate dataPagamento;
}
