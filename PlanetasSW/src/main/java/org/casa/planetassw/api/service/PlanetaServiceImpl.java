package org.casa.planetassw.api.service;

import java.util.List;
import java.util.Optional;

import org.casa.planetassw.api.documento.Planeta;
import org.casa.planetassw.api.repositorio.PlanetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Override
	public Planeta cadastrar(Planeta planeta) {
		return getPlanetaRepository().save(planeta);
	}

	@Override
	public Planeta atualizar(Planeta planeta) {
		return getPlanetaRepository().save(planeta);
	}

	@Override
	public Planeta buscarPorId(String id) {
		Optional<Planeta> optionalPlaneta = getPlanetaRepository().findById(id);
		if (optionalPlaneta.isEmpty()) {
			return null;
		}
		
		return optionalPlaneta.get();
	}
	
	@Override
	public Planeta buscarPorNome(String nome) {
		return getPlanetaRepository().buscarPorNome(nome);
	}

	@Override
	public void remover(String id) {
		getPlanetaRepository().deleteById(id);
	}

	@Override
	public List<Planeta> listar() {
		return getPlanetaRepository().findAll();
	}

	public PlanetaRepository getPlanetaRepository() {
		return planetaRepository;
	}

	public void setPlanetaRepository(PlanetaRepository planetaRepository) {
		this.planetaRepository = planetaRepository;
	}
}
