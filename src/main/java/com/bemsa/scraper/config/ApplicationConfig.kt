package com.bemsa.scraper.config;

import com.bemsa.scraper.constants.DATE_FORMAT
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

import java.text.SimpleDateFormat
import java.time.Duration

@Configuration
@EnableCaching
@EnableRetry
class ApplicationConfig {
    @Value("\${github.base.url}")
    private lateinit var githubUrl: String

//    @Bean
//    fun webClient(webClient: WebClient): WebClient {}

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
                .rootUri(githubUrl)
                .setConnectTimeout(Duration.ofMillis(10000))
                .setReadTimeout(Duration.ofMillis(10000))
                .build();
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.dateFormat = SimpleDateFormat(DATE_FORMAT);
        mapper.registerModule(JavaTimeModule());
        return mapper;
    }

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(listOf(
            ConcurrentMapCache("users"),
            ConcurrentMapCache("repos"))
        );
        return cacheManager
    }
}
