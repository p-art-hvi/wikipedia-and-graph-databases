package cpen221.mp3.cache;

import java.util.*;

public class Cache<T extends Cacheable> {

    /* the default cache size is 32 objects */
    public static final int DSIZE = 32;

    /* the default timeout value is 3600s */
    public static final int DTIMEOUT = 3600;

    private int capacity;
    private int timeout;
    private Map<T, Long> cache;
    /**
     * Create a cache with a fixed capacity and a timeout value.
     * Objects in the cache that have not been refreshed within the timeout period
     * are removed from the cache.
     *
     * @param capacity the number of objects the cache can hold
     * @param timeout the duration, in seconds, an object should be in the cache before it times out
     */
    private Cache(int capacity, int timeout) {
        this.capacity = capacity;
        this.timeout = timeout;
        this.cache = new HashMap<>();
    }

    /**
     * Create a cache with default capacity and timeout values.
     */
    public Cache() {
        this.timeout = DTIMEOUT;
        this.capacity = DSIZE;
    }

    /**
     * Add a value to the cache.
     * If the cache is full then remove the least recently accessed object to
     * make room for the new object.
     */
    public boolean put(T t) {
        long currentTimeMillis = System.currentTimeMillis();
        long twelveHrs = 43200;
        List<Long> timeList = new ArrayList<>();
        if(this.cache.size() == DSIZE){
            for(T element: this.cache.keySet()){
                long time = this.cache.get(element);
                long timeDifference = currentTimeMillis - time;
                if(timeDifference == twelveHrs) {
                    this.cache.remove(element, this.cache.get(element));
                }else{
                    timeList.add(timeDifference);
                }
            }

            Collections.sort(timeList);

            long longest = timeList.get(timeList.size() - 1);
            for(T element: this.cache.keySet()){
                if(this.cache.get(element) == longest){
                    this.cache.remove(element, this.cache.get(element));
                }
            }
        }else{
            this.cache.put(t, this.cache.get(t));
        }
        return false;
    }

    /**
     * @param id the identifier of the object to be retrieved
     * @return the object that matches the identifier from the cache
     */
    T get(String id){
        boolean inCache = true;
        for(T t: cache.keySet()){
            if(t.id().equals(id)){
               return t;
            } else {
                inCache = false;
            }
        }
        if(!inCache){
            throw new IllegalArgumentException();
        }
       return null;
    }

    /**
     * Update the last refresh time for the object with the provided id.
     * This method is used to mark an object as "not stale" so that its timeout
     * is delayed.
     *
     * @param id the identifier of the object to "touch"
     * @return true if successful and false otherwise
     */
    boolean touch(String id) {
        //update time of the object that has id
        T element = get(id);
        for(T t: this.cache.keySet()){

        }
        return false;
    }

    /**
     * Update an object in the cache.
     * This method updates an object and acts like a "touch" to renew the
     * object in the cache.
     *
     * @param t the object to update
     * @return true if successful and false otherwise
     */
    boolean update(T t) {
        //update object by adding new object to map
        return false;
    }

}
