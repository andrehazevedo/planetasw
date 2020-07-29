package org.casa.planetassw.api.service;

import java.util.List;

import org.casa.planetassw.api.documento.Planeta;

public interface PlanetaService {

	public Planeta cadastrar(Planeta planeta);
	
	public Planeta atualizar(Planeta planeta);
	
	public Planeta buscarPorId(String id);
	
	public Planeta buscarPorNome(String nome);

	public void remover(String id);

	public List<Planeta> listar();
	
}
