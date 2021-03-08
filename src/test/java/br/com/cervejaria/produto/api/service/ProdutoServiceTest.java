package br.com.cervejaria.produto.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cervejaria.produto.api.dto.ProdutoDto;
import br.com.cervejaria.produto.api.exception.ResourceNotFoundException;
import br.com.cervejaria.produto.api.model.Produto;
import br.com.cervejaria.produto.api.repository.ProdutoRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProdutoServiceTest {
	
	private ProdutoService service;
	
	private ProdutoRepository repository;
	
	@BeforeEach
	public void initService() {
		repository = mock(ProdutoRepository.class);
		service = new ProdutoService(repository);
	}
	
	@Test
	public void findById() {
		Optional<Produto> produtoMock = produto();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(produtoMock);
		Optional<Produto> result = service.findById(1L);
		assertThat(result.isPresent());
		assertThat(result.get().getNome()).isEqualTo("Refrigerante");
		assertThat(result.get().getValor()).isEqualTo(new BigDecimal("3.50"));
	}
	
	@Test
	public void findAll() {
		List<Produto> produtosMock = produtos();
		Mockito.when(repository.findAll()).thenReturn(produtosMock);
		Collection<Produto> result = service.findAll();
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void cadastrarTest() {
		Produto produtoMock = produto().get();
		Mockito.when(repository.save(Mockito.any())).thenReturn(produtoMock);
		Produto result = service.salvar(new Produto());
		assertNotNull(result);
		assertThat(result.getId()).isEqualTo(1L);
	}
	
	@Test
	public void atualizarViaPut() {
		Optional<Produto> produtoMock = produto();
		Produto produtoAtualizadoMock = produtoAtualizado().get();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(produtoMock);
		Mockito.when(repository.save(Mockito.any())).thenReturn(produtoAtualizadoMock);
		Produto result = service.atualizarViaPut(1L, new ProdutoDto());
		assertNotNull(result);
		assertThat(result.getValor()).isNotEqualTo(produtoMock.get().getValor());
		assertThat(result.getValor()).isEqualTo(new BigDecimal("3.00"));
	}
	
	@Test
	public void atualizarViaPutComIdInexistente() {
		Produto produtoAtualizadoMock = produtoAtualizado().get();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Mockito.when(repository.save(Mockito.any())).thenReturn(produtoAtualizadoMock);
		try {
			service.atualizarViaPut(55L, new ProdutoDto());
		}catch(Exception e) {
			assertTrue(e instanceof ResourceNotFoundException);
		}
	}
	
	private Optional<Produto> produto() {
		return Optional.ofNullable(new Produto(1L, "Refrigerante", new BigDecimal("3.50")));
	}
	
	private Optional<Produto> produtoAtualizado() {
		return Optional.ofNullable(new Produto(1L, "Refrigerante", new BigDecimal("3.00")));
	}
	
	private List<Produto> produtos(){
		return Arrays.asList(new Produto(1L, "Refrigerante", new BigDecimal("3.50")),
				new Produto(1L, "Cerveja", new BigDecimal("2.50")));
	}

}
