/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Andri Setiady
 */
@Configuration

//@PropertySource("classpath:hcms.properties")
//@PropertySource(value={"file:/d:/hcms.properties","classpth:db.properties"})
//@PropertySource(value = {"file:${catalina.home}/properties/api.properties", "classpath:db.properties"},ignoreResourceNotFound = true)
//@PropertySource(value={"classpath:db.properties"})

@PropertySources({
@PropertySource(value = "file:${catalina.home}/properties/api.properties", ignoreResourceNotFound=true),
@PropertySource("classpath:db.properties")
        })

@ComponentScan

public class PropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	System.out.println("**********PROPERTY CONFIG**********");
	return new PropertySourcesPlaceholderConfigurer();

//		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//        c.setLocation(new ClassPathResource(file:#{contextAttributes['config-filename']);
//        return c;
    }

}
