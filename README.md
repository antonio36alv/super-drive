# Super Drive

### GitHub username: antonio36alv

![Spring Boot Badge](https://img.shields.io/badge/Java-Spring%20Boot-green)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![Thymeleaf Badge](https://img.shields.io/badge/Spring%20MVC-Thymeleaf-green)

[Deployed Website](https://super-drive.herokuapp.com/) - Hosted on Heroku which goes into sleep mode if website hasn't been used for 30 minutes, so you may have to give it a bit to come up.

[My GitHub](https://github.com/antonio36alv)

<!-- add main screenshot here -->

<!-- add video demo here -->

## Description

A project that I built as part of my Udacity Java Web Developer course. For specifications/instructions you can find them [here](./Instructions.md) or in the Instructions.md file.
This website is built with Spring Boot and Thymeleaf. It allows a user to store files, notes, and login credentials for other websites. After the project was completed I made some changes
to my liking. The instructions called for an encrypted password to be shown, when viewing all the passwords stored. I changed it to appear as a hidden password (dots instead of alphanumeric characters
and symbols). Then naturally, I added a way for the user to show/hide the password and a button to copy it. I also added a way for when a user returns to the home page (where files, notes and credentials
are stored) the last tab they were on automatically displays.

<!-- - Pages are restricted according to whether or not a user is logged in -->

<!-- TODOs -->
<!-- add main screenshot -->
<!-- add video demo -->
<!-- table of contents, if necessary if necessary -->

## Installions/Requirements

- Java 11+

- Maven

- All the dependencies included within the [pom.xml](./pom.xml) file. IntelliJ is my preferred IDE, which takes care of importing dependencies present in the pom.xml.

To run tests you will also need:

- Geckodriver 

- Firefox


### Features

- Spring MVC

- Thymeleaf

- Spring Security

- MySQL

- MyBatis (ORM)

- Credential Storage with password encryption

- User Authentication - passwords are hashed and salted

### Tests

You can run tests via your IDE or with Maven CLI with:

```mvn clean test```