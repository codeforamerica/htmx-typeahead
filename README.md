# Htmx typeahead spike

This repository is a Spring Boot MVC application with [htmx](https://htmx.org/) and [Spring Boot htmx helpers](https://github.com/wimdeblauwe/htmx-spring-boot).

## Goal of this project

Can we request and show large number of data without a client side heavy JavaScript solution?

## Htmx request

There's a specific `HxRequest` called `/search` that takes a query and returns `HTML`. This allows for the filtering
logic of providers to happen fully within Java and only the resulting `HTML` needs to be returned to the client.

## Speed

It can send ~8000 lines of `HTML` in ~120ms at high speed internet and ~1.1s on "Good 3g".

