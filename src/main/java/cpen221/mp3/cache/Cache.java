package cpen221.mp3.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache<T extends Cacheable> {
    
    private static final int DSIZE = 32;
    private static final int MAXSIZE = 256;
    private static final int DTIMEOUT = 3600;

    private static int capacity;
    private static int timeout;
    private ConcurrentMap<T, Long> cache;
    /**
     * @param cap the number of objects the cache can hold
     * @param tOut the duration, in seconds
     * requires: nothing
     * effects: a cache is created with a fixed capacity and a timeout value.
     *          Objects in the cache that have not been refreshes within the timeout period
     *          are removed from the cache.
     */
    public Cache(int cap, int tOut) {
        capacity = cap;
        timeout = tOut;
        this.cache = new ConcurrentHashMap<>();
        removeOldElements();
    }

    /**
     * requires: nothing
     * effects: a cache is created with default capacity and timeout values.
     *          Stale objects are removed from the cache.
     */
    public Cache() {
        timeout = DTIMEOUT;
        capacity = DSIZE;
        this.cache = new ConcurrentHashMap<>();
        removeOldElements();
    }

    /**
     * requires: nothing
     * effects: Removes stale/old objects from the cache.
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
     * requires: nothing
     * effects: returns true if the object has been successfully added to the cache.
     *          Otherwise, returns false.
     *          If the cache is full then the least recently accessed object is removed.
     */
    public boolean put(T t) {
        long twelveHrs = 432000;
        List<Long> timeList = new ArrayList<>();
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
     * requires: nothing
     * effects:
     *          @return an object with the appropriate identifier.
     *          @throws IllegalArgumentException if there is no object
     *          in the cache with the appropriate id.
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
     * @param id the identifier of the object to "touch"
     * requires: nothing
     * effects: An object is marked as "not stale" so that its timeout is delayed.
     *          @return true if the time for the object with the same identifier as the
     *          provided id is updated. Otherwise return false.
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
     * @param t the object to update
     * requires:
     * effects: An object is updated and renewed in the cache.
     *          @return true if the object is updated successfully in the cache.
     *          Otherwise return false.
     */
    public boolean update(T t) {
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
