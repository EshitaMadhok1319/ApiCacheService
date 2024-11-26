package com.cache.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*
 * This class have the Swagger2 Configuration
 * 
 * Developer: Eshita Madhok
 * 
 */

@Configuration
@EnableWebMvc
public class SwaggerConfiguration implements WebMvcConfigurer {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cache.service")).paths(PathSelectors.regex("/.*"))
				.build().apiInfo(apiInfoMetaData());
	}

	private ApiInfo apiInfoMetaData() {

		return new ApiInfoBuilder().title("Caching").description("API Endpoint")
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0")
				.build();
	}

}