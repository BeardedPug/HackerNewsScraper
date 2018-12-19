import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.jsoup.nodes.Element;
import static org.junit.jupiter.api.Assertions.*;

class ScraperTest {
    static Scraper testScraper;
    static Element testTitleRow;
    static Element testSubtextRow;

    @BeforeAll
    static void setup(){
        testScraper = new Scraper("https://news.ycombinator.com/");
        testTitleRow= new Element("<body>");
        testTitleRow.html("<tr class=\"athing\" id=\"18663860\">\n" +
                "<td align=\"right\" valign=\"top\" class=\"title\"><span class=\"rank\">1.</span></td>\n" +
                "<td valign=\"top\" class=\"votelinks\"><center><a id=\"up_18663860\" href=\"vote?id=18663860&amp;how=up&amp;goto=news\"><div class=\"votearrow\" title=\"upvote\"></div></a></center></td><td class=\"title\"><a href=\"https://www.namepros.com/blog/confirmed-duck-com-transfers-to-duckduckgo.1113728/\" class=\"storylink\">Google transferred ownership of Duck.com to DuckDuckGo</a><span class=\"sitebit comhead\"> (<a href=\"from?site=namepros.com\"><span class=\"sitestr\">namepros.com</span></a>)</span></td></tr>");
        testSubtextRow = new Element("<body>");
        testSubtextRow.html("<tr><td colspan=\"2\"></td><td class=\"subtext\">\n" +
                "<span class=\"score\" id=\"score_18663860\">26 points</span> by <a href=\"user?id=rahiel\" class=\"hnuser\">rahiel</a> <span class=\"age\"><a href=\"item?id=18663860\">36 minutes ago</a></span> <span id=\"unv_18663860\"></span> | <a href=\"hide?id=18663860&amp;goto=news\">hide</a> | <a href=\"item?id=18663860\">4&nbsp;comments</a>" +
                "</td></tr>");
    }

    @Test
    void scrape() {

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
        assertEquals(26, testScraper.getTitle(testSubtextRow));
    }

    @Test
    void getComments() {
        assertEquals(46, testScraper.getPoints(testSubtextRow));
    }

    @Test
    void getRank() {
        assertEquals(1, testScraper.getRank(testTitleRow));
    }
}