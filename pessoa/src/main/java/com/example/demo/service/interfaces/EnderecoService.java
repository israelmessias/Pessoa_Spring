package com.example.demo.service.interfaces;

import com.example.demo.model.dto.EnderecoDTO;
import com.example.demo.model.entity.Endereco;

import java.util.Optional;

public interface EnderecoService {
    Endereco salvar(Endereco endereco);

    Endereco atualizar(Endereco endereco);

    void deletar(Endereco endereco);

    Optional<Endereco> obterPorId(Integer id);

    Endereco converter(EnderecoDTO dto);

}
