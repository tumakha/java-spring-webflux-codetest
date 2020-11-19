package com.example.webflux.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer.ServerDefaultCodecs;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;


/**
 * @author Yuriy Tumakha
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    WebFluxConfigurer webFluxConfigurer(ObjectMapper objectMapper) {
        return new WebFluxConfigurer() {
            @Override
            public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
                ServerDefaultCodecs defaultCodecs = configurer.defaultCodecs();
                defaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                defaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/swagger-ui/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
            }
        };
    }

}
