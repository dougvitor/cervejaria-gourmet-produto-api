package br.com.cervejaria.produto.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdutoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Identificador único do Produto")
	private Long id;
	
	@ApiModelProperty(value = "Nome do Produto", required = true)
	@NotBlank(message = "O campo nome do produto é obrigatório!")
	private String nome;
	
	@ApiModelProperty(value = "Valor do Produto", required = true)
	@NotNull(message = "O campo valor do produto é obrigatório!")
	private BigDecimal valor;
	
}
