package com.example.demo.model.dto;

public class EnderecoDTO {

    private Integer id;
    private Integer cep;

    private String logradouro;

    private Integer numeroResidencia;

    private String cidade;

    private Integer pessoa;

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
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

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    
}
