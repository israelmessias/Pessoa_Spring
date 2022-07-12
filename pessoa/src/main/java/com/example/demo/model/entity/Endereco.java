package com.example.demo.model.entity;

import javax.persistence.*;

@Entity
@Table
public class Endereco {
    @SequenceGenerator(name = "Endereco", sequenceName = "enderco_seq", initialValue = 1, allocationSize = 1)
    @Id
    @GeneratedValue(generator = "Endereco")
    @Column(name="id_endereco", unique = true, nullable = false)
    private Integer id;

    @Column
    private String cep;

    private String logradouro;

    @Column
    private Integer numeroResidencia;

    @Column
    private String cidade;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(Integer numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
