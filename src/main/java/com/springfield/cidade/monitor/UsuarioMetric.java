package com.springfield.cidade.monitor;

import com.springfield.cidade.auth.UsuarioRepository;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMetric {

    private final UsuarioRepository usuarioRepository;
    private final MeterRegistry meterRegistry;

    public UsuarioMetric(UsuarioRepository usuarioRepository, MeterRegistry meterRegistry) {
        this.usuarioRepository = usuarioRepository;
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registrarMetricas() {
        meterRegistry.gauge("usuarios_total_cadastrados", usuarioRepository, repo -> repo.count());
    }
}
