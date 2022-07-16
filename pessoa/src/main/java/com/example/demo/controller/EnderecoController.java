package com.example.demo.controller;

import com.example.demo.model.dto.EnderecoDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.service.impl.EnderecoServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    EnderecoServiceImpl service;

    @PostMapping("/salvar")
    public ResponseEntity salvarEndereco(@RequestBody EnderecoDTO dto){
        Endereco endereco = service.converter(dto);
        try{
            Endereco enderecoSalvo = service.salvarEndereco(endereco);
            return new ResponseEntity(enderecoSalvo, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/atualizar/{id}")
    public ResponseEntity atualizarEndereco(@PathVariable("id") Integer id, @RequestBody EnderecoDTO dto) {
    	return (ResponseEntity) service.obterPorId(id).map(entity ->
        {
            try {
                Endereco endereco = service.converter(dto);
                endereco.setId(entity.getId());
                service.atualizarEndereco(endereco);
                return new ResponseEntity(endereco, HttpStatus.CREATED);
            } catch (Exception e) {
                //TODO: handle exception

                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Não encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletarEndereco(@PathVariable Integer id){
        return service.obterPorId(id).map(entity ->
        {
            service.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(
                () -> new ResponseEntity("Usuario nao encontrado na base de dados",
                        HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/buscar")
    public ResponseEntity buscar(
            @RequestParam(value = "cep", required = false) String cep,
            @RequestParam(value = "logradouro", required = false) String logradouro,
            @RequestParam(value = "numeroResisdencia", required = false) Integer numeroResisdencia,
            @RequestParam(value = "cidade", required = false) String cidade
    )
    {
        Endereco enderecoFiltro = new Endereco();
        enderecoFiltro.setCep(cep);
        enderecoFiltro.setCidade(cidade);
        enderecoFiltro.setLogradouro(logradouro);
        enderecoFiltro.setNumeroResidencia(numeroResisdencia);

        List<Endereco> endereco = service.buscarEndereco(enderecoFiltro);
      return ResponseEntity.ok(endereco);
    }
}
