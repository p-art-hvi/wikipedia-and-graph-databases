package cpen221.mp3.cache;

import cpen221.mp3.wikimediator.WikiMediator;
import fastily.jwiki.core.Wiki;

public class Page implements Cacheable {
    /*
    Representation Invariant:
    -- page cannot have an empty string for title
    -- page cannot have a null title
    -- page cannot have an empty string for text
    -- page cannot have null text
    -- page cannot be null

    Abstraction Function:
    -- title represents the name of the page
    -- text represents the information provided on the page
     */
    private String title;
    private String text;

    /**
     * @param title is the identifier of the page
     * effects: a page object is constructed with a title and text.
     */
    public Page(String title){
        WikiMediator wiki = new WikiMediator();
        this.title = title;
        this.text = WikiMediator.wiki.getPageText(title);
    }

    /**
     * requires: nothing
     * @return the text of the page object.
     */
    public String getText(){
        return text;
    }

    /**
     * requires: nothing
     * @return the title of the page object.
     */
    public String getTitle(){
        return title;
    }

    /**
     * requires: nothing
     * @return the identifier for page
     */
    @Override
    public String id() {
        return title;
    }

    /**
     * Checks whether two page objects are equal to each other.
     * @param obj is the page object
     * @return true if the page objects are equal and false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Page)) return false;
        Page page = (Page) obj;
        if(title.equals(page.getTitle())){
            return text.equals(page.getText());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (title.length() + text.length()) / 1000;
    }
}
