package com.config;

import com.config.TypeNameExtractorReplaced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

@Configuration
public class OverrideSwaggerClasses {

    @Autowired private TypeNameExtractorReplaced typeNameExtractorReplaced;

    @Bean
    @Primary
    public DocumentationPluginsManager bean() {
        return new DocumentationPluginsManagerBootAdapter();
    }

    @Bean
    @Primary
    public TypeNameExtractor beanTypeName() {
        return typeNameExtractorReplaced;
    }
}