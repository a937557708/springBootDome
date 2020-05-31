package com.tjr.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description: swagger配置
 * @Author Young
 * @Date 2019/7/23
 * @Version V1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * 方法1：直接扫描包名路径 方法2：在Application启动类@SpringBootApplication注解加 scanBasePackages =
	 * "com.young"
	 * 
	 * @return
	 *
	 */
	@Bean
	public Docket api() {
		 return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .pathMapping("/")
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(Predicates.not(PathSelectors.regex("/error.*")))
	                .paths(PathSelectors.regex("/.*"))
	                .build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("dome接口").description("Rest API接口").termsOfServiceUrl("http://127.0.0.1:8078")
				.version("1.0").build();
	}
}