package com.springfield.cidade.controller;

//pega o que criei e referencia aqui
import com.springfield.cidade.cidadao.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cidadaos")
//cria a classe CidadaoController
public class CidadaoController {

    private final CidadaoRepository cidadaoRepository;

    public CidadaoController(CidadaoRepository cidadaoRepository) {
        this.cidadaoRepository = cidadaoRepository;
    }

    //cria o método getAllCidadaos que retorna uma lista
    @GetMapping
    public List<CidadaoResponse> getAllCidadaos() {
        List<Cidadao> cidadaos = cidadaoRepository.findAll();
        return cidadaos.stream().map(CidadaoResponse::new).collect(Collectors.toList());
    }

    //cria o método getCidadaoById que retorna um unico cidadao
    @GetMapping("/{id}")
    public ResponseEntity<CidadaoResponse> getCidadaoById(@PathVariable Integer id) {
        Optional<Cidadao> cidadao = cidadaoRepository.findById(id);
        return cidadao.map(value -> ResponseEntity.ok(new CidadaoResponse(value)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //cria e já retorna o cidadão criado
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<CidadaoResponse> criarCidadao(@RequestBody CidadaoRequest data) {
        //cria com os parametros
        Cidadao Cidadao = new Cidadao(data.nome(), data.endereco(), data.bairro());

        //salva o que foi criado
        cidadaoRepository.save(Cidadao);

        // já retorna o cidadão
        return ResponseEntity.ok(new CidadaoResponse(Cidadao));
    }    

    //cr
    @PutMapping("/{id}")
    public ResponseEntity<CidadaoResponse> updateCidadao(@PathVariable Integer id, @RequestBody CidadaoRequest data) {
        Optional<Cidadao> existingCidadao = cidadaoRepository.findById(id);
        if (existingCidadao.isPresent()) {
            Cidadao cidadao = existingCidadao.get();
            cidadao.setNome(data.nome());
            cidadao.setEndereco(data.endereco());
            cidadao.setBairro(data.bairro());
            cidadaoRepository.save(cidadao);
            return ResponseEntity.ok(new CidadaoResponse(cidadao));
        }
        return ResponseEntity.notFound().build();
    }
}
