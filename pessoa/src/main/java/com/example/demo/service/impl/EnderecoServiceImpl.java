package com.example.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.error.EnderecoErro;
import com.example.demo.model.dto.EnderecoDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.model.repository.EnderecoRepository;
import com.example.demo.model.repository.PessoaRepository;
import com.example.demo.service.interfaces.EnderecoService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public Endereco salvar(Endereco endereco) {
        try{
            Endereco enderecoSalvo = repository.save(endereco);
            return enderecoSalvo;
        }catch (RuntimeException r){
            throw new EnderecoErro("Endereco não salvo, motivo: "+r.getMessage());
        }
    }

    @Override
    @Transactional
    public Endereco atualizar(Endereco endereco) {
        try {
            Objects.requireNonNull(endereco.getId());
            Endereco enderecoAtualizado = repository.save(endereco);
            return enderecoAtualizado;
        } catch (Exception e) {
            throw new EnderecoErro("Não foi possivel atualizar o endereco: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deletar(Endereco endereco) {
        try {
            Objects.requireNonNull(endereco.getId());
            repository.delete(endereco);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Endereco> obterPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Endereco converter(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setId(dto.getId());

        endereco.setCidade(dto.getCidade());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumeroResidencia(dto.getNumeroResidencia());

       String cepFormatado = dto.getCep().toString().substring(0,2)+"."
               +dto.getCep().toString().substring(2,5)+"-"
               +dto.getCep().toString().substring(5,8);

       endereco.setCep(cepFormatado);

       Optional<Pessoa> pessoaConvert = pessoaRepository.findById(dto.getPessoa());
       endereco.setPessoa(pessoaConvert.get());
       
        return endereco;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Endereco> buscarEndereco(Endereco endereco) {
        Example example = Example.of(endereco,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

    
}
