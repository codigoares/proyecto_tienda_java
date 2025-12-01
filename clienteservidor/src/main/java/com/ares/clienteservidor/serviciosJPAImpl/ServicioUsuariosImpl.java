package com.ares.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ares.clienteservidor.model.Usuario;
import com.ares.clienteservidor.servicios.ServicioUsuarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
@Transactional
public class ServicioUsuariosImpl implements ServicioUsuarios {
	//vamos a implementar esta clase usando JPA
	
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarUsuario(Usuario usuario) {
		entityManager.persist(usuario);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		// ahora en JPA no hay criteria
		// para obtener datos y hacer consultas:
		// SQL nativo 
		// JPQL -> pseudo SQL, muy sencillo, devuelve objetos
		List<Usuario> usuarios = 
				entityManager.createQuery(
						"select u from Usuario u").getResultList();
		return usuarios;
	}

	@Override
	public void borrarUsuario(int id) {
		Usuario usuarioAborrar = obtenerUsuarioPorId(id);
		if (usuarioAborrar != null) {
			entityManager.remove(usuarioAborrar);					
		}
	}

	@Override
	public Usuario obtenerUsuarioPorId(int id) {
		return entityManager.find(Usuario.class, id);
	}

	@Override
	public void actualizarUsuario(Usuario usuarioEditar) {
		entityManager.merge(usuarioEditar);		
	}

	@Override
	public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
		try {
			Usuario usuario = 
					(Usuario)
					entityManager.createQuery(
						"select u from Usuario u where u.email = :email and u.pass = :pass ").
						setParameter("email", email).
						setParameter("pass", pass).getSingleResult();
			return usuario;
		} catch (Exception e) {
			System.out.println("no se encontro un usuario con el email y pass indicado");
		}
		return null;
	}

	@Override
	public Usuario obtenerUsuarioPorEmail(String email) {
		try {
			Usuario usuario = 
					(Usuario)
					entityManager.createQuery(
						"select u from Usuario u where u.email = :email").
						setParameter("email", email).getSingleResult();
			return usuario;
		} catch (Exception e) {
			System.out.println("no hay un usuario con el email indicado");
		}
		return null;
	}
	
}
