package com.example.demo.service.impl;

import com.example.demo.model.entity.Pessoa;
import com.example.demo.service.interfaces.PessoaService;

import java.util.List;
import java.util.Optional;

public class PessoaServiceImpl implements PessoaService {
    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        return null;
    }

    @Override
    public Pessoa atualizarPessoa(Pessoa pessoa) {
        return null;
    }

    @Override
    public void deletarPessoa(Pessoa pessoa) {

    }

    @Override
    public Optional<Pessoa> obterPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Pessoa> buscarEndereco(Pessoa pessoa) {
        return null;
    }
}
