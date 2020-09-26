# Super Drive

[Deployed Website](https://super-drive.herokuapp.com/)

[My GitHub](https://github.com/antonio36alv)

## Description

A project that I built as part of my Udacity Java Web Developer course. For specifications/instructions you can find them [here](./Instructions.md) or in the Instructions.md file.
This website is built with Spring Boot and Thymeleaf. It allows a user to store files, notes, and login credentials for other websites.

<!-- table of contents here -->

## Installions/Requirements

- Java 11+

- Maven

- All the dependencies included within the [pom.xml](./pom.xml) file.

For tests you will also need geckodriver and Firefox.


### Features

- Thymeleaf

- Spring Security

- MySQL

- Credential Storage with password encryption

- User login passwords are hashed and salted

- Pages are restricted according to whether or not a user is logged in


### Tests

You can run tests via your IDE or with Maven CLI with:

```mvn clean test```

