package com.springfield.cidade.feign;

import com.springfield.cidade.iptu.ParcelaIptu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IptuFeignTest implements CommandLineRunner {

    @Autowired
    private IptuFeignClient iptuFeign;

    @Override
    public void run(String... args) {
        int cidadaoId = 10000;

        System.out.println("✅ Gerando parcelas para cidadão " + cidadaoId);
        iptuFeign.gerarParcelas(cidadaoId, false); // ou true para pagamento único

        System.out.println("📜 Consultando parcelas...");
        List<ParcelaIptu> parcelas = iptuFeign.consultarParcelas(cidadaoId).getBody();
        parcelas.forEach(p -> System.out.printf("🧾 Parcela %d | Mês %d | Valor %.2f | Pago: %s\n",
                p.getId(), p.getMes(), p.getValor(), p.isPaga()));

        if (!parcelas.isEmpty()) {
            System.out.println("💸 Baixando a primeira parcela...");
            iptuFeign.baixarParcela(parcelas.get(0).getId());
        }

        System.out.println("📊 Consultando resumo...");
        String resumo = iptuFeign.consultarResumo(cidadaoId).getBody();
        System.out.println("➡️ Resumo: " + resumo);
    }
}
