package model;

import java.util.ArrayList;
import java.util.List;

// Represents and operates on a collection of Notes 
public class NoteBook {
    
    private ArrayList<Note> notesList;

    // EFFECTS: Creates an empty notebook with an empty list of notes
    public NoteBook() {
        notesList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if note is not yet in list of notes, adds note to the list of notes and return true
    // else, do nothing and return false
    public boolean addNote(Note n) {
        // return false; // stub
        if (!notesList.contains(n)) {
            notesList.add(n);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if note is found in list of notes, removes note from the list of notes and returns true
    // else, do nothing and return false
    public boolean deleteNote(Note n) {
        // return false; // stub
        if (notesList.contains(n)) {
            notesList.remove(n);
            return true;
        }
        return false;
    }

    // EFFECTS: returns note found with given title in list of notes, else null
    public Note getNote(String title) {
        for (Note n : notesList) {
            if (n.getTitle() == title) {
                return n;
            }
        }
        return null;
    }

    public List<Note> getAllNotes() {
        return notesList;
    }
}
