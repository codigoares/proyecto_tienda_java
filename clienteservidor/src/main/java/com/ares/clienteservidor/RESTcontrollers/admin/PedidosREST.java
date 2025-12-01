package com.ares.clienteservidor.RESTcontrollers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ares.clienteservidor.servicios.ServicioPedidos;

@RestController(value = "pedidosRESTadmin")
@RequestMapping("admin/pedidosREST/")
public class PedidosREST {
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("actualizarEstadoPedido")
	public String actualizarEstadoPedido(
			@RequestParam("id") Integer id, @RequestParam("estado") String estado) {
		servicioPedidos.actualizarPedido(id, estado);
		return "ok";
	}

}
