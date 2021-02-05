package com.cpms.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	 @Bean
	    public Docket productApi() {
	        return new Docket(DocumentationType.SWAGGER_2).select()
	                .apis(RequestHandlerSelectors.any())//
	                .paths(Predicates.not(PathSelectors.regex("/error")))//
	                .build()//
	                .apiInfo(metaInfo())//
	                .useDefaultResponseMessages(false)//
	                .securitySchemes(Collections.singletonList(apiKey()))
	                .securityContexts(Collections.singletonList(securityContext()))
	                .tags(new Tag("users", "Operations about users"))//
	                .genericModelSubstitutes(Optional.class);
	    }

	    private ApiInfo metaInfo() {
	    	ApiInfo apiInfo = new ApiInfoBuilder()
	                .title("CDAC Project Management System")
	                .description( "Project Management WebApp for CDAC, Pune")//
	                .version("1.0.0")//
	                .license("CDAC License").licenseUrl("https://cdac.in/")//
	                .contact(new Contact("Group09", "https://cpms.com/login", "cdacgroup10.project@gmail.com"))//
	                .build();
	        return apiInfo;
	    }
	    
	    private ApiKey apiKey() {
	        return new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, "header");
	      }

	      private SecurityContext securityContext() {
	        return SecurityContext.builder()
	            .securityReferences(defaultAuth())
	            .forPaths(PathSelectors.any())
	            .build();
	      }

	      List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope
	            = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        return Arrays.asList(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
	      }
	    
}
