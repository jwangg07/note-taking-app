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

    // EFFECT: Constructs a note with given title and given content
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        EventLog.getInstance().logEvent(new Event("Updated Title for Note"));
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        EventLog.getInstance().logEvent(new Event("Updated Content for Note"));
        this.content = content;
    }
}
