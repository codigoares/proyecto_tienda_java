package com.ares.clienteservidor.serviciosJPAImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ares.clienteservidor.constantesSQL.ConstantesSQL;
import com.ares.clienteservidor.model.Carrito;
import com.ares.clienteservidor.model.Libro;
import com.ares.clienteservidor.model.Usuario;
import com.ares.clienteservidor.servicios.ServicioCarrito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void agregarProducto(int idUsuario, long idProducto, int cantidad) {
		//aunque seria mas eficiente lanzar una sql 
		Usuario usuario = (Usuario) entityManager.find(Usuario.class, idUsuario);
		List<Carrito> productosCarrito = usuario.getProductosCarrito();
		boolean productoEnCarrito = false;
		//ver si el producto esta en el carrito y en tal caso
		//incrementar su cantidad
		for(Carrito pc : productosCarrito) {
			if(pc.getLibro().getId() == idProducto) {
				productoEnCarrito = true;
				pc.setCantidad(pc.getCantidad() + cantidad);
				entityManager.merge(pc);//actualizamos el registro en carrito
			}
		}
		//si el producto no esta en el carrito, crear un registro nuevo
		if( ! productoEnCarrito ) {
			Carrito productoCarrito = new Carrito();
			productoCarrito.setUsuario(usuario);
			productoCarrito.setLibro(entityManager.find(Libro.class, idProducto));
			productoCarrito.setCantidad(cantidad);
			entityManager.persist(productoCarrito);
		}
		
	}//end agregarProducto

	@Override
	public List<Map<String, Object>> obtenerProductosCarrito(int idUsuario) {
		Query query = 
				entityManager.createNativeQuery(
						ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO, Tuple.class);
		query.setParameter("usuario_id", idUsuario);
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

	@Override
	public void quitarProductoCarrito(int idUsuario, Long idLibro) {
		entityManager.createNativeQuery(ConstantesSQL.SQL_ELIMINAR_PRODUCTO_CARRITO).
			setParameter("usuario_id", idUsuario).setParameter("libro_id", idLibro).executeUpdate();
	}

}//end class









