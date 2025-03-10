package com.springfield.cidade.auth;

public record UsuarioRequest(
    Integer cidadaoId,
    String username,
    String senha
) {}

