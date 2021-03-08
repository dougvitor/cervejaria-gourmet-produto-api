package br.com.cervejaria.produto.api.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cervejaria.produto.api.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	Page<Produto> findByValor(BigDecimal valor, Pageable paginacao);

	Produto findByNomeEquals(String nome);
	
	boolean existsByNomeIgnoreCaseContaining(String nome);
	
}
