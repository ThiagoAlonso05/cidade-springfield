package com.springfield.cidade.auth;

public record TrocaSenha(
    String usuario,
    String senhaAtual,
    String novaSenha
) {}
