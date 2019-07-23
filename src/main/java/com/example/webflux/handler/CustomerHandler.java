package com.example.webflux.handler;

import com.example.webflux.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

/**
 * @author Yuriy Tumakha
 */
@Component
public class CustomerHandler {

  @Autowired
  private Validator validator;

  public Mono<ServerResponse> sort(ServerRequest request) {
    Flux<Customer> customers = request.bodyToFlux(Customer.class);
    Flux<Customer> customersSorted = sortByDueTime(customers);

    return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
        .body(customersSorted, Customer.class);
  }

  private Flux<Customer> sortByDueTime(Flux<Customer> customers) {
    return customers.filter(this::validate).sort(comparing(Customer::getDuetime));
  }

  private boolean validate(Customer customer) {
    Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
    if (!constraintViolations.isEmpty()) {
      String message = constraintViolations.stream().map(v -> v.getPropertyPath() + " " +  v.getMessage())
          .collect(joining(". "));
      throw new ResponseStatusException(UNPROCESSABLE_ENTITY, message);
    }
    return true;
  }

}
