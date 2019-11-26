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
    final long THIRTY_SECONDS = 30000;
    final long ONE_SECOND = 1000;
    private Wiki wiki;
    private Map<String, List<Calendar>> requests = new HashMap<>();
    private List<Calendar> times = new ArrayList<>(); //do we need this?
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
    public List<String> simpleSearch(String query, int limit) {
        List<Calendar> dates = new ArrayList();
        /**TODO: create helper method for adding to global variables (simpleSearch and getPage)
         */
        Calendar time = Calendar.getInstance();
        if (requests.containsKey(query)) {
            dates = requests.get(query);
        }
        dates.add(time);
        requests.put(query, dates);
        times.add(time); //is this needed?

        return wiki.search(query, limit);
    }

    /**
     *
     * @param pageTitle title of page to search for
     * @return the text associated with the Wikipedia page pageTitle
     */
    public String getPage(String pageTitle) {

        //add in cache
        Calendar time = Calendar.getInstance();
        List<Calendar> dates = new ArrayList();
        if (requests.containsKey(pageTitle)) {
            dates = requests.get(pageTitle);
        }
        dates.add(time);
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
    public List<String> getConnectedPages(String pageTitle, int hops) {
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
        for (String page : requests.keySet()) {
            common.put(page, requests.get(page).size());
        }

        return mostCommon(limit, common);
    }

    /**
     *
     * @param limit maximum length of List to return
     * @return most frequent requests in last 30 seconds.  Maximum of limit items
     */
    public List<String> trending(int limit) {
        Map<String, Integer> trending = new HashMap<>();
        Calendar currentTime = Calendar.getInstance();

        for (String page : requests.keySet()) {
            trending.put(page, requests.get(page).size());
        }

        for (String page: requests.keySet()) {
            for (Calendar date: requests.get(page)) {
                if (date.getTimeInMillis() - currentTime.getTimeInMillis() > THIRTY_SECONDS) {
                    int num = trending.get(page);
                    trending.put(page, num - 1);
                }
            }
        }

        return mostCommon(limit, trending);
    }

    public List<String> mostCommon(int maxNum, Map<String, Integer> trendingMap){
        List<String> trendingList = new ArrayList<>();
        Integer max;
        String mostCommon = null;

        while (trendingList.size() <= maxNum && !trendingList.isEmpty()) {
            max = Integer.MIN_VALUE;
            for (String page : trendingMap.keySet()) {
                if (trendingMap.get(page) > max) {
                    max = trendingMap.get(page);
                    mostCommon = page;
                }
            }
            trendingList.add(mostCommon);
            trendingMap.remove(mostCommon);
        }
        return trendingList;
    }

    /**
     *
     * @return maximum number of requests seen in any 30 second window.
     *         Includes all requests made using the public API of WikiMediator
     */
    int peakLoad30s() {
        //create list of all Calendar in requests
        List<Calendar> allSearches = new ArrayList<>();
        List<Integer> searchNumbers = new ArrayList<>();
        for (String page: requests.keySet()) {
            for (Calendar date: requests.get(page)) {
                allSearches.add(date);
            }
        }
        //check for Calendar firstSearch
        Collections.sort(allSearches);
        Calendar firstSearch = allSearches.get(0);
        //check every 30 second interval current - firstSearch
        Calendar currentTime = Calendar.getInstance();
        long interval = currentTime.getTimeInMillis() - THIRTY_SECONDS;
        for(int round = 0; interval > firstSearch.getTimeInMillis() + THIRTY_SECONDS; round++) {
            int count = 0;
            for (Calendar date: allSearches) {
                long time = date.getTimeInMillis();
                if (time > interval && time < currentTime.getTimeInMillis() - round * ONE_SECOND) {
                    count++;
                }
            }
            interval = interval - ONE_SECOND;
            searchNumbers.add(count);
        }

        //create List of each int
        //return max value of List
        Collections.sort(searchNumbers);

        return searchNumbers.get(searchNumbers.size());
    }
}
