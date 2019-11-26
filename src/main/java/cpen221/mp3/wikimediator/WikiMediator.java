package cpen221.mp3.wikimediator;

import cpen221.mp3.cache.Cache;
import cpen221.mp3.cache.Cacheable;
import fastily.jwiki.core.Wiki;

import java.util.*;

public class WikiMediator {

    /* TODO: Implement this datatype

        You must implement the methods with the exact signatures
        as provided in the statement for this mini-project.

        You must add method signatures even for the methods that you
        do not plan to implement. You should provide skeleton implementation
        for those methods, and the skeleton implementation could return
        values like null.

     */
    private Wiki wiki;
    private Map<String, List<Calendar>> requests;
    private Set<Calendar> times;
    private Cache<Cacheable> cache;

    public WikiMediator(){
        wiki = new Wiki("en.wikipedia.org");
    }

    /**
     *
     * @param query String to search Wikipedia for
     * @param limit maximum number of pages to return
     * @return up to limit page titles that match the query
     */
    List<String> simpleSearch(String query, int limit) {

        List<Calendar> dates = new ArrayList();
        Calendar time = Calendar.getInstance();
        if (requests.containsKey(query)) {
            dates = requests.get(query);
            dates.add(time);
        }
        requests.put(query, dates);
        times.add(time);

        return wiki.search(query, limit);
    }

    /**
     *
     * @param pageTitle title of page to search for
     * @return the text associated with the Wikipedia page pageTitle
     */
    String getPage(String pageTitle) {

        Calendar time = Calendar.getInstance();
        List<Calendar> dates = new ArrayList();
        if (requests.containsKey(pageTitle)) {
            dates = requests.get(pageTitle);
            dates.add(time);
        }
        requests.put(pageTitle, dates);
        times.add(time);

        return wiki.getPageText(pageTitle);
    }

    /**
     *
     * @param pageTitle name of the page the search starts from
     * @param hops maximum number of links allowed to follow
     * @return a list of page titles that can be reached by following up to hops
     *         links starting with pageTitle
     */
    List<String> getConnectedPages(String pageTitle, int hops) {
        List<String> connectedPages = new ArrayList<>();
        List<String> connected = wiki.getLinksOnPage(pageTitle);

        int linkNumber = 0;
        int listVal = 0;
        if (hops == 0) {
            return connectedPages;
        }
        for (String page: connected) {
            connectedPages.add(page);
        }
        linkNumber++;

        while (linkNumber < hops) {
            for (int i = listVal; i < connectedPages.size(); i++ ) { //only checks new pages each time
                List<String> newPages = wiki.getLinksOnPage(connectedPages.get(i));
                listVal++;
                for(String page1: newPages) {
                    connectedPages.add(page1);
                }
            }
            linkNumber++;
        }

        return connectedPages;
    }

    /**
     *
     * @param limit maximum length of List to return
     * @return the most common Strings used in simpleSearch and getPage.
     *         Items are sorted in non-increasing count order.  Maximum of limit
     *         items.
     */
    List<String> zeitgeist(int limit) {

        Map<String, Integer> common = new HashMap<>();
        List<String> commonList = new ArrayList<>();
        for (String page : requests.keySet()) {
            common.put(page, requests.get(page).size());
        }

        Integer max = Integer.MIN_VALUE;
        String mostCommon = null;

        while (commonList.size() <= limit && !common.isEmpty()) {
            max = Integer.MIN_VALUE;
            for (String page : common.keySet()) {
                if (common.get(page) > max) {
                    max = common.get(page);
                    mostCommon = page;
                }
            }
            commonList.add(mostCommon);
            common.remove(mostCommon);
        }

        return commonList;
    }

    /**
     *
     * @param limit maximum length of List to return
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
