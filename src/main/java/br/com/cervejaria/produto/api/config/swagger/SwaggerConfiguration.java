package br.com.cervejaria.produto.api.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration{
	
	@Bean
	public Docket cervejariaGourmetApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cervejaria.produto.api"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.apiInfo(metaData());
		
		docket.ignoredParameterTypes(Pageable.class);
		
		return docket;
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Cervejaria Gourmet API de Produtos.")
				.description("API REST para Manutenção do Cadastro de Produtos.")
				.version("1.0.0")
				.build();
	}
	
}
