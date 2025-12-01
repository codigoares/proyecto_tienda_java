package com.ares.clienteservidor.servicios;

import java.util.List;

import com.ares.clienteservidor.model.Usuario;


// en java empresarial hay muchos conceptos confusos
// en este caso se llama servicio al interfaz 
// que va a definir las operaciones posibles con usuarios
// casi igual que cuando hemos usado el paquete repository
// o el paquete daos 



public interface ServicioUsuarios {

	void registrarUsuario(Usuario usuario);
	
	List<Usuario> obtenerUsuarios();
	
	void borrarUsuario(int id);
	
	Usuario obtenerUsuarioPorId(int id);
	
	Usuario obtenerUsuarioPorEmail(String email);

	void actualizarUsuario(Usuario usuarioEditar);

	Usuario obtenerUsuarioPorEmailYpass(String email, String pass);
	
}
