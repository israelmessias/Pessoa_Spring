package com.example.demo.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Pessoa {
    @SequenceGenerator(name = "Pessoa", sequenceName = "pessoa_seq", initialValue = 1, allocationSize = 1)
    @Id
    @GeneratedValue(generator = "Pessoa")
    @Column(name="id_pessoa", unique = true, nullable = false)
    private Integer id;

    @Column
    private String nome;

    @Column
    private Date dataNascimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Endereco endereco;

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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


}
