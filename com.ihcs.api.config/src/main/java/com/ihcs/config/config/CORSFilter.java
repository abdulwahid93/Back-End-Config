/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class CORSFilter extends OncePerRequestFilter {

    private Properties prop = new Properties();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        String allowedOrigins = "*";
//
//        if(request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
//            String originHeader = request.getHeader("Origin");
//
//            if(allowedOrigins.contains(request.getHeader("Origin")))
	response.addHeader("Access-Control-Allow-Origin", "*");

	response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	response.addHeader("Access-Control-Allow-Headers", "userid,sessionid,personid,Content-Type");
	response.addHeader("Access-Control-Max-Age", "1800");
//        }

	filterChain.doFilter(request, response);
    }

}
