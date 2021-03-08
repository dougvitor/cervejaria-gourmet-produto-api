package br.com.cervejaria.produto.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CervejariaGourmetProdutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CervejariaGourmetProdutoApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
