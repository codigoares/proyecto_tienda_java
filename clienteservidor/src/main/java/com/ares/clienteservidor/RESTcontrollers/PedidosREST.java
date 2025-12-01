package com.ares.clienteservidor.RESTcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ares.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.ares.clienteservidor.servicios.ServicioPedidos;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pedidosREST/")
public class PedidosREST {
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("paso3")
	public String paso3(HttpServletRequest request) {
		int idUsuario = 
				(int)request.getSession().getAttribute("usuario_id");
		servicioPedidos.confirmarPedido(idUsuario);
		return "ok";
	}
	
	@RequestMapping("paso2")
	public ResumenPedido paso2(String tarjeta, String numero, String titular, 
			HttpServletRequest request) {
		int idUsuario = 
				(int)request.getSession().getAttribute("usuario_id");
		ResumenPedido resumen = 
				servicioPedidos.procesarPaso2(tarjeta,numero,titular, idUsuario);
		return resumen;		
	}
	
	@RequestMapping("paso1")
	public String paso1(String nombre, String direccion, String provincia, HttpServletRequest request) {
		String respuesta = "";
		int idUsuario = 
				(int)request.getSession().getAttribute("usuario_id");
		servicioPedidos.procesarPaso1(nombre, direccion, provincia, idUsuario);
		respuesta = "ok";
		return respuesta;
	}

}
