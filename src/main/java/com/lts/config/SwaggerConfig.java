package com.lts.config;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
@ComponentScan("com.lts.controller")
@Api(value = "api")
public class SwaggerConfig {


    private ApiInfo apiInfo() {
        final Contact contact = new Contact("LITE Technologies Software", "", "office@litesoftware.ro");
        return new ApiInfoBuilder()
                .title("Lite")
                .description("Lite application")
                .contact(contact)
                .build();
    }

    @Bean
    public Docket administrationApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Administration")
                .apiInfo(apiInfo())
                .select()
                .paths(or(
                        PathSelectors.ant("/api/v1/admin/**"),
                        PathSelectors.ant("/api/v1/auth/**"),
                        PathSelectors.ant("/api/v1/errors/**"),
                        PathSelectors.ant("/api/v1/company/**"),
                        PathSelectors.ant("/api/v1/activities/**"),
                        PathSelectors.ant("/api/v1/users/**"),
                        PathSelectors.ant("/api/v1/employees/**")))
                .build();
    }
}
