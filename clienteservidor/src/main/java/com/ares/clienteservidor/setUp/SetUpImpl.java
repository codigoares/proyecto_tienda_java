package com.ares.clienteservidor.setUp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale.Category;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ares.clienteservidor.model.Carrito;
import com.ares.clienteservidor.model.Categoria;
import com.ares.clienteservidor.model.Libro;
import com.ares.clienteservidor.model.Usuario;
import com.ares.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
@Transactional
public class SetUpImpl implements SetUp{

	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void prepararRegistros() {
		TablaSetUp registroSetUp = null;
		//si no hay un registro en la tabla set up, preparamos los registros iniciales
		try {
			registroSetUp = (TablaSetUp) entityManager.createQuery(
					"select r from TablaSetUp r").getSingleResult();
		} catch (Exception e) {
			System.out.println("no se encontro ningun registro en la tabla setup, "
					+ "comenzamos a realizar los registros iniciales...");
		}
		if( registroSetUp != null && registroSetUp.isCompletado() ) {
			return;
		}
		
		//preparar categorias iniciales:
		Categoria cNovela = new Categoria("Novela", "Novela Clasica");
		entityManager.persist(cNovela);
		Categoria cCienciaFiccion = new Categoria("Ciencia Ficcion", "ultimas novedades");
		entityManager.persist(cCienciaFiccion);
		Categoria cEducativo = new Categoria("Educativo","Material de Formacion");
		entityManager.persist(cEducativo);
		
		//preparamos los registros inciales:
		Libro libro1 = new Libro("Jurassic Park", 12.5, "novela de exito");
		libro1.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/libros/jur.jpg") );
		libro1.setCategoria(cCienciaFiccion);
		entityManager.persist(libro1);
		
		Libro libro2 = new Libro("1984", 10.0, "distopía clásica");
		libro2.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/libros/1984.jpg") );
		libro2.setCategoria(cEducativo);
		entityManager.persist(libro2);

		Libro libro3 = new Libro("Cien años de soledad", 15.75, "realismo mágico latinoamericano");
		libro3.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/libros/cien.jpg") );
		libro3.setCategoria(cNovela);
		entityManager.persist(libro3);

		Libro libro4 = new Libro("El código Da Vinci", 13.5, "thriller de misterio");
		libro4.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/libros/davinci.jpg") );
		libro4.setCategoria(cNovela);
		entityManager.persist(libro4);

		Libro libro5 = new Libro("Los juegos del hambre", 11.25, "novela juvenil distópica");
		libro5.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/libros/hambre.jpg") );
		libro5.setCategoria(cNovela);
		entityManager.persist(libro5);

		Libro libro6 = new Libro("El nombre del viento", 14.0, "fantasía épica");
		libro6.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/libros/viento.jpg") );
		libro6.setCategoria(cNovela);
		entityManager.persist(libro6);
		
		Usuario usuario1 = new Usuario("ares", "123", "ares@centronelson.org");
		entityManager.persist(usuario1);
		
		Usuario usuario2 = new Usuario("juan", "abc123", "juan@centronelson.org");
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("maria", "pass456", "maria@centronelson.org");
		entityManager.persist(usuario3);

		Usuario usuario4 = new Usuario("carlos", "secure789", "carlos@centronelson.org");
		entityManager.persist(usuario4);

		Usuario usuario5 = new Usuario("laura", "clave321", "laura@centronelson.org");
		entityManager.persist(usuario5);
		
		
		//vamos a meter unos productos en el carrito de varios usuarios:
		Carrito registroCarrito = new Carrito();
		registroCarrito.setUsuario(usuario1);
		registroCarrito.setLibro(libro1);
		registroCarrito.setCantidad(3);
		entityManager.persist(registroCarrito);
		
		//registrar un pedido para usuario1
		servicioPedidos.procesarPaso1("Ares Sancho", "Nelson 21", "Madrid", usuario1.getId());
		servicioPedidos.procesarPaso2("1", "0123 4567 8901 0123", "Ares Sancho", usuario1.getId());
		servicioPedidos.confirmarPedido(usuario1.getId());
		
		Carrito registroCarrito2 = new Carrito();
		registroCarrito2.setUsuario(usuario3);
		registroCarrito2.setLibro(libro6);
		registroCarrito2.setCantidad(2);
		entityManager.persist(registroCarrito2);		
		
		//una vez preparados los registros iniciales, 
		//marcamos el setup completado de la siguiente forma:
		TablaSetUp registro = new TablaSetUp();
		registro.setCompletado(true);
		entityManager.persist(registro);
		
	}//end prepararRegistros
	
	//metodo que nos va a permitir obtener un byte[] de cada archivo
	// de imagenes_base
	private byte[] obtenerInfoImagen(String ruta_origen) {
		byte[] info = null;
		try {
			URL url = new URL(ruta_origen);
			info = IOUtils.toByteArray(url);
		} catch (IOException e) {
			System.out.println("no se pudo procesar: " + ruta_origen);
			e.printStackTrace();
		}
		return info;
	}

}




