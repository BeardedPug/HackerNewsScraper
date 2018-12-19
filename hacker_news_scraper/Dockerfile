FROM java:8
COPY build/libs/newsScraper-1.0.jar /scraper/
WORKDIR scraper
RUN echo 'alias newsscraper="java -jar newsScraper-1.0.jar"' >> ~/.bashrc
RUN java -jar newsScraper-1.0.jar --posts 1