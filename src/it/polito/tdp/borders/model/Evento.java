package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento> {
	
	// c'è un solo tipo di evento: INGRESSO
	
	private int num; // quante persone
	private Country country; // in quale stato
	private int t; // a quale istante di tempo
	
	public Evento(int num, Country country, int t) {
		super();
		this.num = num;
		this.country = country;
		this.t = t;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the t
	 */
	public int getT() {
		return t;
	}

	/**
	 * @param t the t to set
	 */
	public void setT(int t) {
		this.t = t;
	}

	@Override
	public int compareTo(Evento other) {
		return this.t - other.t;
	}
	
}
