# HackerNewsScraper
A simple command line application that outputs to STDOUT the top posts from https://news.ycombinator.com in JSON. 

## TO RUN

- Clone this repository: ```git clone https://github.com/BeardedPug/HackerNewsScraper.git```
- Download and setup docker from [here](https://www.docker.com/get-started)
- Open the terminal
- cd into HackerNewsScraper
- Load docker image: ```docker load --input newsscraper.tar```
- Run docker container: ```docker run -i -t cacbebe55e0f /bin/bash```
- Now you are in the docker container you can use: ```newsscraper --posts n``` where n is a positive integer <=100

## Technologies used
- [Gradle](https://gradle.org): Build tool for project and dependency management
- [Docker](https://www.docker.com): Containerisation utility
- [IntelliJ](https://www.jetbrains.com/idea/): IDE used

### Libraries used

- [slf4j](https://www.slf4j.org): Logging facility which works well with Lombok.
- [logback](https://logback.qos.ch): Needed for slf4j.
- [jsoup](https://jsoup.org): Used for parsing and traversing the html.
- [jackson](https://github.com/FasterXML/jackson): Standard JSON library for java to parse into JSON objects and pretty print.
- [lombok](https://projectlombok.org): Allows for much more concise code by automating some methods.
- [junit](https://junit.org/junit5/): Used to unit test the scraper.
