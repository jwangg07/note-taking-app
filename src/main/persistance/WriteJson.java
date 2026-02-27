package persistance;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.NoteBook;

// Represents a writer that writes a notebook to a JSON file and stores locally
// Code adapted from Paul Carter's JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/tree/master 
public class WriteJson {

    private PrintWriter writer;
    private String path;

    // EFFECTS: Constructs a writer to write a JSON file to a given path
    public WriteJson(String path) {
        this.path = path;
    }

    // MODIFIES: this
    // EFFECTS: Converts a notebook to a JSON file and saves it locally
    // throws FileNotFoundException if the path file does not exist
    public void write(NoteBook notebook) throws FileNotFoundException {
        writer = new PrintWriter(path);
        JSONObject json = notebook.toJson();
        writer.print(json.toString(4));
        writer.close();
    }
}
