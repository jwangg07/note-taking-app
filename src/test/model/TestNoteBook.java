package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNoteBook {
    
    private NoteBook notebook;
    private ArrayList<Note> noteslist;
    private Note note1;
    private Note note2;

    @BeforeEach
    void setup() {
        notebook = new NoteBook();
        noteslist = new ArrayList<Note>();
        note1 = new Note("note1");
        note2 = new Note("note2");
    }

    @Test
    void testConstructor() {
        assertEquals(noteslist, notebook.getAllNotes());
    }

    @Test
    void testAddNoteOnce() {
        assertTrue(notebook.addNote(note1));
        noteslist.add(note1);
        assertEquals(noteslist, notebook.getAllNotes());
    } 

    @Test
    void testAddNoteMultipleNoRepeat() {
        assertTrue(notebook.addNote(note1));
        assertTrue(notebook.addNote(note2));
        noteslist.add(note1);
        noteslist.add(note2);
        assertEquals(noteslist, notebook.getAllNotes());
    }

    @Test
    void testAddNoteMultipleRepeat() {
        assertTrue(notebook.addNote(note1));
        assertFalse(notebook.addNote(note1));
        noteslist.add(note1);
        assertEquals(noteslist, notebook.getAllNotes());
    }

    @Test
    void testDeleteNoteOnce() {
        notebook.addNote(note1);
        assertTrue(notebook.deleteNote(note1));
        assertEquals(noteslist, notebook.getAllNotes());
    }

    @Test
    void testDeleteNoteEmpty() {
        assertFalse(notebook.deleteNote(note1));
        assertEquals(noteslist, notebook.getAllNotes());   
    }

    @Test
    void deleteNoteMultiple() {
        notebook.addNote(note1);
        notebook.addNote(note2);
        assertTrue(notebook.deleteNote(note1));
        assertTrue(notebook.deleteNote(note2));
        assertEquals(noteslist, notebook.getAllNotes());
    }

    @Test
    void getNoteFound() {
        assertNull(notebook.getNote("note1"));
        notebook.addNote(note1);
        assertEquals(note1, notebook.getNote("note1"));
    }

    @Test
    void getNoteNotFound() {
        notebook.addNote(note1);
        assertNull(notebook.getNote("note2"));
    }

    @Test void getAllNotes() {
        assertEquals(noteslist, notebook.getAllNotes());
        notebook.addNote(note1);
        noteslist.add(note1);
        assertEquals(noteslist, notebook.getAllNotes());
        notebook.addNote(note2);
        noteslist.add(note2);
        assertEquals(noteslist, notebook.getAllNotes());
    }
}
