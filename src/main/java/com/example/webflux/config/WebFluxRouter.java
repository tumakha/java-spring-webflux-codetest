package com.example.webflux.config;

import com.example.webflux.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Yuriy Tumakha
 */
@Configuration
public class WebFluxRouter {

    @Bean
    RouterFunction<ServerResponse> routerFunction(CustomerHandler customerHandler) {
        return RouterFunctions
                .route(POST("/v1/customer/sort").and(accept(APPLICATION_JSON)), customerHandler::sort)
                .and(route(GET("/"), req ->
                        ServerResponse.temporaryRedirect(URI.create("/swagger-ui/index.html")).build()));
    }

}
