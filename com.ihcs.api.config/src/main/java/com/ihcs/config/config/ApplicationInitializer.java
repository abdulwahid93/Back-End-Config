/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
	System.out.println("**********INIT CONFIG**********");
	AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

	rootContext.register(MvcApplication.class);
	rootContext.setServletContext(sc);

	rootContext.refresh();

	DispatcherServlet mvcServlet = new DispatcherServlet(rootContext);
	ServletRegistration.Dynamic appServlet = sc.addServlet("appServlet", mvcServlet);

	appServlet.setLoadOnStartup(1);
	appServlet.addMapping("/");

	OpenSessionInViewFilter OFMSessionFilter = new OpenSessionInViewFilter();
	OpenSessionInViewFilter PRODHRSessionFilter = new OpenSessionInViewFilter();

	OFMSessionFilter.setSessionFactoryBeanName("OFMSessionFactory");
	PRODHRSessionFilter.setSessionFactoryBeanName("PRODHRSessionFactory");

	EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

	FilterRegistration.Dynamic OFMSessionInView = sc.addFilter("OFMSessionInView", OFMSessionFilter);
	OFMSessionInView.addMappingForUrlPatterns(dispatcherTypes, true, "/");

	FilterRegistration.Dynamic PRODHRSessionInView = sc.addFilter("PRODHRSessionInView", PRODHRSessionFilter);
	PRODHRSessionInView.addMappingForUrlPatterns(dispatcherTypes, true, "/");

	FilterRegistration.Dynamic corsFilter = sc.addFilter("corsFilter", CORSFilter.class);
	corsFilter.addMappingForUrlPatterns(null, false, "/*");

	sc.addListener(new ContextLoaderListener(rootContext));
	sc.setInitParameter("defaultHtmlEscape", "true");

	System.out.println("**********INIT CONFIG Done**********");

    }

////	private WebApplicationContext getContext() {
////		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
////        context.setConfigLocation("eu.kielczewski.example.config");
////        Properties prop = PropertiesLoader.load("hcms.properties");
////        context.getEnvironment().setActiveProfiles(prop.getProperty("spring.profiles.active", "dev"));
////        return context;
////    }
}
