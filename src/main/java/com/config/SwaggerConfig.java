package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
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
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    OverrideSwaggerClasses overrideSwaggerClasses;

    public SwaggerConfig(OverrideSwaggerClasses overrideSwaggerClasses ) {
        this.overrideSwaggerClasses = overrideSwaggerClasses;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))   // for selecting what all controllers to use
                .paths(PathSelectors.ant("/users/**"))  // for selecting methods to be included in Documentation
                .build();
    }

    private ApiInfo apiInfo () {
        return new ApiInfoBuilder()
                .title("Spring boot 2.2.1 work")
                .description("spring boot 2.2.1 description")
                .version("2.0")
                .license("my license")
                .licenseUrl("http://www.google.co.in")
                .contact(new Contact("Sunny", "http://www.google.co.in", "testing@mailinator.com"))
                .build();
    }
    // Swagger Metadata : http://localhost:8080/v2/api-docs
    // Swagger UI Url : http://localhost:8080/swagger-ui.html

}
