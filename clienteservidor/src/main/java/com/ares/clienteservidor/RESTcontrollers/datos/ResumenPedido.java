package com.ares.clienteservidor.RESTcontrollers.datos;

import java.util.List;
import java.util.Map;

public class ResumenPedido {
	
	//productos que hay en el carrito
	private List<Map<String, Object>> libros;
	
	//datos del paso1
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	
	//datos del paso2
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	
	public List<Map<String, Object>> getLibros() {
		return libros;
	}
	public void setLibros(List<Map<String, Object>> libros) {
		this.libros = libros;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTitularTarjeta() {
		return titularTarjeta;
	}
	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	

}
