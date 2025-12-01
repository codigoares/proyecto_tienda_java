package com.ares.clienteservidor.controllers.loginAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ares.clienteservidor.controllers.admin.LibrosController;

import jakarta.servlet.http.HttpServletRequest;

//controlador que se encarga de identificar a un admin

@Controller
public class LoginAdminController {
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LibrosController librosController;
	
	@RequestMapping("loginAdmin")
	public String loginAdmin() {
		return "admin/loginAdmin";
	}
	
	@RequestMapping("logoutAdmin")
	public String logoutAdmin(HttpServletRequest request){
		String idiomaActual = 
				messageSource.getMessage("idioma", null, LocaleContextHolder.getLocale());
		request.getSession().invalidate();
		System.out.println("idiomaActual: " + idiomaActual);
		return "tienda_" + idiomaActual;
	}
	
}
