package com.ares.clienteservidor.setUp;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TablaSetUp {

	private boolean completado;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
