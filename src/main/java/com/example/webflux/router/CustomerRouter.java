package com.example.webflux.router;

import com.example.webflux.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author Yuriy Tumakha
 */
@Configuration
public class CustomerRouter {

  @Bean
  public RouterFunction<ServerResponse> route(CustomerHandler customerHandler) {
    return RouterFunctions
        .route(POST("/v1/customer/sort").and(accept(APPLICATION_JSON)), customerHandler::sort);
  }

}
