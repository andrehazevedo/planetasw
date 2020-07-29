package org.casa.planetassw.api.repositorio;

import org.casa.planetassw.api.documento.Planeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	@Query("{\"nome\" : ?0}")
	Planeta buscarPorNome(String nome);
}
