package com.ares.clienteservidor.RESTcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ares.clienteservidor.model.Libro;
import com.ares.clienteservidor.servicios.ServicioLibros;

@RestController
@RequestMapping("librosREST/")
public class LibrosREST {
	
	@Autowired
	private ServicioLibros servicioLibros;

	@RequestMapping("obtener")
	public List<Map<String, Object>> obtenerLibros() {		
		return servicioLibros.obtenerLibrosParaFormarJSON();
	}
	
}
