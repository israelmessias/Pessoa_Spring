package com.example.demo.controller;

import com.example.demo.model.dto.PessoaByIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.service.impl.PessoaServiceImpl;
import com.example.demo.service.interfaces.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaServiceImpl service;

    @PostMapping("/salvar")
    public ResponseEntity salvarPessoa(@RequestBody PessoaDTO dto){
        try {
            Pessoa pessoa = service.converter(dto);
            Pessoa pessoaSalva = service.salvarPessoa(pessoa);
            return new ResponseEntity(pessoaSalva, HttpStatus.CREATED);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @PostMapping("/salvar/new")
    public ResponseEntity salvarPessoa(@RequestBody PessoaByIdDTO dto){
        try {
            Pessoa pessoa = service.converterById(dto);
            Pessoa pessoaSalva = service.salvarPessoa(pessoa);
            return new ResponseEntity(pessoaSalva, HttpStatus.CREATED);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping(path = "/atualizar/{id}")
    public ResponseEntity atualizarEndereco(@PathVariable("id") Integer id, @RequestBody PessoaDTO dto) {
    	return (ResponseEntity) service.obterPorId(id).map(entity ->
        {
            try {
                Pessoa pessoa = service.converter(dto);
                pessoa.setId(entity.getId());
                service.atualizarPessoa(pessoa);
                return new ResponseEntity(pessoa, HttpStatus.CREATED);
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
            service.deletarPessoa(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(
                () -> new ResponseEntity("Usuario nao encontrado na base de dados",
                        HttpStatus.BAD_REQUEST));
    }

    
}
