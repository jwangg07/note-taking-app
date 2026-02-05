package model;

// Represents and operates a Note 
public class Note {

    private String title;
    private String content;

    // EFFECTS: Creates an empty note with given title and no content
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
