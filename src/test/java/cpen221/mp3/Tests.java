package cpen221.mp3;

import cpen221.mp3.wikimediator.WikiMediator;
import cpen221.mp3.cache.Cache;
import cpen221.mp3.cache.Cacheable;
import cpen221.mp3.cache.Page;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {

    WikiMediator wiki = new WikiMediator(); // this creates the wiki for the tests

    @Test
    public void testCachePutAndGet1(){
        Page page = new Page("The Time Traveler's Wife", WikiMediator.getPage("The Time Traveler's Wife"));
        Cache<Page> cache = new Cache<>();
        Assert.assertTrue(cache.put(page));
        Assert.assertEquals(page, cache.get(page.getTitle()));
    }

    @Test
    public void testCachePutAndGet2(){
        Page page1 = new Page("Jellyfish", WikiMediator.getPage("Jellyfish"));
        Page page2 = new Page("Giant Squid", WikiMediator.getPage("Giant Squid"));
        Page page3 = new Page("Octopus", WikiMediator.getPage("Octopus"));
        Page page4 = new Page("Lobster", WikiMediator.getPage("Lobster"));
        Page page5 = new Page("Hermit Crab", WikiMediator.getPage("Hermit Crab"));
        Page page6 = new Page("Sea Cucumber", WikiMediator.getPage("Sea Cucumber"));
        Page page7 = new Page("Starfish", WikiMediator.getPage("Starfish"));

        Cache<Page> cache = new Cache<>();

        Assert.assertTrue(cache.put(page1));
        Assert.assertTrue(cache.put(page2));
        Assert.assertTrue(cache.put(page3));
        Assert.assertTrue(cache.put(page4));
        Assert.assertTrue(cache.put(page5));
        Assert.assertTrue(cache.put(page6));
        Assert.assertTrue(cache.put(page7));

        Assert.assertEquals(page1, cache.get(page1.getTitle()));
        Assert.assertEquals(page2, cache.get(page2.getTitle()));
        Assert.assertEquals(page3, cache.get(page3.getTitle()));
        Assert.assertEquals(page4, cache.get(page4.getTitle()));
        Assert.assertEquals(page5, cache.get(page5.getTitle()));
        Assert.assertEquals(page6, cache.get(page6.getTitle()));
        Assert.assertEquals(page7, cache.get(page7.getTitle()));
    }

    @Test
    public void testCacheTouch1(){
        Page page1 = new Page("Australian Shepherd", WikiMediator.getPage("Australian Shepherd"));
        Page page2 = new Page("Bernese Mountain Dog", WikiMediator.getPage("Bernese Mountain Dog"));
        Page page3 = new Page("Newfoundland Dog", WikiMediator.getPage("Newfoundland Dog"));
        Page page4 = new Page("Chow chow", WikiMediator.getPage("Chow chow"));
        Page page5 = new Page("St. Bernard", WikiMediator.getPage("St. Bernard"));
        Page page6 = new Page("Afghan Hound", WikiMediator.getPage("Afghan Hound"));
        Page page7 = new Page("Samoyed", WikiMediator.getPage("Samoyed"));
        Page page8 = new Page("Rottweiler", WikiMediator.getPage("Rottweiler"));
        Page page9 = new Page("Great Dane", WikiMediator.getPage("Great Dane"));
        Page page10 = new Page("Alaskan Malamute", WikiMediator.getPage("Alaskan Malamute"));

        Cache<Page> cache = new Cache<>();

        Assert.assertTrue(cache.put(page1));
        Assert.assertTrue(cache.put(page2));
        Assert.assertTrue(cache.put(page3));
        Assert.assertTrue(cache.put(page4));
        Assert.assertTrue(cache.put(page5));
        Assert.assertTrue(cache.put(page6));
        Assert.assertTrue(cache.put(page7));
        Assert.assertTrue(cache.put(page8));
        Assert.assertTrue(cache.put(page9));
        Assert.assertTrue(cache.put(page10));

        Assert.assertEquals(page1, cache.get(page1.getTitle()));
        Assert.assertEquals(page2, cache.get(page2.getTitle()));
        Assert.assertEquals(page3, cache.get(page3.getTitle()));
        Assert.assertEquals(page4, cache.get(page4.getTitle()));
        Assert.assertEquals(page5, cache.get(page5.getTitle()));
        Assert.assertEquals(page6, cache.get(page6.getTitle()));
        Assert.assertEquals(page7, cache.get(page7.getTitle()));
        Assert.assertEquals(page7, cache.get(page8.getTitle()));
        Assert.assertEquals(page7, cache.get(page9.getTitle()));
        Assert.assertEquals(page7, cache.get(page10.getTitle()));

        Assert.assertTrue(cache.touch(page1.getTitle()));
        Assert.assertTrue(cache.touch(page2.getTitle()));
        Assert.assertTrue(cache.touch(page3.getTitle()));
        Assert.assertTrue(cache.touch(page4.getTitle()));
        Assert.assertTrue(cache.touch(page5.getTitle()));
        Assert.assertTrue(cache.touch(page6.getTitle()));
        Assert.assertTrue(cache.touch(page7.getTitle()));
        Assert.assertTrue(cache.touch(page8.getTitle()));
        Assert.assertTrue(cache.touch(page9.getTitle()));
        Assert.assertTrue(cache.touch(page10.getTitle()));
    }

    @Test
    public void testCacheUpdate1(){
        Page page1 = new Page("Cranium", WikiMediator.getPage("Cranium"));
        Page page2 = new Page("Mandible", WikiMediator.getPage("Mandible"));
        Page page3 = new Page("Clavical", WikiMediator.getPage("Clavical"));
        Page page4 = new Page("Sternum", WikiMediator.getPage("Sternum"));
        Page page5 = new Page("Humerus", WikiMediator.getPage("Humerus"));
        Page page6 = new Page("Ribs", WikiMediator.getPage("Ribs"));
        Page page7 = new Page("Vertebrae", WikiMediator.getPage("Vertebrae"));
        Page page8 = new Page("Pelvis", WikiMediator.getPage("Pelvis"));
        Page page9 = new Page("Carpals", WikiMediator.getPage("Carpals"));
        Page page10 = new Page("Tibia", WikiMediator.getPage("Tibia"));

        Cache<Page> cache = new Cache<>();

        Assert.assertTrue(cache.put(page1));
        Assert.assertTrue(cache.put(page2));
        Assert.assertTrue(cache.put(page3));
        Assert.assertTrue(cache.put(page4));
        Assert.assertTrue(cache.put(page5));
        Assert.assertTrue(cache.put(page6));
        Assert.assertTrue(cache.put(page7));
        Assert.assertTrue(cache.put(page8));
        Assert.assertTrue(cache.put(page9));
        Assert.assertTrue(cache.put(page10));

        Assert.assertEquals(page1, cache.get(page1.getTitle()));
        Assert.assertEquals(page2, cache.get(page2.getTitle()));
        Assert.assertEquals(page3, cache.get(page3.getTitle()));
        Assert.assertEquals(page4, cache.get(page4.getTitle()));
        Assert.assertEquals(page5, cache.get(page5.getTitle()));
        Assert.assertEquals(page6, cache.get(page6.getTitle()));
        Assert.assertEquals(page7, cache.get(page7.getTitle()));
        Assert.assertEquals(page7, cache.get(page8.getTitle()));
        Assert.assertEquals(page7, cache.get(page9.getTitle()));
        Assert.assertEquals(page7, cache.get(page10.getTitle()));

        Assert.assertTrue(cache.touch(page1.getTitle()));
        Assert.assertTrue(cache.touch(page2.getTitle()));
        Assert.assertTrue(cache.touch(page3.getTitle()));
        Assert.assertTrue(cache.touch(page4.getTitle()));
        Assert.assertTrue(cache.touch(page5.getTitle()));
        Assert.assertTrue(cache.touch(page6.getTitle()));
        Assert.assertTrue(cache.touch(page7.getTitle()));
        Assert.assertTrue(cache.touch(page8.getTitle()));
        Assert.assertTrue(cache.touch(page9.getTitle()));
        Assert.assertTrue(cache.touch(page10.getTitle()));

        Assert.assertTrue(cache.update(page1));
        Assert.assertTrue(cache.update(page2));
        Assert.assertTrue(cache.update(page3));
        Assert.assertTrue(cache.update(page4));
        Assert.assertTrue(cache.update(page5));
        Assert.assertTrue(cache.update(page6));
        Assert.assertTrue(cache.update(page7));
        Assert.assertTrue(cache.update(page8));
        Assert.assertTrue(cache.update(page9));
        Assert.assertTrue(cache.update(page10));
    }


    
    /*
    @Test
    public void testFullCapacity(){
        Cache<Page> cacheFull = new Cache<>(256, 43200);
        Integer i = 1;
        while(cacheFull.size() != 256){
            Page page = new Page(i.toString(), WikiMediator.getPage(i.toString()));
            cacheFull.put(page);
            i++;
        }
        Page pageFull = new Page("Dog", WikiMediator.getPage("Dog"));
        Assert.assertTrue(cacheFull.put(pageFull));
    }
    */
}
