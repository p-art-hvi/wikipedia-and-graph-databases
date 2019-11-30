package cpen221.mp3;

import cpen221.mp3.wikimediator.WikiMediator;
import fastily.jwiki.core.Wiki;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import cpen221.mp3.cache.Cache;
import cpen221.mp3.cache.Cacheable;
import cpen221.mp3.cache.Page;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {

    // WikiMediator wiki = new WikiMediator(); // this creates the wiki for the tests
    @Test
    public void peakLoadTest() throws InterruptedException {
        WikiMediator wiki = new WikiMediator();
        // wiki.requests.clear(); //BUT should this be needed?
        wiki.simpleSearch("Moana", 45);
        wiki.simpleSearch("Frozen", 38);
        Thread.sleep(20000);
        wiki.simpleSearch("Sleeping Beauty", 17); //1
        wiki.simpleSearch("Tangled", 100); //1
        wiki.simpleSearch("The Little Mermaid", 67); //1
        Thread.sleep(9000);
        wiki.getPage("Aladdin");
        wiki.getPage("Mulan");
        wiki.getPage("Moana");
        Thread.sleep(30030);
        wiki.getPage("Frozen 2"); //
        wiki.simpleSearch("Moana", 17); //3
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);//5
        wiki.getPage("Mulan");
        wiki.getPage("Mulan");
        wiki.getPage("Mulan"); //4
        wiki.getPage("Aladdin"); //2

        int expected = 10;
        Assert.assertEquals(expected, wiki.peakLoad30s());

    }

    @Test
    public void peakLoadTest1() throws InterruptedException {
        WikiMediator wiki = new WikiMediator();
        // WikiMediator.requests.clear();
        wiki.simpleSearch("Moana", 45);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Sleeping Beauty", 17); //1
        wiki.simpleSearch("Tangled", 100); //1
        wiki.simpleSearch("The Little Mermaid", 67); //1
        wiki.getPage("Aladdin");
        wiki.getPage("Mulan");
        wiki.getPage("Moana");
        // Thread.sleep(30);
        wiki.getPage("Frozen 2"); //
        wiki.simpleSearch("Moana", 17); //3
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);//5
        Thread.sleep(2900);
        wiki.getPage("Mulan");
        wiki.getPage("Mulan");
        wiki.getPage("Mulan"); //4
        wiki.getPage("Aladdin"); //2

        int expected = 18;
        Assert.assertEquals(expected, wiki.peakLoad30s());

    }

    @Test
    public void peakLoadTest2() throws InterruptedException { //this does not work! send help now im dying
        WikiMediator wiki = new WikiMediator();
        // WikiMediator.requests.clear();
        wiki.simpleSearch("Moana", 45);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Sleeping Beauty", 17); //1
        wiki.simpleSearch("Tangled", 100); //1
        wiki.simpleSearch("The Little Mermaid", 67); //1
        wiki.getPage("Aladdin");
        wiki.simpleSearch("Frozen", 3);
        wiki.getPage("Mulan");
        wiki.getPage("Moana");
        //Thread.sleep(30090);
        wiki.getPage("Frozen 2"); //
        wiki.simpleSearch("Moana", 17); //3
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 3);
        wiki.simpleSearch("Frozen", 7);
        wiki.simpleSearch("Frozen", 9);//5
        Thread.sleep(38000);
        wiki.getPage("Mulan");
        wiki.getPage("Mulan");
        wiki.getPage("Mulan"); //4
        wiki.getPage("Aladdin"); //2

        int expected = 15;
        Assert.assertEquals(expected, wiki.peakLoad30s());

    }


    @Test
    public void testCachePutAndGet1() {
        WikiMediator wiki = new WikiMediator();
        Page page = new Page("The Time Traveler's Wife");
        Cache<Page> cache = new Cache<>();
        Assert.assertTrue(cache.put(page));
        Assert.assertEquals(page, cache.get(page.getTitle()));
    }

    @Test
    public void testCachePutAndGet2() {
        Page page1 = new Page("Jellyfish");
        Page page2 = new Page("Giant Squid");
        Page page3 = new Page("Octopus");
        Page page4 = new Page("Lobster");
        Page page5 = new Page("Hermit Crab");
        Page page6 = new Page("Sea Cucumber");
        Page page7 = new Page("Starfish");

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
    public void testCacheTouch1() {
        Page page1 = new Page("Australian Shepherd");
        Page page2 = new Page("Bernese Mountain Dog");
        Page page3 = new Page("Newfoundland Dog");
        Page page4 = new Page("Chow chow");
        Page page5 = new Page("St. Bernard");
        Page page6 = new Page("Afghan Hound");
        Page page7 = new Page("Samoyed");
        Page page8 = new Page("Rottweiler");
        Page page9 = new Page("Great Dane");
        Page page10 = new Page("Alaskan Malamute");

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
        Assert.assertEquals(page8, cache.get(page8.getTitle()));
        Assert.assertEquals(page9, cache.get(page9.getTitle()));
        Assert.assertEquals(page10, cache.get(page10.getTitle()));

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
    public void testCacheUpdate1() {
        Page page1 = new Page("Cranium");
        Page page2 = new Page("Mandible");
        Page page3 = new Page("Clavical");
        Page page4 = new Page("Sternum");
        Page page5 = new Page("Humerus");
        Page page6 = new Page("Ribs");
        Page page7 = new Page("Vertebrae");
        Page page8 = new Page("Pelvis");
        Page page9 = new Page("Carpals");
        Page page10 = new Page("Tibia");

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
        Assert.assertEquals(page8, cache.get(page8.getTitle()));
        Assert.assertEquals(page9, cache.get(page9.getTitle()));
        Assert.assertEquals(page10, cache.get(page10.getTitle()));

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

    @Test
    public void getConnectedPagesTest() {
        String pageTitle = "Tun Thura Thet";
        WikiMediator wiki = new WikiMediator();
        int expected0 = 1;
        Assert.assertEquals(expected0, wiki.getConnectedPages(pageTitle, 0).size());
        int expected1 = 21;
        Assert.assertEquals(expected1, wiki.getConnectedPages(pageTitle, 1).size());
        int expected2 = 4288;
        Assert.assertEquals(expected2, wiki.getConnectedPages(pageTitle, 2).size());
    }

    @Test
    public void testSimpleSearch() {
        WikiMediator wiki = new WikiMediator();
        wiki.getPage("Moana");
    }

    @Test
    public void ZeitgeistTest() {
        WikiMediator wiki = new WikiMediator();
        //wiki.requests.clear(); //BUT should this be needed?
        wiki.simpleSearch("Moana", 4);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Sleeping Beauty", 17); //1
        wiki.simpleSearch("Tangled", 100); //1
        wiki.simpleSearch("The Little Mermaid", 67); //1
        wiki.getPage("Aladdin");
        wiki.getPage("Mulan");
        wiki.getPage("Moana");
        wiki.getPage("Frozen 2"); //
        wiki.simpleSearch("Moana", 17); //3
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);//5
        wiki.getPage("Mulan");
        wiki.getPage("Mulan");
        wiki.getPage("Mulan"); //4
        wiki.getPage("Aladdin"); //2


        List<String> expected = new ArrayList<>();
        expected.add("Frozen");
        expected.add("Mulan");
        expected.add("Moana");
        expected.add("Aladdin");

        Assert.assertEquals(expected, wiki.zeitgeist(4));

    }

    @Test
    public void TrendingTest() throws InterruptedException {
        WikiMediator wiki = new WikiMediator();
        wiki.requests.clear(); //BUT should this be needed?
        wiki.simpleSearch("Moana", 45);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Sleeping Beauty", 17); //1
        wiki.simpleSearch("Tangled", 100); //1
        wiki.simpleSearch("The Little Mermaid", 67); //1
        wiki.getPage("Aladdin");
        wiki.getPage("Mulan");
        wiki.getPage("Moana");
        wiki.getPage("Frozen 2"); //
        wiki.getPage("Moana");
        wiki.getPage("Moana");
        wiki.getPage("Moana");
        Thread.sleep(31000);
        wiki.simpleSearch("Moana", 17); //3
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);
        wiki.simpleSearch("Frozen", 38);//5
        wiki.getPage("Mulan");
        wiki.getPage("Mulan");
        wiki.getPage("Aladdin"); //4
        wiki.getPage("Aladdin"); //2
        wiki.getPage("Mulan");


        List<String> expected = new ArrayList<>();
        expected.add("Frozen");
        expected.add("Mulan");
        expected.add("Aladdin");
        expected.add("Moana");

        Assert.assertEquals(expected, wiki.trending(4));

    }


    @Test
    public void testFullCapacity1() {
        Cache<Page> fullCache = new Cache<>(1, 2);
        Page page1 = new Page("Lobster");
        Page page2 = new Page("Shrimp");
        Assert.assertTrue(fullCache.put(page1));
        Assert.assertTrue(fullCache.put(page2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        Cache<Page> cache = new Cache<>();
        Page page1 = null;
        Assert.assertFalse(cache.put(page1));
        cache.get(null);
    }

    @Test
    public void testFullCapacity() {
        Cache<Page> fullCache = new Cache<>(256, 432000);
        Integer i = 1;
        while (fullCache.size() != 256) {
            Page page = new Page(i.toString());
            fullCache.put(page);
            i++;
        }
        Page page = new Page("Puppy");
        Assert.assertTrue(fullCache.put(page));
    }
}

    //testCases to write:
    //trending/zeitgeist 0 limit