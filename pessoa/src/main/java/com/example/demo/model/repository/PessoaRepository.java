package com.example.demo.model.repository;

import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
}
