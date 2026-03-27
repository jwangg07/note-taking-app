package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a notebook with a list of Notes 
public class NoteBook {

    private ArrayList<Note> notesList;
    private String filterPrefix;

    // EFFECTS: Creates an empty notebook with an empty list of notes
    public NoteBook() {
        notesList = new ArrayList<>();
        filterPrefix = "";
    }

    // REQUIRES: note title is distinct
    // MODIFIES: this
    // EFFECTS: adds note to the end of list of notes and return true,
    // if the note is not yet in the list of notes,
    // otherwise do nothing and return false
    public boolean addNote(Note n) {
        if (!notesList.contains(n)) {
            notesList.add(n);
            EventLog.getInstance().logEvent(new Event("Added Note \"" + n.getTitle() + "\" to Notebook"));
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
            EventLog.getInstance().logEvent(new Event("Note \"" + n.getTitle() + "\" Deleted from Notebook"));
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

    // MODIFIES: this
    // EFFECTS: sets the prefix by which the notes are filtered
    public void setFilter(String prefix) {
        filterPrefix = prefix;
        EventLog.getInstance().logEvent(new Event("Updated Search to: " + prefix));
    }

    // EFFECTS: filters all notes in notebook, keeping notes that match the prefix
    public List<Note> filterNotes() {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : notesList) {
            if (note.getTitle().startsWith(filterPrefix)) {
                filteredNotes.add(note);
            }
        }
        EventLog.getInstance().logEvent(new Event("Displaying " + filteredNotes.size() + " Note(s)"));
        return filteredNotes;
    }

    public List<Note> getAllNotes() {
        return notesList;
    }

    public int getNumNotes() {
        return notesList.size();
    }

    public void loadNotes(ArrayList<Note> notesList) {
        EventLog.getInstance().logEvent(new Event("Loaded Notebook from file"));
        this.notesList = notesList;
    }

    public JSONObject saveNotes() {
        EventLog.getInstance().logEvent(new Event("Saved Notebook to file"));
        return toJson();
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
