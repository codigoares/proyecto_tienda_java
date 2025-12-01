package com.ares.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//con @Entity indicamos la tabla a la que va a quedar asociada esta clase 
// una entidad es una clase asociada a una tabla
// para que una clase pueda ser entidad debe tener un campo marcado como @Id

@Entity
@Table(name = "tabla_libros")
public class Libro {

	@Size(min = 3, max = 40, message = "titulo debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "debes insertar un titulo")
	@Pattern(regexp = "[A-Za-z0-9 áéíóúÁÉÍÓÚñÑ]+", message = "solo letras, numeros y espacios")
	private String titulo;
	
	@NotNull(message = "debes insertar un precio")
	@Min(value = 1, message = "el precio mínimo es de 1 euro")
	@Max(value = 999, message = "el precio maximo es 999 euros")
	private double precio;
	
	private String descripcion;
	
	//campo para la imagen del producto:
	@Lob
	@Column(name = "imagen_portada", columnDefinition = "LONGBLOB")
	private byte[] imagenPortada;
 	
	@Transient
	private MultipartFile imagen;
	
	@Transient
	private long idCategoria;
	
	@OneToMany(mappedBy = "libro")
	private List<Carrito> carritos =
		new ArrayList<Carrito>();
	
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	public Libro() {
	
	}
	
	public Libro(String titulo, double precio, String descripcion) {
		super();
		this.titulo = titulo;
		this.precio = precio;
		this.descripcion = descripcion;
	}

	public Libro(String titulo, double precio, String description, long id) {
		this.titulo = titulo;
		this.precio = precio;
		this.descripcion = description;
		this.id = id;
	}
	
	public List<Carrito> getCarritos() {
		return carritos;
	}

	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}

	public byte[] getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", precio=" + precio + ", description=" + descripcion + ", id=" + id + "]";
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	
}
