# Demo Rest API

## Prerequisites
- **Running with Docker**
    - Docker 24.x
    - Docker Compose 2.x
- **Running with Maven**
    - JDK 17+
    - Apache Maven 3.x
    - MySQL 8.x

## Configuration
- __src__
    - __main__
	    - __recources__
		    - __application.properties__ *-> App level configs (logger, scheduler, cache)* 
- __.env__ *-> MySQL connection configs*
   


## How to Run
- **With Using Docker**
    - Configure the ```.env``` and ```application.properties``` files    
    - Execute ```docker compose up```

- **Without Using Docker**
    - Configure the ```application-dev.properties``` and ```application.properties``` files 
    - Execute ```mvn clean install``` to build the application
    - Execute ```mvn spring-boot:run``` to run the application

## Todo
- Unit Tests