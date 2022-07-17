package com.example.demo.service;

import com.example.demo.error.EnderecoErro;
import com.example.demo.error.PessoaError;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.repository.EnderecoRepositoryTest.criarEndereco;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

    @InjectMocks
    private PessoaServiceImpl service;
    @Mock
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
        Assertions.assertThat(pessoaSalva.getEnderecos()).isNotEmpty();
    }
    @Test
    public void naoDeveSalvar(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(null);

        Mockito.when( service.salvarPessoa(pessoa)).thenThrow(PessoaError.class);

        Mockito.verify(repository, Mockito.never()).save(pessoa);
    }

    @Test
    public void atualizarEndereco(){
        Pessoa pessoa = criarPessoa();
        pessoa.setId(1);

        Mockito.when(repository.save(pessoa) ).thenReturn(pessoa);

        service.atualizarPessoa(pessoa);

        Mockito.verify(repository, Mockito.times(1)).save(pessoa);
    }

    @Test
    public void NaoDeveAtualizar(){
        Pessoa pessoa = criarPessoa();

        PessoaError erro = assertThrows(
                PessoaError.class,
                () -> service.atualizarPessoa(pessoa)
        );

        Mockito.verify(repository, Mockito.never()).save(pessoa);
    }

    @Test
    public void deletarPessoa(){
        Pessoa pessoa = criarPessoa();
        pessoa.setId(1);

        service.deletarPessoa(pessoa);

        Mockito.verify(repository).delete(pessoa);
    }

    @Test
    public void deletarPessoaQueNaoExiste(){
        Pessoa pessoa = criarPessoa();

        PessoaError erro = assertThrows(
                PessoaError.class,
                () -> service.deletarPessoa(pessoa)
        );

        Mockito.verify(repository, Mockito.never()).save(pessoa);
    }

    @Test
    public void obterEnderecoPorId(){
        Integer id = 1;
        Pessoa pessoa = criarPessoa();
        pessoa.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> result = service.obterPorId(id);

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public  void retornarVazioEnderecoPorId(){
        Integer id = 1;
        Pessoa pessoa = criarPessoa();
        pessoa.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Pessoa> result = service.obterPorId(id);

        Assertions.assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void listarEndereco()
    {
        Integer id = 1;
        Pessoa pessoa = criarPessoa();
        pessoa.setId(id);

        List<Pessoa> lista = Arrays.asList(pessoa);
        Mockito.when(repository.findAll(Mockito.any(Example.class)) ).thenReturn(lista);

        //execução
        List<Pessoa> result = service.buscarPessoa(pessoa);

        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .contains(pessoa);

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
