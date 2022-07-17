package com.example.demo.controller;

import com.example.demo.model.dto.PessoaByIdDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.service.impl.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.service.impl.PessoaServiceImpl;
import com.example.demo.service.interfaces.PessoaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaServiceImpl service;

    @Autowired
    private EnderecoServiceImpl enderecoService;

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

    @GetMapping("enderecos/{id}")
    public ResponseEntity listarEnderecosPessoa(@PathVariable Integer id){
        try {
            List<Endereco> enderecos = service.consultarEnderecos(id);
            return ResponseEntity.ok(enderecos);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity buscar(
            @RequestParam(value = "nome", required = false) String cep,
            @RequestParam(value = "logradouro", required = false) String logradouro,
            @RequestParam(value = "numeroResisdencia", required = false) Integer numeroResisdencia,
            @RequestParam(value = "numeroResisdencia", required = false) String cidade,
            @RequestParam(value = "cidade", required = false) String nome,
            @RequestParam(value = "cidade", required = false) LocalDate dataNascimento

    )
    {
        Endereco enderecoFiltro = new Endereco();
        enderecoFiltro.setCep(cep);
        enderecoFiltro.setCidade(cidade);
        enderecoFiltro.setLogradouro(logradouro);
        enderecoFiltro.setNumeroResidencia(numeroResisdencia);

        List<Endereco> enderecos = enderecoService.buscarEndereco(enderecoFiltro);

        Pessoa pessoaFiltro = new Pessoa();
        pessoaFiltro.setNome(nome);
        pessoaFiltro.setDataNascimento(dataNascimento);
        pessoaFiltro.setEnderecos(Set.copyOf(enderecos));

        List<Pessoa>pessoas = service.buscarPessoa(pessoaFiltro);
        return ResponseEntity.ok(pessoas);
    }

    
}
