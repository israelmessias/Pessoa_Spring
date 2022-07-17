package com.example.demo.service.interfaces;

import com.example.demo.model.dto.PessoaByIdDTO;
import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    Pessoa salvarPessoa(Pessoa pessoa);

    Pessoa atualizarPessoa(Pessoa pessoa);

    void deletarPessoa(Pessoa pessoa);

    Optional<Pessoa> obterPorId(Integer id);

    List<Pessoa> buscarPessoa(Pessoa pessoa);

    Pessoa converter(PessoaDTO dto);

    Pessoa converterById(PessoaByIdDTO dto);

    List<Endereco> consultarEnderecos(Integer id);
}
