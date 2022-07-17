package com.example.demo.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

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
    private LocalDate dataNascimento;

//     @JsonBackReference
    @OneToMany(cascade={
        CascadeType.PERSIST,
        CascadeType.MERGE}, 
        targetEntity = Endereco.class)
    @JoinTable(name = "pessoa_endereco", 
               joinColumns=@JoinColumn(name="id_pessoa")
              ,inverseJoinColumns =@JoinColumn(name = "id_endereco")
    )
    private Set<Endereco> enderecos;

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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    

}
