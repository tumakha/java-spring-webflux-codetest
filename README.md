# Qudini Backend Software Engineer Code Test

**Please _fork_ this repository rather than creating a PR.**

To test how quickly you can pick up a new project and follow requirements, we ask
candidates to do the following project. You have a choice of web frameworks:

* Spring WebFlux
* Spring MVC

This repository is the Spring WebFlux test. If you want to use Spring MVC
instead, fork the this repository instead:
(https://github.com/qudini/java-spring-mvc-codetest)


Meet the following requirements:

* Fork this project.
* Start project in the chosen framework.
* Provide an API which accepts a list of JSON `Customer` objects in the body of
  a POST request (see the JSON example attached). You can create this request
  via whatever tool you prefer, whether it's curl, Postman, a Spring webclient,
  or something else.
* The API should take this list of objects and sort them by `duetime` from
  oldest to newest and return it back as a sorted JSON array.
* Use Java 8's `datetime` package or Joda time (http://www.joda.org/joda-time/)
  library to handle times with timezones.
* The API should be non-blocking and be as efficient as possible in its sorting.
* We'll test this by load testing the project with a few hundred users to see
  how it performs. (If you have time try using JMeter to test your
  implementation.)

Bonus point:
* Add some tests to ensure your code works as expected. Is it better to test the
  controller or test the service layer directly? Either approach is valid so
  long as you can justify it.
