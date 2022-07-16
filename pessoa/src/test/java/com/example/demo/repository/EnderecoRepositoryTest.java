package com.example.demo.repository;

import com.example.demo.model.dto.EnderecoDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.repository.EnderecoRepository;
import com.example.demo.service.impl.EnderecoServiceImpl;
import com.example.demo.service.impl.PessoaServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void devePersistirNoBD(){
        Endereco endereco = criarEndereco();

        Endereco enderecoSalvo = repository.save(endereco);

        Assertions.assertThat(enderecoSalvo.getId()).isNotNull();
    }

    @Test
    public void deveDeletarEndereco(){
        Endereco endereco = enderecoPersistido();

        endereco = entityManager.find(Endereco.class, endereco.getId());

        repository.delete(endereco);
        Endereco enderecoInexistente = entityManager.find(Endereco.class, endereco.getId());

        Assertions.assertThat(enderecoInexistente).isNull();
    }

    @Test
    public void existeEnderecoNoBD(){
        Endereco endereco = enderecoPersistido();

        Optional<Endereco> enderecoEncontrado = repository.findById(endereco.getId());

        Assertions.assertThat(enderecoEncontrado.isPresent()).isTrue();
    }

    public static Endereco criarEndereco(){
        EnderecoDTO dto = new EnderecoDTO();
        dto.setCep(40250080);
        dto.setCidade("Salvador");
        dto.setLogradouro("Rua 18 de Outubro, Matatu");
        dto.setNumeroResidencia(187);
        dto.setEnderecoPrincipal("PRINCIPAL");

        EnderecoServiceImpl impl = new EnderecoServiceImpl();
        return impl.converter(dto);
    }

    private Endereco enderecoPersistido(){
        Endereco endereco = criarEndereco();

        entityManager.persist(endereco);

        return endereco;
    }

}
