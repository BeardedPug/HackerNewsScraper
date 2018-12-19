/*
    Scraper grabs to a page,
    parses to element list,
    runs through elements picking out posts,
    validates posts based on rules,
    if valid adds posts to results array
    recursively runs till, it runs out of pages
    or has found enough results
    then returns results as ArrayList<Post>
 */

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Slf4j
@AllArgsConstructor
class Scraper {
    String url;

    ArrayList<Post> scrape(int resultsToGet, int pageNum){
        ArrayList<Post> resultsFound = new ArrayList<>();
        if (resultsToGet > 0 && resultsToGet <= 100 && pageNum > 0 && pageNum < 17) { //found pageNum can be maximum of 16
            try {
                Document page = Jsoup.connect(url + "?p=" + pageNum).get();
                Elements table = page.body().getElementsByClass("itemList").get(0).getElementsByTag("tr");
                int ignoreConstant = 0;
                while (resultsFound.size() != resultsToGet) {
                    if ((resultsFound.size() + ignoreConstant) == 30) {
                        // 30 is maximum per page so recursively check pages
                        resultsFound.addAll(scrape((resultsToGet - resultsFound.size()), (pageNum + 1)));
                        return resultsFound;
                    } else {
                        Element titleRow = table.get((resultsFound.size() + ignoreConstant) * 3);
                        Element subTextRow = table.get((resultsFound.size() + ignoreConstant) * 3 + 1);
                        String title = getTitle(titleRow);
                        String articleUrl = getUrl(titleRow);
                        String author = getAuthor(subTextRow);
                        int points = getPoints(subTextRow);
                        int comments = getComments(subTextRow);
                        int rank = getRank(titleRow);
                        // Ignore row if one element is missing
                        if (title.equals("") || articleUrl.equals("") || author.equals("") || points == -1 || comments == -1 || rank == -1) {
                            log.debug("ignored row");
                            log.debug(titleRow.toString());
                            log.debug(subTextRow.toString());
                            log.debug(new Post(title, articleUrl, author, points, comments, rank).toString());
                            ignoreConstant++;
                        } else {
                            resultsFound.add(new Post(title, articleUrl, author, points, comments, rank));
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return resultsFound;
    }

    String getTitle(Element row){
        String title;
        try {
            title = row.getElementsByClass("storyLink").text();
        } catch (Exception e){
            title = "";
            log.error(e.getMessage());
        }
        if ( title.length() == 0){
            title = "";
        } else if (title.length() > 256){
            title = title.substring(0, 256);
        }
        return title;
    }

    String getUrl(Element row){
        String url;
        try {
            url = row.getElementsByClass("storyLink").attr("href");
            // Check validity of url, will catch exception if not valid
            try {
                new URI(url).parseServerAuthority();
            } catch (URISyntaxException e) {
                url = "";
            }
        } catch (Exception e) {
            url = "";
            log.error(e.getMessage());
        }
        return url;
    }

    String getAuthor(Element row){
        String author;
        try {
            author = row.getElementsByClass("hnuser").text();
        } catch (Exception e) {
            author = "";
            log.error(e.getMessage());
        }
        if ( author.length() == 0) {
            author = "";
        } else if (author.length() > 256) {
            author = author.substring(0, 256);
        }
        return author;
    }

    int getPoints(Element row){
        int points;
        try {
            String pointStr = row.getElementsByClass("score").text();
            pointStr = pointStr.replace("s", ""); // Get rid of plural first
            points = Integer.parseInt(pointStr.replace(" point", ""));
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return points;
    }

    int getComments(Element row){
        String comments;
        try {
            comments = row.getElementsByTag("a").get(3).text();
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        // Check fo different wordings for comment, e.g. is discuss if no comments
        if ( comments.contains("comment")) {
            comments = comments.replace(" comment", "");
            comments = comments.replace("s", ""); // Plural if comments more than one
            return Integer.parseInt(comments);
        } else if (comments.contains("discuss")) {
            return 0;
        }
        return -1;
    }

    int getRank(Element row){
        int rank;
        try {
            rank = Integer.parseInt(row.getElementsByClass("rank").text().replace(".", ""));
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return rank;
    }
}
