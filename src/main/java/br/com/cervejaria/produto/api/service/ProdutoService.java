package br.com.cervejaria.produto.api.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import br.com.cervejaria.produto.api.dto.ProdutoDto;
import br.com.cervejaria.produto.api.exception.BusinessMessageException;
import br.com.cervejaria.produto.api.exception.ResourceNotFoundException;
import br.com.cervejaria.produto.api.model.Produto;
import br.com.cervejaria.produto.api.repository.ProdutoRepository;

@Service
public class ProdutoService extends AbstractService<Produto>{
	
	private ProdutoRepository repository;
	
	@Autowired
	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	public Optional<Produto> findById(Long id) {
		return repository.findById(id);
	}
	
	public Collection<Produto> findAll() {
		return repository.findAll();
	}
	
	public Page<Produto> findAll(Pageable paginacao) {
		return repository.findAll(paginacao);
	}
	
	public Produto findByNomeIgnoreCaseContaining(String nome) {
		return repository.findByNomeEquals(nome);
	}
	
	public Page<Produto> findByValor(BigDecimal valor, Pageable paginacao) {
		return repository.findByValor(valor, paginacao);
	}

	public Produto salvar(Produto produto) {
		
		if(repository.existsByNomeIgnoreCaseContaining(produto.getNome())) {
			throw new BusinessMessageException("Produto informado já existe!");
		}
		
		return repository.save(produto);
	}
	
	public Produto atualizarViaPatch(Long id, JsonPatch patch) throws JsonProcessingException, JsonPatchException {
		Produto produtoAtualizado = obterProduto(id);
		Produto produtoComPatchAplicado = aplicarPatch(produtoAtualizado, patch);
		return repository.save(produtoComPatchAplicado);
	}
	
	public Produto atualizarViaPut(Long id, ProdutoDto produto) {
		Produto produtoASerAlterado = obterProduto(id);
		produtoASerAlterado.setNome(produto.getNome());
		produtoASerAlterado.setValor(produto.getValor());
		return repository.save(produtoASerAlterado);
	}
	
	public void excluir(final Long id) {
		repository.deleteById(id);
	}
	
	public Produto obterProduto(Long id) {
		Optional<Produto> produtoASerAlterado = findById(id);
		
		if(!produtoASerAlterado.isPresent()) {
			throw new ResourceNotFoundException("Não existe produto cadastrado com o id informado!");
		}
		
		return produtoASerAlterado.get();
	}

}
