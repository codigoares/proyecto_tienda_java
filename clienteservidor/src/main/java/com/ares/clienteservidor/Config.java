package com.ares.clienteservidor;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ares.clienteservidor.interceptores.InterceptorAdmin;

@Component
public class Config  implements WebMvcConfigurer{

	@Autowired
	private InterceptorAdmin interceptorAdmin;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorAdmin);
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
	@Bean
	private LocaleResolver localeResolver() {
		//aqui vamos a decir que para que un cambio de idioma se mantenga
		//en sesion, hay que hacer lo siguiente:
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.getDefault());
		return localeResolver;		
	}
	
	
	@Bean
	private LocaleChangeInterceptor localeChangeInterceptor() {
		//aqui vamos a decir como se va a disparar el cambio de idioma
		LocaleChangeInterceptor localeChangeInterceptor = 
				new LocaleChangeInterceptor();
		localeChangeInterceptor.setIgnoreInvalidLocale(true);
		localeChangeInterceptor.setParamName("idioma");
		return localeChangeInterceptor;
	}
	
}
