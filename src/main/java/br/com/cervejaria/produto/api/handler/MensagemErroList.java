package br.com.cervejaria.produto.api.handler;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemErroList extends MensagemErro{

	private static final long serialVersionUID = 1L;
	
	private List<String> erros;
	
	public MensagemErroList(int statusCode, String mensagemDefault, Date data, List<String> erros) {
		super(statusCode, data, mensagemDefault);
		this.erros = erros;
	}

}
