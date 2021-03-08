package br.com.cervejaria.produto.api.handler;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MensagemErro implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	
	private Date data;
	
	private String erro;

}
