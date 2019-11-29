package cpen221.mp3.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache<T extends Cacheable> {

    /* the default cache size is 32 objects */
    private static final int DSIZE = 32;

    /*maximum cache size*/
    private static final int MAXSIZE = 256;

    /* the default timeout value is 3600s */
    private static final int DTIMEOUT = 3600;

    private static int capacity;
    private static int timeout;
    private ConcurrentMap<T, Long> cache;
    /**
     * Create a cache with a fixed capacity and a timeout value.
     * Objects in the cache that have not been refreshed within the timeout period
     * are removed from the cache.
     *
     * @param cap the number of objects the cache can hold
     * @param tOut the duration, in seconds, an object should be in the cache before it times out
     * requires: nothing
     * effects:
     */
    public Cache(int cap, int tOut) {
        capacity = cap;
        timeout = tOut;
        this.cache = new ConcurrentHashMap<>();
        removeOldElements();
    }

    /**
     * Create a cache with default capacity and timeout values.
     * If an object has gone stale, it is removed from the cache.
     * requires:
     * effects:
     */
    public Cache() {
        timeout = DTIMEOUT;
        capacity = DSIZE;
        this.cache = new ConcurrentHashMap<>();
        removeOldElements();
    }

    /**
     * Removes stale/old objects from the cache.
     * requires:
     * effects:
     */
    private void removeOldElements() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                long previous = -1;
                while(true){
                    long recent = System.currentTimeMillis();
                    while(recent - previous >= 10){
                        previous = recent;
                        for(T element: cache.keySet()){
                            if(recent - cache.get(element) >= timeout * 10){
                                cache.remove(element, cache.get(element));
                            }
                        }
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * Add a value to the cache.
     * If the cache is full then remove the least recently accessed object to
     * make room for the new object.
     * requires:
     * effects: returns true if the object has been successfully added to the cache.
     *          Otherwise, returns false.
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
                    return this.cache.containsKey(t) && this.cache.containsValue(this.cache.get(t));
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
                    return this.cache.containsKey(t) && this.cache.containsValue(this.cache.get(t));
                }
            }
        }
            this.cache.put(t, System.currentTimeMillis());
            return this.cache.containsKey(t) && this.cache.containsValue(this.cache.get(t));
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
