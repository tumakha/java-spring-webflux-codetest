package com.example.webflux;

import com.example.webflux.test.concurrent.ConcurrentRun;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static java.net.HttpURLConnection.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.StreamUtils.copyToString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTest {

  @LocalServerPort
  private int serverPort;

  @Value("classpath:customers-req.json")
  private Resource customers;

  @Value("classpath:customers-response.json")
  private Resource customersResponse;

  @Value("classpath:customers-big-req.json")
  private Resource customersBig;

  @Value("classpath:customers-big-response.json")
  private Resource customersBigResponse;

  @Before
  public void setUp() {
    RestAssured.port = serverPort;
  }

  @Test
  public void testSortSmallList() {
    testCustomersSort(customers, customersResponse);
  }

  @Test
  public void testSortBigList() {
    testCustomersSort(customersBig, customersBigResponse);
  }

  @Test
  public void testSortEmptyList() {
    testCustomersSort("[ ]", "[]");
  }

  @Test
  public void testUnexpectedJsonFormat() {
    String jsonRequest = "[{},{}]";
    given().
        contentType(JSON).
        body(jsonRequest).
        post("/v1/customer/sort").
        then().
        contentType(JSON).
        statusCode(422).
        body("error", equalTo("Unprocessable Entity"));
  }

  @Test
  public void testInvalidJson() {
    String jsonRequest = "['not valid JSON']";
    given().
        contentType(JSON).
        body(jsonRequest).
        post("/v1/customer/sort").
        then().
        contentType(JSON).
        statusCode(HTTP_BAD_REQUEST).
        body("error", equalTo("Bad Request"));
  }

  @Test
  public void testUnknownPath() {
    given().
        when().
        get("/unknown/path").
        then().
        statusCode(HTTP_NOT_FOUND).
        body("error", equalTo("Not Found"));
  }

  @Test
  public void testParallelSort() {
    LocalDateTime startTime = LocalDateTime.now();

    new ConcurrentRun(this::testSortBigList).run(1000);

    Duration duration = Duration.between(startTime, LocalDateTime.now());
    System.out.println(format("1000 sort requests completed in %d s %d ms",
        duration.getSeconds(), duration.toMillis() % 1000));
  }

  private void testCustomersSort(Resource requestBody, Resource expectedResponse){
    testCustomersSort(getResourceAsString(requestBody), getResourceAsString(expectedResponse));
  }

  private void testCustomersSort(String requestBody, String expectedResponse) {
    String response = given().
        contentType(JSON).
        body(requestBody).
        post("/v1/customer/sort").
        then().
        contentType(JSON).
        statusCode(HTTP_OK).
        extract().body().asString();

    assertThatJson(response).isEqualTo(expectedResponse);
  }

  private String getResourceAsString(Resource resource) {
    try {
      return copyToString(resource.getInputStream(), UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
