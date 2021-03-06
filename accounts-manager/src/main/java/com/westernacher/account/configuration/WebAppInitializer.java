package com.westernacher.account.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletRegistration.Dynamic;  

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();  
        ctx.register(AccountsConfiguration.class);  
        ctx.setServletContext(servletContext); 
        DispatcherServlet servlet = new DispatcherServlet(ctx);
        Dynamic dynamic = servletContext.addServlet("dispatcher", servlet);  
        dynamic.addMapping("/");  
        dynamic.setLoadOnStartup(1);  
    
	}

}
