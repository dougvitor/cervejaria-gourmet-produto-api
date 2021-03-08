package br.com.cervejaria.produto.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import br.com.cervejaria.produto.api.dto.ProdutoDto;
import br.com.cervejaria.produto.api.mapper.service.ProdutoModelMapperService;
import br.com.cervejaria.produto.api.model.Produto;
import br.com.cervejaria.produto.api.service.ProdutoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@Autowired
	private ProdutoModelMapperService mapper;
	
	@GetMapping
	@ApiOperation(value = "Serviço para listar todos os produtos.", httpMethod = "GET", responseContainer = "List", response = ProdutoDto.class)
	public Collection<ProdutoDto> listarTodos(){
		
		Collection<Produto> produtos = service.findAll();
		
		return produtos
				.stream()
					.map(mapper::convertToDto)
						.collect(Collectors.toList());
	}
	
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Serviço para obter produtos por identificador único.", httpMethod = "GET", response = ProdutoDto.class)
	public ResponseEntity<ProdutoDto> findById(@PathVariable Long id){
		Optional<Produto> produto = service.findById(id);
		
		if(!produto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(mapper.convertToDto(produto.get()));
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
	            value = "Número da página."),
	    @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
	            value = "Tamanho da página."),
	    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
	            value = "Ordenação.")
	})
	@GetMapping(value = "/filtro-por-valor-produto")
	@ApiOperation(value = "Serviço para listar produtos por valor.", httpMethod = "GET", response = ProdutoDto.class)
	public Page<ProdutoDto> listarPorValor(@RequestParam(required = false) BigDecimal valor, @PageableDefault(sort = "id", direction = Direction.DESC, page= 0, size = 1) Pageable paginacao){
		
		Page<Produto> produtos = null;
		
		if(valor == null) {
			produtos = service.findAll(paginacao);
		}else {
			produtos = service.findByValor(valor, paginacao);
		}
		
		return produtos.map(mapper::convertToDto);
	}
	
	@GetMapping(value = "/filtro-por-nome-produto/{nome}")
	@ApiOperation(value = "Serviço para obter produtos por nome.", httpMethod = "GET", response = ProdutoDto.class)
	public ResponseEntity<ProdutoDto> obterPorNome(@PathVariable String nome){
		
		Produto produto = service.findByNomeIgnoreCaseContaining(nome);
		
		if(produto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(mapper.convertToDto(produto));
	}
	
	@PostMapping
	@ApiOperation(value = "Serviço para cadastrar novos produtos.", httpMethod = "POST", response = ProdutoDto.class)
	public ResponseEntity<ProdutoDto> cadastrar(@RequestBody @Valid ProdutoDto dto, UriComponentsBuilder uriBuilder){
		Produto produto = mapper.convertToEntity(dto);
		Produto produtoSalvo = service.salvar(produto);
		
		URI uri = uriBuilder
				.path("/produto/{id}")
				.buildAndExpand(produtoSalvo.getId())
				.toUri();
		
		return ResponseEntity
				.created(uri)
				.body(mapper.convertToDto(produtoSalvo));
	}
	
	@PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
	@ApiOperation(value = "Serviço para atualizar informações dos produtos.", httpMethod = "PATCH", response = ProdutoDto.class)
	public ResponseEntity<ProdutoDto> atualizarViaPatch(@PathVariable Long id, @RequestBody JsonPatch jsonPatch) throws JsonProcessingException, JsonPatchException{
		Produto produtoAtualizado = service.atualizarViaPatch(id, jsonPatch);
		return ResponseEntity.ok(mapper.convertToDto(produtoAtualizado));
	}
	
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Serviço para sobrescrever todas informações dos produtos.", httpMethod = "PUT", response = ProdutoDto.class)
	public ResponseEntity<ProdutoDto> atualizarViaPut(@PathVariable Long id, @RequestBody ProdutoDto produto){
		Produto produtoAtualizado = service.atualizarViaPut(id, produto);
		return ResponseEntity.ok(mapper.convertToDto(produtoAtualizado));
	}
	
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Serviço para excluir produtos.", httpMethod = "DELETE")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Optional<Produto> produto = service.findById(id);
		
		if(produto.isPresent()) {
			service.excluir(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
