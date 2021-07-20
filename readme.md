# Java_Springboot_FullStack_Blog_APP



## Running Spring Boot Crud locally
BookApp is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:

### Technologies
The following items  have been  used for this project:
* Spring Boot.
* Spring Data JPA & Hibernate
* Thymleaf&Mysql JDBC Driver

### Software Programms

* Java Development Kit(JDK).
* Spring Tool Suite IDE (sts)
* MYSQL database server & Workbench
```
https://github.com/sahin88/Java_Springboot_FullStack_Blog_APP
cd Java_Springboot_FullStack_Blog_APP
./mvnw package
java -jar target/*.jar
```

You can then access Java_Springboot_FullStack_Blog_APP here: http://localhost:8080/

<img width="720" alt="spring_crud_" src="https://github.com/sahin88/Java_Springboot_FullStack_Blog_APP/blob/main/app_1.png">
<img width="720" alt="spring_crud_" src="https://github.com/sahin88/Java_Springboot_FullStack_Blog_APP/blob/main/app_2.png">



Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

## Uml Diagramm
<img width="720" alt="spring_crud_" src="https://github.com/sahin88/Java_Springboot_FullStack_Blog_APP/blob/main/class_diagramm.png">



## Working with Spring Boot Crud in your IDE

### Prerequisites
The following items should be installed in your system:
* Java 8 or newer.
* git command line tool (https://help.github.com/articles/set-up-git)


### Steps:

1) On the command line
```
git clone https://github.com/sahin88/Java_Springboot_FullStack_Blog_APP
```
2) Inside Eclipse or STS
```
File -> Import -> Maven -> Existing Maven project
```

Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.





# License

Spring Boot Crud  sample application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[spring-petclinic]: https://github.com/spring-projects/spring-petclinic
[spring-framework-petclinic]: https://github.com/spring-petclinic/spring-framework-petclinic
[spring-petclinic-angularjs]: https://github.com/spring-petclinic/spring-petclinic-angularjs 
[javaconfig branch]: https://github.com/spring-petclinic/spring-framework-petclinic/tree/javaconfig
[spring-petclinic-angular]: https://github.com/spring-petclinic/spring-petclinic-angular
[spring-petclinic-microservices]: https://github.com/spring-petclinic/spring-petclinic-microservices
[spring-petclinic-reactjs]: https://github.com/spring-petclinic/spring-petclinic-reactjs
[spring-petclinic-graphql]: https://github.com/spring-petclinic/spring-petclinic-graphql
[spring-petclinic-kotlin]: https://github.com/spring-petclinic/spring-petclinic-kotlin
[spring-petclinic-rest]: https://github.com/spring-petclinic/spring-petclinic-rest
