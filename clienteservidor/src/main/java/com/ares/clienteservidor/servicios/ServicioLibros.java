package com.ares.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.ares.clienteservidor.model.Libro;


public interface ServicioLibros {
	
	void registrarLibro(Libro libro);
	
	List<Libro> obtenerLibros();
	
	void borrarLibro(long id);
	
	void actualizarLibro(Libro libro);

	Libro obtenerLibroPorId(long long1);
	
	// para la parte publica, servicios REST
	
	List< Map<String, Object> > obtenerLibrosParaFormarJSON ();

	
}







