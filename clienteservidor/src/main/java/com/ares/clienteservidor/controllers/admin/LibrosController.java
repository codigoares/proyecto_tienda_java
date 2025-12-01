package com.ares.clienteservidor.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ares.clienteservidor.model.Libro;
import com.ares.clienteservidor.servicios.ServicioCategorias;
import com.ares.clienteservidor.servicios.ServicioLibros;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/")
public class LibrosController {

	@Autowired
	private ServicioLibros servicioLibros;
	
	@Autowired
	private ServicioCategorias servicioCategorias;
	
	
	@RequestMapping("editarLibro")
	public String editarLibro( @RequestParam("id") Long id, Model model) {
		Libro libro = servicioLibros.obtenerLibroPorId(id);
		libro.setIdCategoria(libro.getCategoria().getId());
		model.addAttribute("libroEditar",libro);
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());		
		return "admin/libros_editar";
	}
	
	@RequestMapping("guardarCambiosLibro")
	public String guardarCambiosLibro(@ModelAttribute("libroEditar")Libro libroEditar, Model model) {
		servicioLibros.actualizarLibro(libroEditar);
		return obtenerLibros(model);
	}
	
	
	@RequestMapping("registrarLibro")
	public String registrarLibro(Model model) {
		Libro l = new Libro();
		l.setPrecio(1);
		model.addAttribute("xxx", l);
		//vamos a meter las categorias en model para que le lleguen a la vista
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		
		return "admin/libros_registro";
	}
	
	@RequestMapping("guardarLibro")
	public String guardarLibro(@ModelAttribute("xxx") @Valid Libro nuevoLibro, 
			BindingResult resultadoValidaciones, Model model ) {
		if(resultadoValidaciones.hasErrors()) {
			return "admin/libros_registro";
		}
		servicioLibros.registrarLibro(nuevoLibro);
		return obtenerLibros(model);
	}
	
	@RequestMapping("obtenerLibros")
	public String obtenerLibros(Model model) {		
		model.addAttribute("libros", servicioLibros.obtenerLibros());
		return "admin/libros";
	}
	
	@RequestMapping("borrarLibro")
	public String borrarLibro( @RequestParam("id") Long id, Model model ) {
		servicioLibros.borrarLibro(id);
		return obtenerLibros(model);
	}
	
}






