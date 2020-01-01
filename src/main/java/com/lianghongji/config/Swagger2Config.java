package com.lianghongji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket createManageApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("manageApi").apiInfo(manageApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.lianghongji.manageapi.controller.v1"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo manageApiInfo() {
		return new ApiInfoBuilder().title("通用后台接口文档")
				.contact(new Contact("梁洪基", "", "lianghj1997@qq.com"))
				.version("1.0")
				.build();
	}
}
