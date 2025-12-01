package com.ares.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

public interface ServicioCarrito {

	void agregarProducto(int idUsuario, long idProducto, int cantidad);
	List<Map<String, Object>> obtenerProductosCarrito(int idUsuario);
	void quitarProductoCarrito(int idUsuario, Long idLibro);
	
}
