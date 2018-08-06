/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

/**
 *
 * @author Reza Dwi Saputra <reza.dwi@bni.co.id>
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;

@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        //Adding userid header
        ParameterBuilder headerUseridBuilder = new ParameterBuilder();
        headerUseridBuilder.name("userid").modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        //Adding sessionid header
        ParameterBuilder headerSessionidBuilder = new ParameterBuilder();
        headerSessionidBuilder.name("sessionid").modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        //Adding Authorization header
        ParameterBuilder headerAuthorizationBuilder = new ParameterBuilder();
        headerAuthorizationBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        List<Parameter> listCredentialHeader = new ArrayList<Parameter>();
        listCredentialHeader.add(headerUseridBuilder.build());
        listCredentialHeader.add(headerSessionidBuilder.build());
        listCredentialHeader.add(headerAuthorizationBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ihcs.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(
                        RequestMethod.GET,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(500)
                                        .message("500 message")
                                        .responseModel(new ModelRef("Error"))
                                        .build(),
                                new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build()))
                .globalOperationParameters(listCredentialHeader);
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "IHCS API", "Confidential for personal purposed only.",
                "API TOS", "Terms of service will follow later",
                "infohct@bni.co.id",
                "License of API",
                "infohct@bni.co.id");
        return apiInfo;
    }
}
