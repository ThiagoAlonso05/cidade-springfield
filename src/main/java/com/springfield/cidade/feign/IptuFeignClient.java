package com.springfield.cidade.feign;

import com.springfield.cidade.iptu.ParcelaIptu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "iptu", url = "http://localhost:8080")
public interface IptuFeignClient {

    @PostMapping("/iptu/gerar")
    ResponseEntity<String> gerarParcelas(
            @RequestParam Integer cidadaoId,
            @RequestParam boolean pagamentoUnico);

    @PostMapping("/iptu/baixa/{idParcela}")
    ResponseEntity<String> baixarParcela(@PathVariable Integer idParcela);

    @GetMapping("/iptu/consulta/{cidadaoId}")
    ResponseEntity<List<ParcelaIptu>> consultarParcelas(@PathVariable Integer cidadaoId);

    @GetMapping("/iptu/resumo/{cidadaoId}")
    ResponseEntity<String> consultarResumo(@PathVariable Integer cidadaoId);
}
