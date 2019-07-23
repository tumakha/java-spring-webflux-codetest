package com.example.webflux.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author Yuriy Tumakha
 */
@Data
public class Customer {

  private Long id;

  private String name;

  @NotNull
  private ZonedDateTime duetime;

  private ZonedDateTime jointime;

}
