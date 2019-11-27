package cpen221.mp3.cache;

public class Page implements Cacheable {
    private static String title;
    private static String text;

    public Page(String title, String text){
        Page.title = title;
        Page.text = text;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
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
}