package cpen221.mp3.cache;

import cpen221.mp3.wikimediator.WikiMediator;
import fastily.jwiki.core.Wiki;

public class Page implements Cacheable {
    public static String title;
    private static String text;

    public Page(String title){
        WikiMediator wiki = new WikiMediator();
        Page.title = title;
    }

    public String getText(){
        return WikiMediator.wiki.getPageText(title);
    }

    public String getTitle(){
        return title;
    }

    public void updateTitle(String title){
        Page.title = title;
    }

    public void updateText(String text){
        Page.text = text;
    }

    @Override
    public String id() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Page)) return false;
        Page page = (Page) obj;
        if(title.equals(page.getTitle())){
            return text.equals(page.getText());
        }
        return false;
    }
}
