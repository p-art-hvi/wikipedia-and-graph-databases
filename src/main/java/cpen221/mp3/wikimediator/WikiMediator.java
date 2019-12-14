package cpen221.mp3.wikimediator;

import cpen221.mp3.cache.Cache;
import cpen221.mp3.cache.Cacheable;
import cpen221.mp3.cache.Page;
import fastily.jwiki.core.Wiki;

import java.util.*;

public class WikiMediator {

     /*
    Representation Invariant:
    -- must access an online Wikipedia database
    -- cache contains no duplicates
    -- requests must not contain duplicate keys
    -- all times must be in milliseconds and >= zero

    Abstraction Function:
    -- wiki represents the online database in which the methods draw data from
    -- requests maps all searches to a list of the times they have been searched.
       each instance of time is represented by a long of the time in milliseconds
    -- times represents a list of all instances in which any requests to the wiki were made.
       each instance is represented by a long of the time in milliseconds
    -- cache represents an abstract object which stores Page objects.
       Page objects are stored for a specified maximum time.
       stores up to a specified maximum number of Page objects
    */


    final static long THIRTY_SECONDS = 30000;
    final static long ONE_SECOND = 1000;
    public static Wiki wiki;
    public Map<String, List<Long>> requests = new HashMap<>();
    private static List<Long> times = new ArrayList<>();
    private Cache<Cacheable> cache = new Cache<>();

    /**
     * effects: creates a WikiMediator object of the English language Wikipedia
     */
    public WikiMediator(){
        wiki = new Wiki("en.wikipedia.org");
    }

    /**
     *
     * @param query String to search Wikipedia for
     * @param limit maximum number of pages to return
     * @return up to limit page titles that match the query.
     *         Empty if query is null or an empty String
     */
    public List<String> simpleSearch(String query, int limit) {
        List<Long> dates = new ArrayList();
        if (query.equals("")) {
            return new ArrayList<>();
        }
        /**TODO: create helper method for adding to global variables (simpleSearch and getPage)
         */
        Long time = System.currentTimeMillis();
        if (requests.containsKey(query)) {
            dates = requests.get(query);
        }
        dates.add(time);
        requests.put(query, dates);
        times.add(time);

        return wiki.search(query, limit);
    }

    /**
     * @param pageTitle title of page being searched for
     * @return the text associated with the Wikipedia page pageTitle.
     *         If PageTitle is null or empty, an empty String is returned
     */
    public String getPage(String pageTitle) {

        Long time = System.currentTimeMillis();
        times.add(time);
        if ( pageTitle.equals("")) {
            return "";
        }
        List<Long> dates = new ArrayList();
        if (requests.containsKey(pageTitle)) {
            dates = requests.get(pageTitle);
        }
        dates.add(time);
        requests.put(pageTitle, dates);
        Page page = new Page(pageTitle);

        if (cache.contains(page)) {
            cache.update(page);
            return page.getText();

        }
        cache.put(page);
        return wiki.getPageText(pageTitle);
    }

    /**
     * @param pageTitle name of the page the search starts from
     * @param hops maximum number of links allowed to follow, must be >= 0
     * @return a list of page titles that can be reached by following up to hops
     *         links starting with pageTitle.
     *         List includes pageTitle and will return a list containing only
     *         pageTitle if hops is 0.
     *
     */
    public List<String> getConnectedPages(String pageTitle, int hops) {

        Long time = System.currentTimeMillis();
        times.add(time);
        List<String> connectedPages = new ArrayList<>();
        List<String> connected = wiki.getLinksOnPage(pageTitle);
        connectedPages.add(pageTitle);

        int linkNumber = 0;
        int listVal = 1;
        if (hops == 0) {
            return connectedPages;
        }
        for (String page: connected) {
            connectedPages.add(page);
        }
        linkNumber++;
        int size = connectedPages.size();

        while (linkNumber < hops) {
            for (int i = listVal; i < size; i++ ) { //only checks new pages each time
                List<String> newPages = wiki.getLinksOnPage(connectedPages.get(i));
                listVal++;
                for(String page1: newPages) {
                    connectedPages.add(page1);
                }
            }
            linkNumber++;
            size = connectedPages.size();
        }

        return connectedPages;
    }

    /**
     *
     * @param limit maximum length of List to return
     * @return the most common Strings used in simpleSearch and getPage.
     *         Items are sorted in non-increasing count order.
     *         List is of maximum length limit.
     *
     */
    public List<String> zeitgeist(int limit) {
        Long time = System.currentTimeMillis();
        times.add(time);

        Map<String, Integer> common = new HashMap<>();
        for (String page : requests.keySet()) {
            common.put(page, requests.get(page).size());
        }

        return mostCommon(limit, common);
    }

    /**
     *
     * @param limit maximum length of List to return
     * @return most frequent requests searched in getPage and simpleSearch
     *         in last 30 seconds.
     *         List is of maximum length limit.
     */
    public List<String> trending(int limit) {
        Long time = System.currentTimeMillis();
        times.add(time);

        Map<String, Integer> trending = new HashMap<>();
        Long currentTime = System.currentTimeMillis();

        for (String page : requests.keySet()) {
            trending.put(page, requests.get(page).size());
        }

        for (String page: requests.keySet()) {
            for (Long date: requests.get(page)) {
                if (currentTime - date > THIRTY_SECONDS) {
                    int num = trending.get(page);
                    trending.put(page, num - 1);
                }
            }
        }

        return mostCommon(limit, trending);
    }

    /**
     *
     * @param maxNum maximum length of List to return
     * @param trendingMap key: String
     *                    value: number of occurrences of key
     * @return Sorted list of the most common Strings in the given map in descending count order.
     *         List is of maximum length maxNum.
     *
     */
    public List<String> mostCommon(int maxNum, Map<String, Integer> trendingMap){
        List<String> trendingList = new ArrayList<>();
        Integer max;
        String mostCommon = null;

        while (trendingList.size() < maxNum && !trendingMap.isEmpty()) {
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
     * requires: nothing
     * @return maximum number of requests seen in any 30 second window.
     *         Includes all requests made using the public API of WikiMediator.
     */
    public int peakLoad30s() {
        //create list of all Calendar in requests
        List<Long> allSearches = new ArrayList<>();
        List<Integer> searchNumbers = new ArrayList<>();
        for (String page: requests.keySet()) {
            for (Long date: requests.get(page)) {
                allSearches.add(date);
            }
        }
        //check for Calendar firstSearch
        Collections.sort(allSearches);


        for(int i = 1; i <= allSearches.size(); i++) {
            long intervalMin = allSearches.get(allSearches.size()-i) - THIRTY_SECONDS;
            long intervalMax = allSearches.get(allSearches.size()-i);
            int count = 0;
            for (Long time: allSearches) {
                if (time >= intervalMin && time <= intervalMax) {
                    count++;
                }
            }
            searchNumbers.add(count);
        }

        //create List of each int
        //return max value of List
        Collections.sort(searchNumbers);

        return searchNumbers.get(searchNumbers.size() - 1);
    }

    /**
     * Finds a path from the start of the page to the stop page
     * by following the links between pages
     * @param startPage the title of the page where the path needs to start
     * @param stopPage the title of the page where the path should stop
     * @return a list of page titles visited in the path
     */
    List<String> getPath(String startPage, String stopPage){
        return null;//change this
    }

    /**
     *Handles structured queries which allow clients to request page titles
     *that correlates with specific client-defined criteria.
     * @param query is the client request for all pages associated with the specific criteria
     * @return a list of page titles which matches the query
     */
    List<String> executeQuery(String query){
        return null;//change this
    }
}
