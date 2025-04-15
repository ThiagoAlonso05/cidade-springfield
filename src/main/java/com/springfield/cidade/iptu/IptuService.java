package com.springfield.cidade.iptu;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IptuService {

    private final ParcelaIptuRepository repository;

    public IptuService(ParcelaIptuRepository repository) {
        this.repository = repository;
    }

    public void gerarParcelas(Integer cidadaoId, boolean pagamentoUnico) {
        int anoAtual = LocalDate.now().getYear();
        List<ParcelaIptu> parcelas = new ArrayList<>();

        if (pagamentoUnico) {
            for (int mes = 1; mes <= 12; mes++) {
                BigDecimal valor = (mes == 1) ? new BigDecimal("1000.00") : BigDecimal.ZERO;
                parcelas.add(new ParcelaIptu(null, cidadaoId, mes, anoAtual, valor, false, null));
            }
        } else {
            for (int mes = 1; mes <= 12; mes++) {
                parcelas.add(new ParcelaIptu(null, cidadaoId, mes, anoAtual, new BigDecimal("1000.00"), false, null));
            }
        }

        repository.saveAll(parcelas);
    }

    public List<ParcelaIptu> listarParcelas(Integer cidadaoId) {
        return repository.findByCidadaoId(cidadaoId);
    }

    public void pagarParcela(Integer parcelaId) {
        ParcelaIptu parcela = repository.findById(parcelaId)
            .orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        parcela.setPaga(true);
        parcela.setDataPagamento(LocalDate.now());
        repository.save(parcela);
    }

    public BigDecimal calcularTotalPago(Integer cidadaoId) {
        return repository.findByCidadaoId(cidadaoId).stream()
            .filter(ParcelaIptu::isPaga)
            .map(ParcelaIptu::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalDevido(Integer cidadaoId) {
        return repository.findByCidadaoId(cidadaoId).stream()
            .filter(p -> !p.isPaga())
            .map(ParcelaIptu::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
