package model;

import java.util.List;

// Manages text operations in the note
public class TextManager {
    
    // EFFECTS: Creates TextManager with an empty line in list of lines (of text) and cursor
    public TextManager() {
        // stub
    }

    // MODIFIES: this, Cursor
    // EFFECTS: Inserts c at cursor position, and moves cursor to the right
    public void insertChar(char c) {
        // stub
    }
    
    // MODIFIES: this, Cursor
    // EFFECTS: If column is 0, does nothing, 
    // else, deletes the character to the left of cursor position and moves cursor to the left
    public void deleteChar() {
        // stub
    }

    // MODIFIES: this, Cursor
    // EFFECTS: Creates a new line in list of lines, pushes any text to the right of current line to the next line
    public void newLine() {
        // stub
    }

    // EFFECTS: Returns list of lines converted to a list of string
    public List<String> getLines() {
        return null; // stub
    }

    public Cursor getCursor() {
        return null; // stub
    }
}
