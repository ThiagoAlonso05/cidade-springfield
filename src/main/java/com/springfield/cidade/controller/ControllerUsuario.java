package com.springfield.cidade.controller;

import com.springfield.cidade.cidadao.*;
import com.springfield.cidade.auth.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ControllerUsuario {

    private final UsuarioRepository repositoryUsuario;
    private final CidadaoRepository repositoryCidadao;

    public ControllerUsuario(UsuarioRepository repositoryUsuario, CidadaoRepository repositoryCidadao) {
        this.repositoryUsuario = repositoryUsuario;
        this.repositoryCidadao = repositoryCidadao;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest request) {
        Optional<Cidadao> cidadaoOpt = repositoryCidadao.findById(request.cidadaoId());

        if (cidadaoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Cidadão não localizado.");
        }

        if (repositoryUsuario.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Nome de usuário já utilizado.");
        }

        Usuario novoUsuario = new Usuario(null, cidadaoOpt.get(), request.username(), request.senha(), false, 0,
                LocalDateTime.now());
        repositoryUsuario.save(novoUsuario);

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    // Método para efetuar o login de um usuário
    // O método recebe um objeto Login como parâmetro
    @PostMapping("/entrar")
    public ResponseEntity<String> entrar(@RequestBody Login request) {
        Optional<Usuario> usuarioOpt = repositoryUsuario.findByUsername(request.usuario());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não localizado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.isBloqueado()) {
            return ResponseEntity.status(403).body("Usuário bloqueado, solicite o desbloqueio.");
        }

        if (!usuario.getSenha().equals(request.senha())) {
            usuario.setTentativasLogin(usuario.getTentativasLogin() + 1);

            if (usuario.getTentativasLogin() >= 3) {
                usuario.setBloqueado(true);
            }

            repositoryUsuario.save(usuario);
            return ResponseEntity.status(403)
                    .body("Senha incorreta. Tentativas restantes: " + (3 - usuario.getTentativasLogin()));
        }

        usuario.setTentativasLogin(0);
        usuario.setUltimaTrocaSenha(LocalDateTime.now());
        repositoryUsuario.save(usuario);

        return ResponseEntity.ok("Login efetuado com sucesso!");
    }

    // Método para liberar um usuário bloqueado
    // O método recebe o nome de usuário como parâmetro
    @PostMapping("/liberar/{username}")
    public ResponseEntity<String> liberarUsuario(@PathVariable String username) {
        Optional<Usuario> usuarioOpt = repositoryUsuario.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não localizado.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setBloqueado(false);
        usuario.setTentativasLogin(0);
        repositoryUsuario.save(usuario);

        return ResponseEntity.ok("Usuário liberado com sucesso!");
    }

    // Método para alterar a senha de um usuário
    // O método recebe um objeto TrocaSenha como parâmetro
    @PostMapping("/alterar-senha")
    public ResponseEntity<String> alterarSenha(@RequestBody TrocaSenha request) {
        Optional<Usuario> usuarioOpt = repositoryUsuario.findByUsername(request.usuario());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não localizado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSenha().equals(request.senhaAtual())) {
            return ResponseEntity.status(403).body("Senha atual inválida.");
        }

        usuario.setSenha(request.novaSenha());
        usuario.setUltimaTrocaSenha(LocalDateTime.now());
        repositoryUsuario.save(usuario);

        return ResponseEntity.ok("Senha modificada com sucesso!");
    }
}
