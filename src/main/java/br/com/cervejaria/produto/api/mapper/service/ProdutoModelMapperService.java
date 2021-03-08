package br.com.cervejaria.produto.api.mapper.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cervejaria.produto.api.dto.ProdutoDto;
import br.com.cervejaria.produto.api.model.Produto;

@Service
public class ProdutoModelMapperService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDto convertToDto(Produto model) {
		return modelMapper.map(model, ProdutoDto.class);
	}
	
	public Produto convertToEntity(ProdutoDto dto) {
		return modelMapper.map(dto, Produto.class);
	}

}
