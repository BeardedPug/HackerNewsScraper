# HackerNewsScraper
A simple command line application that outputs to STDOUT the top posts from https://news.ycombinator.com in JSON. 

## TO RUN

### Method 1 from Docker Image (Google drive link):
- Download and setup docker from [here](https://www.docker.com/get-started)
- Download docker image [here](https://drive.google.com/file/d/1R5MYrWh_ijlEdRZeZLZJy5wgpx6EPT6B/view?usp=sharing)
- Open the terminal
- cd to location of newscraper.tar
- Load docker image: ```docker load --input newscraper.tar```
- Run docker container: ```docker run -i -t newsscraperimg /bin/bash```
- Now you are in the docker container you can use: ```newsscraper --posts n``` where n is a positive integer <=100

### Method 2
- Install git: [instructions](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- Make a github account (if you don't have one already)
- Clone this repository: ```git clone https://github.com/BeardedPug/HackerNewsScraper.git```
- Download and setup docker from [here](https://www.docker.com/get-started)
- Install gradle from [here](https://gradle.org)
- Open the terminal
- cd into HackerNewsScraper/hacker_news_scraper
- Run ```gradle build```
- Run ```gradle fatjar```
- Create docker image: ```docker build -t newsscraperimg .```
- Run docker container: ```docker run -i -t newsscraperimg /bin/bash```
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
