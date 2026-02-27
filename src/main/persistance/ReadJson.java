package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Note;
import model.NoteBook;

// Represents a reader that reads a notebook from a JSON file locally
// Code adapted from Paul Carter's JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/tree/master 
public class ReadJson {

    private String path;

    // EFFECTS: Constructs a reader to read a JSON file given by path
    public ReadJson(String path) {
        this.path = path;
    }

    // EFFECTS: parses the JSON file and returns a notebook
    // throws IOException if an error occurs while reading data from the file
    public NoteBook jsonToNoteBook() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        String data = contentBuilder.toString();
        JSONObject jsonObject = new JSONObject(data);

        NoteBook notebook = new NoteBook();
        addNotes(notebook, jsonObject);
        return notebook; 
    }

    // MODIFIES: notebook
    // EFFECTS: parses notes from JSON object and adds them to notebook
    private void addNotes(NoteBook notebook, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextJson = (JSONObject)json;

            String title = nextJson.getString("title");
            String content = nextJson.getString("content");
            
            Note note = new Note(title, content);
            notebook.addNote(note);
        }
    }

}
