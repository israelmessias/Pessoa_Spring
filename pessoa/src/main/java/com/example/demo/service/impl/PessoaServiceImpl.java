package com.example.demo.service.impl;

import com.example.demo.error.PessoaError;
import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.model.repository.PessoaRepository;
import com.example.demo.service.interfaces.PessoaService;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository repository;
    
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
        }
    }

    @Override
    public Optional<Pessoa> obterPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Pessoa> buscarEndereco(Pessoa pessoa) {
        return null;
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
}
