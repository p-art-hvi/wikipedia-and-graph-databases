package cpen221.mp3.cache;

import java.util.*;

public class Cache<T extends Cacheable> {

    /* the default cache size is 32 objects */
    public static final int DSIZE = 32;

    /*maximum cache size*/
    public static final int MAXSIZE = 256;

    /* the default timeout value is 3600s */
    public static final int DTIMEOUT = 3600;

    private static int capacity;
    private static int timeout;
    private Map<T, Long> cache;
    /**
     * Create a cache with a fixed capacity and a timeout value.
     * Objects in the cache that have not been refreshed within the timeout period
     * are removed from the cache.
     *
     * @param capacity the number of objects the cache can hold
     * @param timeout the duration, in seconds, an object should be in the cache before it times out
     */
    public Cache(int capacity, int timeout) {
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
        this.cache = new HashMap<>();
    }

    public int size(){
        return this.cache.size();
    }
    /**
     * Add a value to the cache.
     * If the cache is full then remove the least recently accessed object to
     * make room for the new object.
     */
    public boolean put(T t) {
        long twelveHrs = 432000;
        List<Long> timeList = new ArrayList<>();
        //if the cache is full then remove the oldest object in the cache
        if(this.cache.size() == MAXSIZE){
            for(T element: this.cache.keySet()){
                long time = this.cache.get(element);
                long timeDifference = System.currentTimeMillis() - time;
                if(timeDifference >= twelveHrs) {
                    this.cache.remove(element, this.cache.get(element));
                    this.cache.put(t, System.currentTimeMillis());
                    return true;
                }else{
                    timeList.add(timeDifference);
                }
            }

            Collections.sort(timeList);

            long longest = timeList.get(timeList.size() - 1);
            for(T element: this.cache.keySet()){
                if(this.cache.get(element) == longest){
                    this.cache.remove(element, this.cache.get(element));
                    this.cache.put(t, System.currentTimeMillis());
                    return true;
                }
            }
        }else{
            this.cache.put(t, System.currentTimeMillis());
            return true;
        }
        return false;
    }

    /**
     * @param id the identifier of the object to be retrieved
     * @return the object that matches the identifier from the cache
     */
    public T get(String id){
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
    public boolean touch(String id) {
        boolean touch = false;
        T element = get(id);
        for(T t: this.cache.keySet()){
            if(t == element){
                this.cache.replace(t, this.cache.get(t), System.currentTimeMillis());
                touch = true;
            }
        }

        return touch;
    }

    /**
     * Update an object in the cache.
     * This method updates an object and acts like a "touch" to renew the
     * object in the cache.
     *
     * @param t the object to update
     * @return true if successful and false otherwise
     */
    public boolean update(T t) {
        //update object by adding new object to map
        boolean update = false;
        if(this.cache.containsKey(t)){
            update = true;
            this.cache.put(t, System.currentTimeMillis());
        }
        return update;
    }

    /*
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cache<?>){
            return ((Cache<?>) obj).hashCode() == hashCode();
        }
        return false;
    }

    public int hashCode() {
       return this.cache.hashCode();
    }
     */
}
