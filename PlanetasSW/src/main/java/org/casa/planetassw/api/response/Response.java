package org.casa.planetassw.api.response;

import java.util.List;

public class Response<T> {

	private Integer codigo;
	private T dados;
	private List<String> erros;
	
	public Response(Integer codigo, T dados) {
		this.codigo = codigo;
		this.dados = dados;
	}
	
	public Response(Integer codigo, List<String> erros) {
		this.codigo = codigo;
		this.erros = erros;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public T getDados() {
		return dados;
	}

	public void setDados(T dados) {
		this.dados = dados;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
}
