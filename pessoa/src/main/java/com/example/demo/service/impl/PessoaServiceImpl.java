package com.example.demo.service.impl;

import com.example.demo.error.EnderecoErro;
import com.example.demo.error.PessoaError;
import com.example.demo.model.dto.PessoaByIdDTO;
import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.model.repository.EnderecoRepository;
import com.example.demo.model.repository.PessoaRepository;
import com.example.demo.service.interfaces.PessoaService;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        try {
            Pessoa pessoaSalva = repository.save(pessoa);
            return pessoaSalva;
        } catch (Exception e) {
            //TODO: handle exception
          throw new  PessoaError("Não foi possivel salvar pessoa  "+e.getMessage());
        }
    }

    @Override
    public Pessoa atualizarPessoa(Pessoa pessoa) {
        try {
            Objects.requireNonNull(pessoa.getId());
            Pessoa pessoaAtualizada = repository.save(pessoa);
            return pessoaAtualizada;
        } catch (Exception e) {
            //TODO: handle exception
          throw new  PessoaError("Não foi possivel Atuallizar registro de  pessoa  "+e.getMessage());
        }
    }

    @Override
    public void deletarPessoa(Pessoa pessoa) {
        try {
            Objects.requireNonNull(pessoa.getId());
            repository.delete(pessoa);
        } catch (Exception e) {
            //TODO: handle exception
            throw new PessoaError("Não foi possivel achar a pessoa, motivo: "+e.getMessage());
        }
    }

    @Override
    public Optional<Pessoa> obterPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Pessoa> buscarPessoa(Pessoa pessoa) {
        Example example = Example.of(pessoa,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

    @Override
    public Pessoa converter(PessoaDTO dto) {
        // TODO Auto-generated method stub
        Pessoa pessoa = new Pessoa();

        pessoa.setId(dto.getId());
        pessoa.setNome(dto.getNome());
        pessoa.setEnderecos(dto.getEnderecos());

        LocalDate dataConvert  = LocalDate.of(dto.getAno(), dto.getMes(), dto.getDia());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        dataConvert.format(format);
        pessoa.setDataNascimento(dataConvert);

        return pessoa;
    }

    @Override
    public Pessoa converterById(PessoaByIdDTO dto) {
        Pessoa pessoa = new Pessoa();

        pessoa.setId(dto.getId());
        pessoa.setNome(dto.getNome());

        LocalDate dataConvert  = LocalDate.of(dto.getAno(), dto.getMes(), dto.getDia());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataConvert.format(format);
        pessoa.setDataNascimento(dataConvert);

        Set<Endereco> enderecoConvert = new HashSet<>();

        for (Integer endereco : dto.getEnderecos()) {
            Optional<Endereco> findEndereco = enderecoRepository.findById(endereco);
            enderecoConvert.add(findEndereco.get());
        }

        pessoa.setEnderecos(enderecoConvert);

        return pessoa;
    }

    @Override
    public List<Endereco> consultarEnderecos(Integer id) {
        try{
          return   repository.findEnderecos(id);
        }catch (NullPointerException e){
            throw new EnderecoErro("Não foi possivel achar Pessoa para essa pessoa, motivo: " + e.getMessage());
        }
    }
}
