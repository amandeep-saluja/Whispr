package com.chat.whispr.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfo("Whispr Chat Server",
                "Whispr Chat Backend Service",
                "1.0",
                "",
                new Contact("Whispr Dev team",
                        "https://github.com/amandeep-saluja/Whispr",
                        "amandeepsaluja25@gmail.com"),
                "MIT",
                "",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chat.whispr"))
                .paths(PathSelectors.any())
                .build();
    }
}
