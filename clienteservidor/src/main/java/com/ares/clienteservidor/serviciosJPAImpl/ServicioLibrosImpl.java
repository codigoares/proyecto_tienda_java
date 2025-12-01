package com.ares.clienteservidor.serviciosJPAImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.ares.clienteservidor.constantesSQL.ConstantesSQL;
import com.ares.clienteservidor.model.Categoria;
import com.ares.clienteservidor.model.Libro;
import com.ares.clienteservidor.servicios.ServicioLibros;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioLibrosImpl implements ServicioLibros{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarLibro(Libro libro) {		
		try {
			libro.setImagenPortada(libro.getImagen().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//tenemos que asociar la categoria a nivel de base de datos
		//porque idCategoria tiene puesto @Transient
		Categoria categoria = entityManager.find(Categoria.class, libro.getIdCategoria());
		libro.setCategoria(categoria);		
		entityManager.persist(libro);
	}

	@Override
	public List<Libro> obtenerLibros() {
		return entityManager.createQuery("select l from Libro l order by l.id desc").getResultList();
	}

	@Override
	public void borrarLibro(long id) {
		//antes de borrar el producto debemos eliminar todas las referencias 
		//al mismo en el carrito y pedidos
		entityManager.createNativeQuery(
				"delete from CARRITO where LIBRO_ID = :id ").
				setParameter("id", id).executeUpdate();
		entityManager.createNativeQuery(
				"delete from PRODUCTO_PEDIDO where LIBRO_ID = :id ").
				setParameter("id", id).executeUpdate();		
		//createNativeQuery lanza sql, no confundir con createQuery
		entityManager.createNativeQuery(
				"delete from tabla_libros where id = :id ").
				setParameter("id", id).executeUpdate();
	}

	@Override
	public void actualizarLibro(Libro libroFormEditar) {
		//lo siguiente es un tanto bestia, 
		//porque hay campos sensibles como por ejemplo el de byte[]
		//entityManager.merge(libro);
		Libro libroBaseDeDatos = entityManager.find(Libro.class, libroFormEditar.getId());
		libroBaseDeDatos.setTitulo(libroFormEditar.getTitulo());
		libroBaseDeDatos.setPrecio(libroFormEditar.getPrecio());
		libroBaseDeDatos.setDescripcion(libroFormEditar.getDescripcion());
		if( libroFormEditar.getImagen().getSize() > 0 ) {
			try {
				libroBaseDeDatos.setImagenPortada(libroFormEditar.getImagen().getBytes());
			} catch (IOException e) {
				System.out.println("no se pudo procesar el archivo subido");
				e.printStackTrace();
			}
		}
		libroBaseDeDatos.setCategoria(entityManager.find(Categoria.class, libroFormEditar.getIdCategoria()));
		entityManager.merge(libroBaseDeDatos);
		
	}

	@Override
	public Libro obtenerLibroPorId(long id) {
		return entityManager.find(Libro.class, id);
	}

	@Override
	public List<Map<String, Object>> obtenerLibrosParaFormarJSON() {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_LIBROS_PARA_JSON, Tuple.class);
		List<Tuple> tuples = query.getResultList();

		List<Map<String, Object>> resultado = new ArrayList<>();
		for (Tuple tuple : tuples) {
		    Map<String, Object> fila = new HashMap<>();
		    for (TupleElement<?> element : tuple.getElements()) {
		        fila.put(element.getAlias(), tuple.get(element));
		    }
		    resultado.add(fila);
		}
		return resultado;
	}
	
	
}






