package com.example.demo.model.repository;

import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
