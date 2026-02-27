package persistance;

import java.io.IOException;

import org.json.JSONObject;

import model.NoteBook;

// Represents a reader that reads a notebook from a JSON file locally
public class ReadJson {

    // EFFECTS: Constructs a reader to read a JSON file given by path
    public ReadJson(String path) {

    }

    // EFFECTS: parses the JSON file and returns a notebook
    // throws IOException if an error occurs while reading data from the file  
    public NoteBook jsonToNoteBook() throws IOException {
        return null; // stub
    }

    // MODIFIES: notebook
    // EFFECTS: parses notes from JSON object and adds them to notebook
    private void addNotes(NoteBook notebook, JSONObject jsonObject) {
        // stub
    }

}
