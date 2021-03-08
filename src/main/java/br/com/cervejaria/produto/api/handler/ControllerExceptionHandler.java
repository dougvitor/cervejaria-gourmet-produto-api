package br.com.cervejaria.produto.api.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cervejaria.produto.api.exception.BusinessMessageException;
import br.com.cervejaria.produto.api.exception.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(ResourceNotFoundException ex) {
		MensagemErro mensagemErro = new MensagemErro(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
	}
	
	
	@ExceptionHandler(BusinessMessageException.class)
	public ResponseEntity<Object> handleBusinessMessage(BusinessMessageException ex) {
		MensagemErro mensagemErro = new MensagemErro(HttpStatus.PRECONDITION_FAILED.value(), new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(mensagemErro);
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		MensagemErro mensagemErro = montarMensagemErro(ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
	}

	private MensagemErroList montarMensagemErro(MethodArgumentNotValidException ex) {
		List<String> erros = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors().forEach((erro) -> {
			erros.add(erro.getDefaultMessage());
		});
		
		String mensagemDefault = "Campo(s) inválidos";
		
		MensagemErroList apiErro = new MensagemErroList(HttpStatus.BAD_REQUEST.value(), mensagemDefault, new Date(), erros);
		return apiErro;
	}

}
