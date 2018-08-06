/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

import com.ihcs.api.security.OAuth2SecurityConfiguration;
import com.ihcs.api.security.ResourceServerConfiguration;
import com.ihcs.api.security.MethodSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = {"com.ihcs"})
@EnableWebMvc
@EnableAsync
@Import(value = {RepositoryConfig.class, SwaggerConfiguration.class, ResourceServerConfiguration.class, OAuth2SecurityConfiguration.class, MethodSecurityConfig.class})


public class MvcApplication extends WebMvcConfigurerAdapter {

    @Autowired
    private IhcsInterceptor interceptor;

    @Bean
    public ViewResolver viewResolver() {
        System.out.println("**********MVC CONFIG**********");
        InternalResourceViewResolver vr = new InternalResourceViewResolver();

        vr.setViewClass(JstlView.class);
        vr.setPrefix("/WEB-INF/view/");
        vr.setSuffix(".jsp");

        return vr;
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver getMultipartResolver() {
        CommonsMultipartResolver o = new CommonsMultipartResolver();
        o.setMaxUploadSize(1024 * 1024 * 10);

        return o;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

	registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Bean
    public WebHandlerExceptionResolver exceptionResolver() {
        return new WebHandlerExceptionResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(interceptor)
		.addPathPatterns("/**")
		;

	//registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }
//    @Bean
//    public Docket mainConfig(){
//	return new Docket(DocumentationType.SWAGGER_2)
//		.select().apis(RequestHandlerSelectors.any())
//		.paths(PathSelectors.any())
//		.build()
//		.pathMapping("/")
//		.directModelSubstitute(LocalDate.class, String.class)
//		.genericModelSubstitutes(ResponseEntity.class);
//			
//    } 
//    

}
