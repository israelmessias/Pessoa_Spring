package com.example.demo.service;

import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.model.enums.EnderecoPrincipal;
import com.example.demo.model.repository.PessoaRepository;
import com.example.demo.repository.EnderecoRepositoryTest;
import com.example.demo.service.impl.PessoaServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Set;

import static com.example.demo.repository.EnderecoRepositoryTest.criarEndereco;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

    @SpyBean
    private PessoaServiceImpl service;
    @MockBean
    private PessoaRepository repository;

    @Test
    public void deveSalvarPessoa(){
        Pessoa pessoa = criarPessoa();
        Mockito.when(repository.save
                ( Mockito.any( Pessoa.class) ) ).thenReturn(pessoa);

        Pessoa pessoaSalva = service.salvarPessoa(new Pessoa());

        Assertions.assertThat(pessoaSalva).isNotNull();
        Assertions.assertThat(pessoaSalva.getNome()).isEqualTo("Tatiane");
        Assertions.assertThat(pessoaSalva.getDataNascimento()).isEqualTo("1999-08-12");
        Assertions.assertThat(pessoaSalva.getEnderecos()).isEmpty();
    }

    private static Pessoa criarPessoa() {
        PessoaDTO dto = new PessoaDTO();
        dto.setNome("Tatiane");
        dto.setDia(12);
        dto.setMes(8);
        dto.setAno(1999);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(LocalDate.of(dto.getAno(), dto.getMes(), dto.getDia()));
        Set<Endereco> enderecos = Set.of(criarEndereco());
        pessoa.setEnderecos(enderecos);
        return pessoa;
    }
}
