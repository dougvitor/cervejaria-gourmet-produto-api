package br.com.cervejaria.produto.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

public abstract class AbstractService<T> {
	
	@SuppressWarnings("unchecked")
	protected T aplicarPatch(T type, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode patched = patch.apply(objectMapper.convertValue(type, JsonNode.class));
	    return (T) objectMapper.treeToValue(patched, type.getClass());
	}
}
