package model;

import java.util.ArrayList;
import java.util.List;

// Represents a notebook with a list of Notes 
public class NoteBook {
    
    private ArrayList<Note> notesList;

    // EFFECTS: Creates an empty notebook with an empty list of notes
    public NoteBook() {
        notesList = new ArrayList<>();
    }

    // REQUIRES: note title is distinct
    // MODIFIES: this
    // EFFECTS: adds note to the end of list of notes and return true, 
    // if the note is not yet in the list of notes, 
    // otherwise do nothing and return false
    public boolean addNote(Note n) {
        // return false; // stub
        if (!notesList.contains(n)) {
            notesList.add(n);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if note is found in list of notes, remove note from the list of notes and returns true
    // otherwise, do nothing and return false
    public boolean deleteNote(Note n) {
        if (notesList.contains(n)) {
            notesList.remove(n);
            return true;
        }
        return false;
    }

    // EFFECTS: returns the note found with given title in list of notes, 
    // otherwise, return null
    public Note getNote(String title) {
        for (Note n : notesList) {
            if (n.getTitle().equals(title)) {
                return n;
            }
        }
        return null;
    }

    public List<Note> getAllNotes() {
        return notesList;
    }
}
