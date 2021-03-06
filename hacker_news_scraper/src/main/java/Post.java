/*
   POJO for each post the scraper finds
 */

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter
@AllArgsConstructor
@ToString
class Post {

    String title, uri, author;
    int points, comments, rank;

}
