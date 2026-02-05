package model;

import java.util.List;

// Represents and operates on a collection of Notes 
public class NoteBook {
    
    // EFFECTS: Creates an empty notebook with an empty list of notes
    public NoteBook() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: if note is not yet in list of notes, adds note to the list of notes and return true
    // else, do nothing and return false
    public boolean addNote(Note n) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: if note is found in list of notes, removes note from the list of notes and returns true
    // else, do nothing and return false
    public boolean deleteNote(Note n) {
        return false; // stub
    }

    // EFFECTS: returns note found with given title in list of notes, else null
    public Note getNote(String title) {
        return null; // stub
    }

    public List<Note> getAllNotes() {
        return null; // stub
    }
}
