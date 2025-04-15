package com.springfield.cidade.iptu;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/iptu")
public class IptuController {

    private final IptuService service;

    public IptuController(IptuService service) {
        this.service = service;
    }

    @PostMapping("/gerar")
    public ResponseEntity<String> gerarParcelas(@RequestParam Integer cidadaoId, @RequestParam boolean pagamentoUnico) {
        service.gerarParcelas(cidadaoId, pagamentoUnico);
        return ResponseEntity.ok("Parcelas geradas com sucesso.");
    }

    @GetMapping("/parcelas/{cidadaoId}")
    public ResponseEntity<List<ParcelaIptu>> listarParcelas(@PathVariable Integer cidadaoId) {
        return ResponseEntity.ok(service.listarParcelas(cidadaoId));
    }

    @PostMapping("/pagar/{parcelaId}")
    public ResponseEntity<String> pagarParcela(@PathVariable Integer parcelaId) {
        service.pagarParcela(parcelaId);
        return ResponseEntity.ok("Parcela paga com sucesso.");
    }

    @GetMapping("/total-pago/{cidadaoId}")
    public ResponseEntity<BigDecimal> totalPago(@PathVariable Integer cidadaoId) {
        return ResponseEntity.ok(service.calcularTotalPago(cidadaoId));
    }

    @GetMapping("/total-devido/{cidadaoId}")
    public ResponseEntity<BigDecimal> totalDevido(@PathVariable Integer cidadaoId) {
        return ResponseEntity.ok(service.calcularTotalDevido(cidadaoId));
    }
}
