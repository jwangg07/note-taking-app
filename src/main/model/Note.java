package model;

// Represents a note having a title and content
public class Note {

    private String title;
    private String content;

    // EFFECTS: Constructs a note with given title and no content
    public Note(String title) {
        this.title = title;
        content = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
