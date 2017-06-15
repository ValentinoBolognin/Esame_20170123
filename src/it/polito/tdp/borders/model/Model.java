package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private UndirectedGraph<Country, DefaultEdge> graph ;
	private List<Country> countries ;
	private Map<Integer, Country> countryMap ;
	
	// risultati simulazione
	List<CountryAndNum> stanziali;
	
	public Model() {
	}

	public List<CountryAndNum> creaGrafo(int anno){
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
		
		//aggiungi i vertici
		BordersDAO dao = new BordersDAO();
		this.countries = dao.loadAllCountries();
		
		this.countryMap = new HashMap<>();
		for(Country c : this.countries) {
			this.countryMap.put(c.getcCode(), c);
		}
		
		Graphs.addAllVertices(graph, this.countries) ;
		
		//aggiungi gli archi
		List<IntegerPair> confini = dao.getCountryPairs(anno) ;		
		for(IntegerPair p: confini) {
			graph.addEdge(this.countryMap.get(p.getN1()), 
					this.countryMap.get(p.getN2())) ;
		}
		
		List<CountryAndNum> lista = new ArrayList<>();
		for(Country c : this.countries) {
			int confinanti = Graphs.neighborListOf(this.graph, c).size() ;
			if(confinanti != 0 )
				lista.add( new CountryAndNum(c, confinanti)) ;
		}
		Collections.sort(lista);
		return lista ;
	}
	
	public static void main(String args[]) {
		Model m = new Model() ;

		List<CountryAndNum> lista = m.creaGrafo(2010);
		System.out.println(m.graph);
		for(CountryAndNum cn : lista) {
			System.out.format("%s: %d\n", cn.getCountry().toString(), cn.getNum()) ;
		}
				
	}

	public int simula(Country partenza) {
	
		Simulatore sim = new Simulatore(this.graph) ;
		
		sim.inserisci(partenza);
		
		sim.run() ;
		
		this.stanziali = sim.getPresenti() ;
		
		return sim.getPassi() ;
	}

	public List<CountryAndNum> getStanziali() {
		return this.stanziali;
	}

	
}
