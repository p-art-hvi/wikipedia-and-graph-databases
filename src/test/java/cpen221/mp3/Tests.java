package cpen221.mp3;

import cpen221.mp3.wikimediator.WikiMediator;
import fastily.jwiki.core.Wiki;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;

public class Tests {

    /*
        You can add your tests here.
        Remember to import the packages that you need, such
        as cpen221.mp3.cache.
     */
    WikiMediator wiki = new WikiMediator(); // this creates the wiki for the tests

    @Test
    public void getConnectedPagesTest() {
        String pageTitle = "Tun Thura Thet";
        int expected0 = 20;
        Assert.assertEquals(expected0, WikiMediator.getConnectedPages(pageTitle, 0).size());
       // int expected1 = 100;
       // Assert.assertEquals(expected1, WikiMediator.getConnectedPages(pageTitle, 1).size());
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
    public void TrendingTest() {
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


}
