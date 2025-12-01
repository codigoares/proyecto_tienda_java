package com.ares.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


//si no ponemos el @Table(name ="nombre_tabla") el nombre de la tabla 
//sera el mismo nombre que la clase, empezando por minuscula
@Entity
public class Usuario {

	private String nombre;
	private String pass;
	
	@Column(unique = true)
	private String email;
	
	@OneToMany(mappedBy = "usuario")
	private List<Carrito> productosCarrito = 
			new ArrayList<Carrito>();	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String nombre, String pass, String email) {
		super();
		this.nombre = nombre;
		this.pass = pass;
		this.email = email;
	}

	public List<Carrito> getProductosCarrito() {
		return productosCarrito;
	}

	public void setProductosCarrito(List<Carrito> productosCarrito) {
		this.productosCarrito = productosCarrito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
