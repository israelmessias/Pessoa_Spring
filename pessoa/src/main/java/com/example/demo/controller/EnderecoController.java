package com.example.demo.controller;

import com.example.demo.model.dto.EnderecoDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.service.impl.EnderecoServiceImpl;
import com.example.demo.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    EnderecoServiceImpl service;

    @PostMapping("/salvar")
    public ResponseEntity salvarEndereco(EnderecoDTO dto){
        Endereco endereco = service.converter(dto);
        try{
            Endereco enderecoSalvo = service.salvar(endereco);
            return new ResponseEntity(enderecoSalvo, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
