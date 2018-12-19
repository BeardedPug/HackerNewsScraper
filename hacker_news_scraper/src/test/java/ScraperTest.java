import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScraperTest {
    private static Scraper testScraper;
    private static Element testTitleRow;
    private static Element testSubtextRow, testSubtextRow2, testSubtextRow3;

    @BeforeAll
    static void setup(){
        testScraper = new Scraper("https://news.ycombinator.com/");
        testTitleRow= new Element("<body>");
        testTitleRow.html("<tr class=\"athing\" id=\"18663860\">\n" +
                "<td align=\"right\" valign=\"top\" class=\"title\"><span class=\"rank\">1.</span></td>\n" +
                "<td valign=\"top\" class=\"votelinks\"><center><a id=\"up_18663860\" href=\"vote?id=18663860&amp;" +
                "how=up&amp;goto=news\"><div class=\"votearrow\" title=\"upvote\"></div></a></center></td><td " +
                "class=\"title\"><a href=\"https://www.namepros.com/blog/confirmed-duck-com-transfers-to-duckduckgo" +
                ".1113728/\" class=\"storylink\">Google transferred ownership of Duck.com to DuckDuckGo</a><span" +
                "class=\"sitebit comhead\"> (<a href=\"from?site=namepros.com\"><span class=\"sitestr\">namepros.com" +
                "</span></a>)</span></td></tr>");
        testSubtextRow = new Element("<body>");
        testSubtextRow.html("<tr><td colspan=\"2\"></td><td class=\"subtext\">\n" +
                "<span class=\"score\" id=\"score_18663860\">26 points</span> by <a href=\"user?id=rahiel\" " +
                "class=\"hnuser\">rahiel</a> <span class=\"age\"><a href=\"item?id=18663860\">36 minutes ago</a>" +
                "</span> <span id=\"unv_18663860\"></span> | <a href=\"hide?id=18663860&amp;goto=news\">hide</a> |" +
                " <a href=\"item?id=18663860\">46&nbsp;comments</a></td></tr>");
        testSubtextRow2 = new Element("<body>");
        testSubtextRow2.html("<tr><td colspan=\"2\"></td><td class=\"subtext\">\n" +
                "<span class=\"score\" id=\"score_18663860\">1 point</span> by <a href=\"user?id=rahiel\" " +
                "class=\"hnuser\">rahiel</a> <span class=\"age\"><a href=\"item?id=18663860\">36 minutes ago</a>" +
                "</span> <span id=\"unv_18663860\"></span> | <a href=\"hide?id=18663860&amp;goto=news\">hide</a> | " +
                "<a href=\"item?id=18663860\">1&nbsp;comment</a></td></tr>");
        testSubtextRow3 = new Element("<body>");
        testSubtextRow3.html("<tr><td colspan=\"2\"></td><td class=\"subtext\">\n" +
                "<span class=\"score\" id=\"score_18663860\">26 points</span> by <a href=\"user?id=rahiel\" " +
                "class=\"hnuser\">rahiel</a> <span class=\"age\"><a href=\"item?id=18663860\">36 minutes ago</a>" +
                "</span> <span id=\"unv_18663860\"></span> | <a href=\"hide?id=18663860&amp;goto=news\">hide</a> | " +
                "<a href=\"item?id=18663860\">discuss</a></td></tr>");
    }

    @Test
    void scrape() {
        //Test less than 30 and more than 30 as it has to go multiple pages
        ArrayList<Post> returnedPosts = testScraper.scrape(10, 1);
        assertEquals(10, returnedPosts.size());
        returnedPosts = testScraper.scrape(72, 1);
        assertEquals(72, returnedPosts.size());
    }

    @Test
    void getTitle() {
        assertEquals("Google transferred ownership of Duck.com to DuckDuckGo", testScraper.getTitle(testTitleRow));
    }

    @Test
    void getUrl() {
        assertEquals("https://www.namepros.com/blog/confirmed-duck-com-transfers-to-duckduckgo.1113728/", testScraper.getUrl(testTitleRow));
    }

    @Test
    void getAuthor() {
        assertEquals("rahiel", testScraper.getAuthor(testSubtextRow));
    }

    @Test
    void getPoints() {
        assertEquals(26, testScraper.getPoints(testSubtextRow));
        assertEquals(1, testScraper.getPoints(testSubtextRow2));
    }

    @Test
    void getComments() {
        assertEquals(46, testScraper.getComments(testSubtextRow));
        assertEquals(1, testScraper.getComments(testSubtextRow2));
        assertEquals(0, testScraper.getComments(testSubtextRow3));
    }

    @Test
    void getRank() {
        assertEquals(1, testScraper.getRank(testTitleRow));
    }
}