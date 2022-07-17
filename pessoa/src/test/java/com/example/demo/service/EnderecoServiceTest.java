package com.example.demo.service;

import com.example.demo.error.EnderecoErro;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.enums.EnderecoPrincipal;
import com.example.demo.model.repository.EnderecoRepository;
import com.example.demo.repository.EnderecoRepositoryTest;
import com.example.demo.service.impl.EnderecoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.repository.EnderecoRepositoryTest.criarEndereco;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoServiceImpl service;

    @Mock
    private EnderecoRepository repository;


    @Test
    public void deveSalvarEndereco(){
        Endereco endereco = criarEndereco();
        Mockito.when(repository.save
                ( Mockito.any( Endereco.class) ) ).thenReturn(endereco);

        Endereco enderecoSalvo = service.salvarEndereco(new Endereco());

        Assertions.assertThat(enderecoSalvo).isNotNull();
        Assertions.assertThat(enderecoSalvo.getCep()).isEqualTo("40.250-080");
        Assertions.assertThat(enderecoSalvo.getCidade()).isEqualTo("Salvador");
        Assertions.assertThat(enderecoSalvo.getLogradouro()).isEqualTo("Rua 18 de Outubro, Matatu");
        Assertions.assertThat(enderecoSalvo.getNumeroResidencia()).isEqualTo(187);
        Assertions.assertThat(enderecoSalvo.getEnderecoPrincipal()).isEqualTo(EnderecoPrincipal.PRINCIPAL);
    }

    @Test
    public void naoDeveSalvar(){
        Endereco endereco = new Endereco();
        endereco.setCidade(null);

        Mockito.when( service.salvarEndereco(endereco)).thenThrow(EnderecoErro.class);

        Mockito.verify(repository, Mockito.never()).save(endereco);


    }

    @Test
    public void atualizarEndereco(){
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(1);
        endereco.setEnderecoPrincipal(EnderecoPrincipal.SECUNDARIO);

        Mockito.when(repository.save(endereco) ).thenReturn(endereco);

        service.atualizarEndereco(endereco);

        Mockito.verify(repository, Mockito.times(1)).save(endereco);
    }

    @Test
    public void NaoDeveAtualizar(){
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();

        EnderecoErro erro = assertThrows(
                EnderecoErro.class,
                () -> service.atualizarEndereco(endereco)
        );

        Mockito.verify(repository, Mockito.never()).save(endereco);
    }

    @Test
    public void deletarEndereco(){
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(1);
        endereco.setEnderecoPrincipal(EnderecoPrincipal.SECUNDARIO);

        service.deletar(endereco);

        Mockito.verify(repository).delete(endereco);
    }

    @Test
    public void deletarEnderecoQueNaoExiste(){
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();

        EnderecoErro erro = assertThrows(
                EnderecoErro.class,
                () -> service.deletar(endereco)
        );

        Mockito.verify(repository, Mockito.never()).save(endereco);
    }

    @Test
    public void obterEnderecoPorId(){
        Integer id = 1;
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(endereco));

        Optional<Endereco> result = service.obterPorId(id);

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public  void retornarVazioEnderecoPorId(){
        Integer id = 1;
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Endereco> result = service.obterPorId(id);

        Assertions.assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void filtrarEndereco()
    {
        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(1);

        List<Endereco> lista = Arrays.asList(endereco);
        Mockito.when(repository.findAll(Mockito.any(Example.class)) ).thenReturn(lista);

        //execução
        List<Endereco> result = service.buscarEndereco(endereco);

        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .contains(endereco);

    }



}
