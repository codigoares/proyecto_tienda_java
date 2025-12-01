package com.ares.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ares.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.ares.clienteservidor.constantes.Estados;
import com.ares.clienteservidor.constantesSQL.ConstantesSQL;
import com.ares.clienteservidor.model.Carrito;
import com.ares.clienteservidor.model.Pedido;
import com.ares.clienteservidor.model.ProductoPedido;
import com.ares.clienteservidor.model.Usuario;
import com.ares.clienteservidor.servicios.ServicioCarrito;
import com.ares.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class ServicioPedidosImpl implements ServicioPedidos{

	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<Pedido> obtenerPedidos() {
		return entityManager.createQuery(
				"select p from Pedido p order by p.id desc", Pedido.class).getResultList();
	}

	@Override
	public Pedido obtenerPedidoPorId(int idPedido) {
		return (Pedido)entityManager.find(Pedido.class, idPedido);
	}

	@Override
	public void actualizarPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);
		p.setEstado(estado);	
		entityManager.merge(p);
	}
	
	@Override
	public void confirmarPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		//obtener los productos en el carrito para meterlos en productoPedido
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		List<Carrito> c = entityManager.createQuery(
				"select c from Carrito c where c.usuario.id = :usuario_id")
				.setParameter("usuario_id", idUsuario)
				.getResultList();
		System.out.println("productos en el carrito a procesar: " + c.size());
		for( Carrito productoCarrito : c ) {
			ProductoPedido pp = new ProductoPedido();
			pp.setCantidad(productoCarrito.getCantidad());
			pp.setLibro(productoCarrito.getLibro());
			pp.setPedido(p);
			entityManager.persist(pp);
		}
		p.setEstado(Estados.TERMINADO.name());
		entityManager.merge(p);
		//eliminar los productos del carrito
		entityManager.createNativeQuery(ConstantesSQL.SQL_VACIAR_CARRITO)
		.setParameter("usuario_id", idUsuario).executeUpdate();
		
	}	
	
	@Override
	public void procesarPaso1(String nombre, String direccion, String provincia, int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setNombreCompleto(nombre);
		p.setDireccion(direccion);
		p.setProvincia(provincia);
		entityManager.merge(p);
	}
	
	@Override
	public ResumenPedido procesarPaso2(String tarjeta, String numero, String titular, int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setTipoTarjeta(tarjeta);
		p.setNumeroTarjeta(numero);
		p.setTitularTarjeta(titular);
		entityManager.merge(p);		
		//preparamos el ResumenPedido a devolver
		return obtenerResumenDelPedido(idUsuario);		
	}
	
	private ResumenPedido obtenerResumenDelPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		ResumenPedido resumen = new ResumenPedido();
		resumen.setNombreCompleto(p.getNombreCompleto());
		resumen.setDireccion(p.getDireccion());
		resumen.setProvincia(p.getProvincia());
		resumen.setTipoTarjeta(p.getTipoTarjeta());
		resumen.setTitularTarjeta(p.getTitularTarjeta());
		//lo siguiente ira ofuscado menos los 4 ultimos numeros
		resumen.setNumeroTarjeta(p.getNumeroTarjeta());
		resumen.setLibros(servicioCarrito.obtenerProductosCarrito(idUsuario));
		return resumen;
	}
	
	//este metodo devuelve el pedido incompleto actual del usuario, 
	//si no existe, lo creamos
	private Pedido obtenerPedidoIncompletoActual(int idUsuario) {
		Usuario usuario = entityManager.find(Usuario.class, idUsuario);
		Object pedidoIncompleto = null;
		List<Pedido> pedidos = 
				entityManager.createQuery(
					"select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id")
				.setParameter("estado", Estados.INCOMPLETO.name())
				.setParameter("usuario_id", idUsuario).getResultList();
		if(pedidos.size() == 1) {
			pedidoIncompleto = pedidos.get(0);
		}
		Pedido pedido = null;
		if(pedidoIncompleto != null) {
			pedido = (Pedido) pedidoIncompleto;
		}else {
			pedido = new Pedido();
			pedido.setEstado(Estados.INCOMPLETO.name());
			pedido.setUsuario(usuario);
		}
		return pedido;
	}

	
}











