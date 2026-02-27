package persistance;

import java.io.FileNotFoundException;

import model.NoteBook;

// Represents a writer that writes a notebook to a JSON file and stores locally
// Code adapted from Paul Carter's JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/tree/master 
public class WriteJson {
    
    // EFFECTS: Constructs a writer to write a JSON file to a given path
    public WriteJson(String path) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Converts a notebook to a JSON file and saves it locally
    // throws FileNotFoundException if the path file does not exist 
    public void write(NoteBook notebook) throws FileNotFoundException {

    }

}
