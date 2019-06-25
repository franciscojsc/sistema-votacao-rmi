package br.com.franciscochaves.model;

import java.io.Serializable;

public class Eleitor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
	private String id;
	
	public Eleitor(String indentificador) {
		this.id = indentificador;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
