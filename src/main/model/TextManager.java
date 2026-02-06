package model;

import java.util.List;

public class TextManager {
    
    // EFFECTS: Creates TextManager with an empty list of lines (of text) and cursor
    public TextManager() {

    }

    // MODIFIES: this, Cursor
    // EFFECTS: Inserts c at cursor position, and moves cursor to the right
    public void insertChar(char c) {

    }
    
    // MODIFIES: this, Cursor
    // EFFECTS: If column is 0, does nothing, 
    // else, deletes the character to the left of cursor position and moves cursor to the left
    public void deleteChar() {

    }

    // MODIFIES: this, Cursor
    // EFFECTS: Creates a new line in list of lines, pushes any text to the right of current line to the next line
    public void newLine() {

    }

    // EFFECTS: Returns list of lines converted to a list of string
    public List<String> getLines() {
        return null;
    }
}
