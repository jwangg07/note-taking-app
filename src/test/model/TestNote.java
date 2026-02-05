package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNote {
    
    private Note note;

    @BeforeEach
    void setup() {
        note = new Note("note");
    }

    @Test
    void testConstructor() {
        assertEquals("note", note.getTitle());
        assertEquals("", note.getContent());
    }

    @Test
    void testGetTitle() {
        assertEquals("note", note.getTitle());
    }

    @Test
    void testSetTitle() {
        note.setTitle("newNote1");
        assertEquals("newNote1", note.getTitle());
    }

    @Test
    void testGetContent() {
        assertEquals("", note.getContent());
    }

    @Test
    void testSetContent() {
        note.setContent("hello world");
        assertEquals("hello world", note.getContent());
    }
}