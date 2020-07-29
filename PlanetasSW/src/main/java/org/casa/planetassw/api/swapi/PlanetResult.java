package org.casa.planetassw.api.swapi;

import java.util.List;

public class PlanetResult {
	private String count;
	private List<Planet> results;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<Planet> getResults() {
		return results;
	}
	public void setResults(List<Planet> results) {
		this.results = results;
	}

	
}
