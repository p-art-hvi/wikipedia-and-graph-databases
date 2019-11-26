package cpen221.mp3.cache;

public class Page implements Cacheable {
    private String title;
    private String text;

    public Page(String title, String text){
        this.title = title;
        this.text = text;
    }

    public String getTitle(){
        return this.title;
    }

    public String getText(){
        return this.text;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateText(String text){
        this.text = text;
    }

    @Override
    public String id() {
        return this.title;
    }
}
