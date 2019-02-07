# Qudini Backend Software Engineer Code Test

**Please _fork_ this repository rather than creating a PR.**

To test how quickly you can pick up a new project and follow requirements, we ask
candidates to do the following project. You have a choice of web frameworks:

* Spring WebFlux
* Spring MVC

This repository is the Spring WebFlux test. If you want to use Spring MVC instead, fork
the this repository instead: (https://github.com/qudini/java-spring-mvc-codetest)

Meet the following requirements:

* Fork this project.
* Start project in the chosen framework.
* Provide an API which accepts a list of JSON 'customer' objects in the body of
  a POST request (see the JSON example attached).
* The API should take this list of objects and sort them by due time then return
  this back as a sorted JSON array.
* Should use Java 8's `datetime` package or Joda time
  (http://www.joda.org/joda-time/) library to handle times with timezones.
* The API should be non-blocking and be as efficient as possible in its sorting.
* We'll test this by load testing the project with a few hundred users to see
  how it performs (if you have time try using JMeter to test your
  implementation).

Bonus points:

* Some tests to ensure your code is working as expected
* An API is great, but how about adding some UI for easy upload of the file.
