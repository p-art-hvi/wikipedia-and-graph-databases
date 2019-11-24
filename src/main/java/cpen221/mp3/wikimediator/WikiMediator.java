package cpen221.mp3.wikimediator;

import fastily.jwiki.core.Wiki;

import java.util.List;

public class WikiMediator {

    /* TODO: Implement this datatype

        You must implement the methods with the exact signatures
        as provided in the statement for this mini-project.

        You must add method signatures even for the methods that you
        do not plan to implement. You should provide skeleton implementation
        for those methods, and the skeleton implementation could return
        values like null.

     */

    public WikiMediator(){
        Wiki wiki = new Wiki("en.wikipedia.org");
    }
    /**
     *
     * @param query
     * @param limit
     * @return up to limit page titles that match the query
     */
    List<String> simpleSearch(String query, int limit) {

        return null;
    }

    /**
     *
     * @param pageTitle
     * @return the text associated with the Wikipedia page pageTitle
     */
    String getPage(String pageTitle) {
        return null;
    }

    /**
     *
     * @param pageTitle
     * @param hops
     * @return a list of page titles that can be reached by following up to hops
     *         links starting with pageTitle
     */
    List<String> getConnectedPages(String pageTitle, int hops) {
        return null;
    }

    /**
     *
     * @param limit
     * @return the most common Strings used in simpleSearch and getPage.
     *         Items are sorted in non-increasing count order.  Maximum of limit
     *         items.
     */
    List<String> zeitgeist(int limit) {
        return null;
    }

    /**
     *
     * @param limit
     * @return most frequent requests in last 30 seconds.  Maximum of limit items
     */
    List<String> trending(int limit) {
        return null;
    }

    /**
     *
     * @return maximum number of requests seen in any 30 second window.
     *         Includes all requests made using the public API of WikiMediator
     */
    int peakLoad30s() {
        return 0;
    }
}
