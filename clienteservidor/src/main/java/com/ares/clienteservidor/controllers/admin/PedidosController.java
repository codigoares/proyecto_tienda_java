package com.ares.clienteservidor.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ares.clienteservidor.constantes.Estados;
import com.ares.clienteservidor.model.Pedido;
import com.ares.clienteservidor.servicios.ServicioPedidos;

@Controller
@RequestMapping("admin/")
public class PedidosController {

	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("obtenerPedidos")
	public String obtenerPedidos(Model model) {
		model.addAttribute("pedidos", servicioPedidos.obtenerPedidos());
		return "admin/pedidos";
	}
	
	@RequestMapping("verDetallesPedido")
	public String verDetallesPedido(@RequestParam("id") Integer id, Model model) {
		
		Pedido p = servicioPedidos.obtenerPedidoPorId(id);
		model.addAttribute("pedido",p);
		
		//vamos a mandar a la vista los valores del desplegable estado del pedido
		Map<String, String> estados = new HashMap<String, String>();
		estados.put(Estados.INCOMPLETO.name(), "incompleto");
		estados.put(Estados.TERMINADO.name(), "finalizado por el usuario");
		estados.put(Estados.LISTO_PARA_ENVIAR.name(), "listo para enviar");
		estados.put(Estados.ENVIADO.name(), "pedido enviado");
		model.addAttribute("estados",estados);
		
		return "admin/pedidos_detalle";
		
	}
	
}











