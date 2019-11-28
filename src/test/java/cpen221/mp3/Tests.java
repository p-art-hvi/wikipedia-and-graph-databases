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


import java.util.HashMap;
import java.util.Map;

public class Tests {

    WikiMediator wiki = new WikiMediator(); // this creates the wiki for the tests

    @Test
    public void testCachePut1(){
        Page page = new Page("The Time Traveler's Wife", WikiMediator.getPage("The Time Traveler's Wife"));
        Cache<Page> cache = new Cache<>();
        Assert.assertTrue(cache.put(page));
        Assert.assertEquals(page, cache.get(page.getTitle()));
    }

    @Test
    public void testCachePut2(){
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
        //Assert.assertEquals(page3, cache.get(page3.getTitle()));
        //Assert.assertEquals(page4, cache.get(page4.getTitle()));
        //Assert.assertEquals(page5, cache.get(page5.getTitle()));
        //Assert.assertEquals(page6, cache.get(page6.getTitle()));
        //Assert.assertEquals(page7, cache.get(page7.getTitle()));
    }


    @Test
    public void testCacheTouch1(){

    }

    @Test
    public void testCacheUpdate1(){

    }
    @Test
    public void getConnectedPagesTest() {
        String pageTitle = "Tun Thura Thet";
       // int expected1 = 21;
        //Assert.assertEquals(expected1, WikiMediator.getConnectedPages(pageTitle, 1).size());
        int expected2 = 4288;
        Assert.assertEquals(expected2, WikiMediator.getConnectedPages(pageTitle, 2).size());
    }

    @Test
    public void ZeitgeistTest() {
        WikiMediator.simpleSearch("Moana", 45);
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Sleeping Beauty", 17); //1
        WikiMediator.simpleSearch("Tangled", 100); //1
        WikiMediator.simpleSearch("The Little Mermaid", 67); //1
        WikiMediator.getPage("Aladdin");
        WikiMediator.getPage("Mulan");
        WikiMediator.getPage("Moana");
        WikiMediator.getPage("Frozen 2"); //1
        WikiMediator.simpleSearch("Moana", 17); //3
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Frozen", 38);//5
        WikiMediator.getPage("Mulan");
        WikiMediator.getPage("Mulan");
        WikiMediator.getPage("Mulan"); //4
        WikiMediator.getPage("Aladdin"); //2

        List<String> expected = new ArrayList<>();
        expected.add("Frozen");
        expected.add("Mulan");
        expected.add("Moana");
        expected.add("Aladdin");

        Assert.assertEquals(expected, WikiMediator.zeitgeist(4));

    }
    @Test
    public void TrendingTest() throws InterruptedException {
        WikiMediator.simpleSearch("Moana", 45);
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Sleeping Beauty", 17); //1
        WikiMediator.simpleSearch("Tangled", 100); //1
        WikiMediator.simpleSearch("The Little Mermaid", 67); //1
        WikiMediator.getPage("Aladdin");
        WikiMediator.getPage("Mulan");
        WikiMediator.getPage("Moana");
        WikiMediator.getPage("Frozen 2"); //1
        Thread.sleep(31000);
        WikiMediator.simpleSearch("Moana", 17); //1
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Frozen", 38);
        WikiMediator.simpleSearch("Frozen", 38);//4
        WikiMediator.getPage("Mulan");
        WikiMediator.getPage("Mulan");
        WikiMediator.getPage("Mulan"); //3
        WikiMediator.getPage("Aladdin");
        WikiMediator.simpleSearch("Aladdin", 45); //2

        List<String> expected = new ArrayList<>();
        expected.add("Frozen");
        expected.add("Mulan");
        expected.add("Aladdin");
        expected.add("Moana");

        Assert.assertEquals(expected, WikiMediator.trending(4));

    }


}
