/**
 * Take https://news.ycombinator.com
 * turn into json format for n posts
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Slf4j
public class NewsScraper {

    public static void main(String args[]) {
        int resultsToGet;
        if (args.length >= 2 && args[0].equals("--posts")) {
            try {
                resultsToGet = Integer.parseInt(args[1]);
            } catch (Exception e) {
                log.error(Arrays.toString(args));
                System.out.println("ERROR: Please pass a number as the second command line argument");
                return;
            }
            if (args.length == 3 && args[2].equals("debug")){
                Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
                root.setLevel(Level.DEBUG);
            } else {
                Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
                root.setLevel(Level.OFF);
            }
        } else {
            System.out.println("ERROR: Please pass the second command line arguments: --posts n");
            System.out.println("Where n is the number of posts requested in the inclusive range 1-100");
            return;
        }
        if (0 <= resultsToGet && resultsToGet <= 100) {
            log.debug("Start NewsScraper");
            Scraper scraper = new Scraper("https://news.ycombinator.com/");
            ArrayList<Post> results = scraper.scrape(resultsToGet, 1);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                System.out.print(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("Number of posts requested must be greater than 0 and less than 100");
        }
    }
}