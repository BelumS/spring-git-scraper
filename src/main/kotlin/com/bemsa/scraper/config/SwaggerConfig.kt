package com.bemsa.scraper.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.*
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ParameterType
import springfox.documentation.service.RequestParameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Value("\${spring.application.name}")
    private lateinit var appName: String

    @Value("\${spring.application.description}")
    private lateinit var appDescription: String

    @Value("\${api.version.one}")
    private lateinit var apiV1Path: String

    @Bean
    fun apiV1(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("v1")
                .globalRequestParameters(listOf(apiParam()))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(apiV1Path))
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title(appName)
                .description(appDescription)
                .build()
    }

    private fun apiParam(): RequestParameter {
        return RequestParameterBuilder()
                .name("Test")
                .description("Test")
                .`in`(ParameterType.HEADER)
                .deprecated(false)
                .required(false)
                .build()
    }
}
