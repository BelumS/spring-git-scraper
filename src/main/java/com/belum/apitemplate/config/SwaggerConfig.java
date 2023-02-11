package com.belum.apitemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Created by bel-sahn on 7/29/19
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
//region PROPERTIES
//endregion

//region CONSTRUCTORS
//endregion

//region BEANS
    @Bean
    public Docket apiV1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("v1")
                .apiInfo(apiInfo())
                .globalOperationParameters(Collections.singletonList(apiParam()))
                .select()
                .paths(PathSelectors.ant("/api/v1/**"))
                .build();
    }

    @Bean
    public Docket apiV2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("v2")
                .apiInfo(apiInfo())
                .globalOperationParameters(Collections.singletonList(apiParam()))
                .select()
                .paths(PathSelectors.ant("/api/v2/**"))
                .build();
    }
//endregion

//region HELPER METHODS
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .build();
    }

    private Parameter apiParam() {
        return new ParameterBuilder()
                .name("")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
    }
//endregion
}
