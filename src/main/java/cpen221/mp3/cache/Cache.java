package cpen221.mp3.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache<T extends Cacheable> {

    /*
    Representation Invariant:
    -- cache cannot contain null objects
    -- cache cannot have infinite timeout
    -- cache can contain <= maximum capacity
    -- cache cannot contain duplicate objects

    Abstraction Function:
    -- cache represents a ConcurrentHashMap in which methods add, remove, refresh and renew the objects which it stores.
       each key is an object and each value is the time, in milliseconds, at which the object was added to the cache
    -- timeout is the maximum duration, in seconds, for which an object is allowed to remain in the cache
    -- capacity is the maximum amount of objects that the cache is allowed to hold
     */

    private static final int DSIZE = 32;
    private static final int MAXSIZE = 256;
    private static final int DTIMEOUT = 3600;

    private static int capacity;
    private static int timeout;
    private ConcurrentMap<T, Long> cache;

    /**
     * @param cap the number of objects the cache can hold
     * @param tOut the duration, in seconds
     * effects: a cache is created with a fixed capacity and a timeout value.
     *          Objects in the cache that have not been refreshed or renewed
     *          within the timeout period, are removed from the cache.
     */
    public Cache(int cap, int tOut){
        capacity = cap;
        timeout = tOut;
        this.cache = new ConcurrentHashMap<>();
        removeOldElements();
    }

    /**
     * requires: nothing
     * effects: a cache is created with default capacity and timeout values.
     *          Objects which have not been refreshed or renewed are removed from the cache.
     */
    public Cache(){
        timeout = DTIMEOUT;
        capacity = DSIZE;
        this.cache = new ConcurrentHashMap<>();
        removeOldElements();
    }

    /**
     * requires: nothing
     * effects: Removes objects which have not been refreshed or renewed
     *          within the timeout period from the cache.
     */
    private void removeOldElements(){
       /* Thread thread2 = new Thread(new Runnable(){

            @Override
            public void run() {
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
        */
        Thread thread1 = new Thread(new Runnable() {
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
        thread1.start();
       // thread2.start();
        //thread1.join();
        //thread2.join();
    }

    /**
     * Adds an object to the cache.
     * If the cache is full then the least recently accessed object is removed.
     * @param t is the object which needs to be added to the cache
     * @return true if the object has been successfully added to the cache.
     * Otherwise, returns false.
     */
    public boolean put(T t) {
        if(t == null){
            return false;
        }
        long twelveHrs = 432000;
        List<Long> timeList = new ArrayList<>();
       /* if(this.cache.size() == MAXSIZE){
            for(T element: this.cache.keySet()){
                long time = this.cache.get(element);
                long timeDifference = System.currentTimeMillis() - time;
                if(timeDifference >= twelveHrs) {
                    this.cache.remove(element);
                    this.cache.put(t, System.currentTimeMillis());
                    return this.cache.containsKey(t);
                }else{
                    timeList.add(timeDifference);
                }
            }
            Collections.sort(timeList);

            long longest = timeList.get(timeList.size() - 1);
            for(T element: this.cache.keySet()){
                if(this.cache.get(element) == longest){
                    this.cache.remove(element);
                    this.cache.put(t, System.currentTimeMillis());
                    return this.cache.containsKey(t);
                }
            }
        } */
            this.cache.put(t, System.currentTimeMillis());
            return this.cache.containsKey(t);
    }

    /**
     * Gets an object with a specified identifier.
     * @param id the identifier of the object to be retrieved
     * @return an object with the appropriate identifier.
     * @throws IllegalArgumentException if there is no object
     * in the cache with the appropriate id.
     */
    public T get(String id){
        if(id == null){
            throw new IllegalArgumentException();
        }
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
     * An object is marked as "not stale" so that its timeout is delayed.
     * @param id the identifier of the object to "touch"
     * @return true if the time for the object with the same identifier as
     * the provided id is updated. Otherwise return false.
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
     * Checks whether the cache contains the object.
     * @param t is the object to check
     * @return true if the object is contained in the cache. Otherwise return false.
     */
    public boolean contains(T t){
        return this.cache.containsKey(t);
    }


    /**
     * An object is updated and renewed in the cache.
     * @param t the object to update
     * @return true if the object is updated successfully in the cache. Otherwise return false.
     */
    public boolean update(T t) {
        boolean update = false;
        if(this.cache.containsKey(t)){
            update = true;
            this.cache.put(t, System.currentTimeMillis());
        }
        return update;
    }

    /**
     * requires: nothing
     * @return: the size of the cache
     */
    public int size() {
        return this.cache.size();
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
