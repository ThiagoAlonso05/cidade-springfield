package com.springfield.cidade.cidadao;

public record CidadaoResponse(
    Integer id,
    String nome,
    String endereco,
    String bairro
){
    public CidadaoResponse(Cidadao cidadao){
        this(
        cidadao.getId(), //1
        cidadao.getNome(), //Thiago
        cidadao.getEndereco(), //Rua tucunduva
        cidadao.getBairro() //Frega
        );
    }
}
