<br />
<p align="center">

<h3 align="center">Saga Design Pattern Demo</h3>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#technologies">Technologies</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#solution">Solution Design</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

Goal of this project is to demonstrate POC for Saga design pattern.

<!-- GETTING STARTED -->

## Getting Started

How to build and run application

### Prerequisites

To build and run the application, you need to have installed tools/libraries

1. Java 8
   JDK [https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
2. Maven 3.8.1 [https://maven.apache.org/](https://maven.apache.org/)

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/nhatquanghoe/saga-demo
   ```
2. Navigate to project' root directory
   ```sh
   cd saga-demo
   ```
3. Build and run the application
   ```sh
   mvn spring-boot:run
   ```

<!-- Technologies -->

## Technologies

1. Backend: Java 8, Spring Boot - including Spring Data JPA, Hibernate
2. Database: embedded Derby
3. Message Broker: embedded ActiveMQ

<!-- USAGE EXAMPLES -->

## Usage

### API documentation

After launching application successfully, full API documentation can be found at

- http://localhost:8080/swagger-ui/
- http://localhost:8080/v2/api-docs

### Example

A step by step example of Curl commands to run full order flow is provided here [Example](docs/CurlCommandExample.md)


<!-- SOLUTION DETAILS -->

## Solution

Solution details are described in [Solution Details](docs/SolutionDetails.md)
