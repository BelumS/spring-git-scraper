FROM amazoncorretto:11-alpine-jdk
MAINTAINER Bel Sahn
COPY build/libs/spring-git-scraper-1.0.0.jar spring-git-scraper-1.0.0.jar
ENTRYPOINT ["java","-jar","/spring-git-scraper-1.0.0.jar"]