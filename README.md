# Maze Generator & Solver

**This project provides Java implementations of Deep First Search algorithm for generating so called "perfect mazes".**  

<img width=250px src='https://github.com/igushkin/maze/blob/master/examples/example.png'>

## Explanation

The algorithm starts at a given cell and marks it as visited. It selects a random neighboring cell that hasn’t been visited yet and makes that one the current cell, marks it as visited, and so on.

If the current cell doesn’t have a neighbor that hasn’t been visited yet, we move back to the last cell with a not-visited neighbor.

The algorithm finished when there is no not-visited cell anymore.

*The algorithm is recursive and might cause memory issues for big mazes.* 

## Built With

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Spring Boot](https://github.com/spring-projects/spring-boot)
* [JQuery](https://github.com/jquery/jquery)

## Getting Started
### Prerequisites
This application requires JDK 17 to run. 

1. Dowload the source

```
git clone https://github.com/igushkin/maze.git
```

2. Build the project by running

```
mvn clean package
```

3. Execute the JAR file by running:

```
java -jar target/mvn-example-1.0-SNAPSHOT.jar
```

4. Open client/index.html in the browser
