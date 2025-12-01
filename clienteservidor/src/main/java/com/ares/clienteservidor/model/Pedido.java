package com.ares.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
	
	//estado indicara si el pedido esta completado o no
	private String estado;
	
	//paso1:
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	
	//paso2:
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	
	//lo siguiente simplemente pide los productos pedido cuando se pida un pedido
	//asociacion inversa del @ManyToOne a Pedido mapeado en ProductoPedido
	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
	private List<ProductoPedido> productosPedido = 
		new ArrayList<ProductoPedido>();	
	
	@ManyToOne(targetEntity = Usuario.class, optional = false)
	private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<ProductoPedido> getProductosPedido() {
		return productosPedido;
	}

	public void setProductosPedido(List<ProductoPedido> productosPedido) {
		this.productosPedido = productosPedido;
	}

	


}
