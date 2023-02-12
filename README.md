# GitScraper
An app that scrapes user data from GitHub.

## Table of contents
- [GitScraper](#gitscraper)
  - [Table of contents](#table-of-contents)
  - [Overview](#overview)
    - [The challenge](#the-challenge)
    - [UML Diagram](#uml-diagram)
    - [Architecture Diagram](#architecture-diagram)
    - [Screenshot](#screenshot)
  - [My process](#my-process)
    - [Built with](#built-with)
    - [How to use the app](#how-to-scrape-data)
    - [What I learned](#what-i-learned)
    - [Useful resources](#useful-resources)
  - [Author](#author)

## Overview

### The challenge
Users should be able to:
- Retrieve API data about a GitHub user by pinging a REST endpoint.
- See the user data displayed as JSON.

## UML Diagram
![](./src/main/resources/static/images/uml-diagram.png)

## Architecture Diagram
![](./src/main/resources/static/images/architecture-diagram.png)

## Screenshot
![](./src/main/resources/static/images/screenshot.png)

## My process
### Built With
* Spring Boot v2.7.7
  * Spring Web
  * Spring Cache
  * Spring Retry v1.3.2
  * Spring AOP (required dependency for Retry)
* Java 11
* Project Lombok 
* Testing
  * JUnit 5
  * AssertJ v3.24.1
* Springfox API Documentation v3.0.0
* GitHub REST API

### How to Scrape Data
1. Start the app using the `./gradlew spring-boot:run` command
2. Ping the REST endpoint with command: `curl -v localhost:8080/scraper/git/{username}`, or use Postman.
     * Replace `{username}` with a valid Github username `String`
3. The endpoint will return your desired user data as JSON.

### What I Learned

I learned how to cache the data.

```java
public class GithubServiceImpl implements GithubService {
  String s = "";
}
```

### Useful resources
- [Baeldung - Spring Cache](https://www.baeldung.com/spring-cache-tutorial) - I learned how to use the Spring Cache module.
- [Baeldung - Spring Retry](https://www.baeldung.com/spring-retry) - I learned how to use the Spring Retry module.
- [GitHub REST API - Quickstart](https://docs.github.com/en/rest/quickstart?apiVersion=2022-11-28) - This helped me learn about the Github REST API.
- [GitHub REST API - Get User](https://docs.github.com/rest/reference/users#get-a-user) - Reference docs to get a user from GitHub.
- [GitHub REST API - Get Repos for User](https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user) - Reference docs to get a user's repos from GitHub.

## Author
- GitHub - [@BelumS](https://github.com/BelumS/spring-git-scraper)
