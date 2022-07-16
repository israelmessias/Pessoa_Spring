package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.dto.PessoaByIdDTO;
import com.example.demo.model.dto.PessoaDTO;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Pessoa;
import com.example.demo.model.repository.EnderecoRepository;
import com.example.demo.model.repository.PessoaRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PessoaRespositoryTest {
	@Autowired
	private PessoaRepository repository;
	@Autowired
	private  TestEntityManager entityManager;
	
	@Test
	public void devePersistirPessoaNoBD() {
		Pessoa pessoa = criarPessoa();
		
		Pessoa pessoaSalva = repository.save(pessoa);
		
		Assertions.assertThat(pessoaSalva.getId()).isNotNull();	
	}

	@Test
	public void existePessoaNoBD(){
		Pessoa pessoa = pessoaPersitida();

		Optional<Pessoa> pessoaEncontrada = repository.findById(pessoa.getId());

		Assertions.assertThat(pessoaEncontrada.isPresent()).isTrue();
	}

	@Test
	public void deveDeletarPessoa(){
		Pessoa pessoa = pessoaPersitida();

		pessoa = entityManager.find(Pessoa.class, pessoa.getId());

		repository.delete(pessoa);
		Pessoa pessoaInexistente = entityManager.find(Pessoa.class, pessoa.getId());

		Assertions.assertThat(pessoaInexistente).isNull();
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
		return pessoa;
	}


	public  Pessoa pessoaPersitida(){
		Pessoa pessoa = criarPessoa();

		entityManager.persist(pessoa);

		return pessoa;
	}
}
