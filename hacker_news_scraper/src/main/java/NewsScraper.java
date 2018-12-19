/**
 * Take https://news.ycombinator.com
 * turn into json format for n posts
 */

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

@Slf4j
public class NewsScraper {

    public static void main(String args[]) {
        log.info("Start NewsScraper");
        int resultsToGet = 100;
        if (0 <= resultsToGet && resultsToGet <= 100) {
            Scraper scraper = new Scraper("https://news.ycombinator.com/");
            ArrayList<Post> results = scraper.scrape(resultsToGet, 1);
            log.info(results.toString());
        } else {
            System.out.print("Number of posts requested must be greater than 0 and less than 100");
        }
    }
}