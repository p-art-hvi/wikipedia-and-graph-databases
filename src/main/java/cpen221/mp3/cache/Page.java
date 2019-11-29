package cpen221.mp3.cache;

import cpen221.mp3.wikimediator.WikiMediator;
import fastily.jwiki.core.Wiki;

public class Page implements Cacheable {
    public String title;
    private String text;

    public Page(String title){
        WikiMediator wiki = new WikiMediator();
        this.title = title;
        this.text = WikiMediator.wiki.getPageText(title);
    }

    public String getText(){
        return text;
    }

    public String getTitle(){
        return title;
    }

    public void updateTitle(String title){
        this.title = title;
    }

      public void updateText(String text){
        this.text = text;
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
