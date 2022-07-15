package com.example.demo.model.dto;

import java.util.HashSet;
import java.util.Set;

public class PessoaByIdDTO {
   private Integer id;

   private String nome;

   private Integer dia;

   private Integer mes;

   private Integer ano;

   private Set<Integer> enderecos = new HashSet<>();

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public Integer getDia() {
      return dia;
   }

   public void setDia(Integer dia) {
      this.dia = dia;
   }

   public Integer getMes() {
      return mes;
   }

   public void setMes(Integer mes) {
      this.mes = mes;
   }

   public Integer getAno() {
      return ano;
   }

   public void setAno(Integer ano) {
      this.ano = ano;
   }

   public Set<Integer> getEnderecos() {
      return enderecos;
   }

   public void setEnderecos(Set<Integer> enderecos) {
      this.enderecos = enderecos;
   }
}
