package com.springfield.cidade.cidadao;

//anotações
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

//métodos repetitivos get e set
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;

@Table(name = "CIDADAO")
@Entity(name = "CIDADAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cidadao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String endereco;
    private String bairro;

    public Cidadao(String nome, String endereco, String bairro) {
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
    }

    public Cidadao(CidadaoRequest data) {
        this.nome = data.nome();
        this.endereco = data.endereco();
        this.bairro = data.bairro();
    }
}
