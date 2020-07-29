package org.casa.planetassw.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.casa.planetassw.api.documento.Planeta;
import org.casa.planetassw.api.response.Response;
import org.casa.planetassw.api.service.PlanetaService;
import org.casa.planetassw.api.swapi.PlanetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/planetasw")
public class PlanetaController {

	private static final String URL = "https://swapi.dev/api/planets/?search={name}";
	private static final int ZERO = 0;
	private static final Integer SUCESSO = 0;
	private static final Integer ERRO = -1;

	
	@Autowired
	private PlanetaService planetaService;
	
	@Autowired
	private MessageSource message;

	@GetMapping
	public ResponseEntity<Response<List<Planeta>>> listar(){
		List<Planeta> listaPlanetas = getPlanetaService().listar();

		for (Planeta planeta : listaPlanetas) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				UriComponents uri = UriComponentsBuilder.fromHttpUrl(URL).build();
				PlanetResult planetResult = restTemplate.getForObject(uri.toUriString(), PlanetResult.class, planeta.getNome());
				if (!planetResult.getResults().isEmpty()) {
					planeta.setQuantidadeAparicao(planetResult.getResults().get(ZERO).getFilms().size());
				} else {
					planeta.setQuantidadeAparicao(0);
				}
			} catch (Exception e) {
				planeta.setQuantidadeAparicao(0);
			}
		}
	
		return ResponseEntity.ok(new Response<List<Planeta>>(SUCESSO, listaPlanetas));
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Response<Planeta>> buscarPorId(@PathVariable(name="id") String id){
		Planeta planeta = getPlanetaService().buscarPorId(id);
		if (planeta != null) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				UriComponents uri = UriComponentsBuilder.fromHttpUrl(URL).build();
				PlanetResult planetResult = restTemplate.getForObject(uri.toUriString(), PlanetResult.class, planeta.getNome());
				if (!planetResult.getResults().isEmpty()) {
					planeta.setQuantidadeAparicao(planetResult.getResults().get(ZERO).getFilms().size());
				} else {
					planeta.setQuantidadeAparicao(0);
				}
			} catch (Exception e) {
				planeta.setQuantidadeAparicao(0);
			}
			return ResponseEntity.ok(new Response<Planeta>(SUCESSO, planeta));
		} else {
			List<String> erros = new ArrayList<String>();
			erros.add(message.getMessage("planeta.nao.encontrado", null, new Locale("pt-br")));
			return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));
		}
	}
	
	@GetMapping(path = "/nome/{nome}")
	public ResponseEntity<Response<Planeta>> buscarPorNome(@PathVariable(name="nome") String nome){
		Planeta planeta = getPlanetaService().buscarPorNome(nome);
		if (planeta != null) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				UriComponents uri = UriComponentsBuilder.fromHttpUrl(URL).build();
				PlanetResult planetResult = restTemplate.getForObject(uri.toUriString(), PlanetResult.class, planeta.getNome());
				if (!planetResult.getResults().isEmpty()) {
					planeta.setQuantidadeAparicao(planetResult.getResults().get(ZERO).getFilms().size());
				} else {
					planeta.setQuantidadeAparicao(0);
				}
			} catch (Exception e) {
				planeta.setQuantidadeAparicao(0);
			}
			return ResponseEntity.ok(new Response<Planeta>(SUCESSO, planeta));
		} else {
			List<String> erros = new ArrayList<String>();
			erros.add(message.getMessage("planeta.nao.encontrado", null, new Locale("pt-br")));
			return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));
		}
	}
	
	@PostMapping
	public ResponseEntity<Response<Planeta>> cadastrar(@RequestBody Planeta planeta){
		List<String> erros = new ArrayList<String>();
		if (planeta.getNome().isBlank()) {
			erros.add(message.getMessage("mandatorio.nao.informado", new Object[] {"Nome"}, new Locale("pt-br")));
		}
		if (planeta.getClima().isBlank()) {
			erros.add(message.getMessage("mandatorio.nao.informado", new Object[] {"Clima"}, new Locale("pt-br")));
		}
		if (planeta.getTerreno().isBlank()) {
			erros.add(message.getMessage("mandatorio.nao.informado", new Object[] {"Terreno"}, new Locale("pt-br")));
		}
		if (erros.isEmpty()) {
			Planeta planetaNomeExistente = getPlanetaService().buscarPorNome(planeta.getNome());
			if (planetaNomeExistente != null) {
				erros.add(message.getMessage("planeta.ja.cadastrado", null, new Locale("pt-br")));
				return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));
			} else {
				return ResponseEntity.ok(new Response<Planeta>(SUCESSO, getPlanetaService().cadastrar(planeta)));
			}
		} else {
			return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));			
		}
	}
	
	@PutMapping(path = "/id/{id}")
	public ResponseEntity<Response<Planeta>> atualizar(@PathVariable(name="id") String id, @RequestBody Planeta planeta){
		List<String> erros = new ArrayList<String>();
		if (planeta.getNome().isBlank()) {
			erros.add(message.getMessage("mandatorio.nao.informado", new Object[] {"Nome"}, new Locale("pt-br")));
		}
		if (planeta.getClima().isBlank()) {
			erros.add(message.getMessage("mandatorio.nao.informado", new Object[] {"Clima"}, new Locale("pt-br")));
		}
		if (planeta.getTerreno().isBlank()) {
			erros.add(message.getMessage("mandatorio.nao.informado", new Object[] {"Terreno"}, new Locale("pt-br")));
		}
		if (erros.isEmpty()) {
			Planeta planetaIdExistente = getPlanetaService().buscarPorId(id);
			if (planetaIdExistente != null) {
				Planeta planetaNomeExistente = getPlanetaService().buscarPorNome(planeta.getNome());
				if ((planetaNomeExistente != null) && !id.equalsIgnoreCase(planetaNomeExistente.getId())) {
					erros.add(message.getMessage("planeta.ja.cadastrado", null, new Locale("pt-br")));
					return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));
				} else {
					planeta.setId(id);
					return ResponseEntity.ok(new Response<Planeta>(SUCESSO, getPlanetaService().atualizar(planeta)));
				}			
				
			} else {
				erros.add(message.getMessage("planeta.nao.encontrado", null, new Locale("pt-br")));
				return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));
			}
		} else {
			return ResponseEntity.badRequest().body(new Response<Planeta>(ERRO, erros));			
		}
	}
	
	@DeleteMapping(path = "/id/{id}")
	public ResponseEntity<Response<Integer>> remover(@PathVariable(name="id") String id) {
		getPlanetaService().remover(id);
		return ResponseEntity.ok(new Response<Integer>(ERRO, SUCESSO));
	}
	
	public PlanetaService getPlanetaService() {
		return planetaService;
	}

	public void setPlanetaService(PlanetaService planetaService) {
		this.planetaService = planetaService;
	}

	public MessageSource getMessage() {
		return message;
	}

	public void setMessage(MessageSource message) {
		this.message = message;
	}
}
