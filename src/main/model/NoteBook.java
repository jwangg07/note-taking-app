package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
        if (!notesList.contains(n)) {
            notesList.add(n);
            EventLog.getInstance().logEvent(new Event("Added Note to Notebook"));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if note is found in list of notes, remove note from the list of
    // notes and returns true
    // otherwise, do nothing and return false
    public boolean deleteNote(Note n) {
        if (notesList.contains(n)) {
            notesList.remove(n);
            EventLog.getInstance().logEvent(new Event("Note Deleted from Notebook"));
            return true;
        }
        return false;
    }

    // EFFECTS: returns the note found with given title in list of notes,
    // otherwise, return null
    public Note getNote(String title) {
        for (Note n : notesList) {
            if (n.getTitle().equals(title)) {
                EventLog.getInstance().logEvent(new Event("Opened Note from Notebook"));
                return n;
            }
        }
        return null;
    }

    // public List<Note> filterNotes(String prefix) {
    //     List<Note> filteredNotes = new ArrayList<>();
    //     for (Note note : notesList) {
    //         if (note.getTitle().startsWith(prefix)) {
    //             filteredNotes.add(note);
    //         }
    //     }
    // }

    public List<Note> getAllNotes() {
        return notesList;
    }

    public int getNumNotes() {
        return notesList.size();
    }

    // EFFECTS: Converts notebook into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Note note : notesList) {
            JSONObject noteJson = new JSONObject();
            noteJson.put("title", note.getTitle());
            noteJson.put("content", note.getContent());
            jsonArray.put(noteJson);
        }

        json.put("notes", jsonArray);

        return json;
    }
}
