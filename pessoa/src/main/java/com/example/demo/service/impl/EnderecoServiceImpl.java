package com.example.demo.service.impl;

import com.example.demo.error.EnderecoErro;
import com.example.demo.model.dto.EnderecoDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.repository.EnderecoRepository;
import com.example.demo.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    EnderecoRepository repository;

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

//        System.out.println(dto.getCep());
//        String cepFormatado = dto.getCep().toString().substring(0,2)+"."
//                +dto.getCep().toString().substring(2,5)+"-"
//                +dto.getCep().toString().substring(5,8);

//        endereco.setCep(cepFormatado);
        return endereco;
    }
}
