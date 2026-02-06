package model;

import java.util.ArrayList;
import java.util.List;

// Manages text operations in the note
public class TextManager {
    
    private Cursor cursor;
    private ArrayList<StringBuilder> lines;

    // EFFECTS: Creates TextManager with an empty line in list of lines (of text) and cursor
    public TextManager() {
        cursor = new Cursor();
        lines = new ArrayList<StringBuilder>();
        lines.add(new StringBuilder());
    }

    // MODIFIES: this, Cursor
    // EFFECTS: Inserts c at cursor position, and moves cursor to the right
    public void insertChar(char c) {
        lines.get(cursor.getRow()).insert(cursor.getColumn(), c);
        cursor.moveRight();
    }
    
    // MODIFIES: this, Cursor
    // EFFECTS: If column is 0, does nothing, 
    // else, deletes the character to the left of cursor position and moves cursor to the left
    public void deleteChar() {
        int col = cursor.getColumn();
        if (col > 0) {
            lines.get(cursor.getRow()).deleteCharAt(col-1);
            cursor.moveLeft();
        }
    }

    // MODIFIES: this, Cursor
    // EFFECTS: Creates a new line in list of lines, 
    // pushes any text to the right of current line to the next line
    // moves cursor down a row and resets column to the start of the line
    public void newLine() {
        int row = cursor.getRow();
        int column = cursor.getColumn();
        StringBuilder currentLine = lines.get(row);

        StringBuilder textToRight = new StringBuilder(currentLine.substring(column, currentLine.length()));
        currentLine.replace(column, currentLine.length(), "");
        lines.add(row+1, textToRight);

        cursor.moveDown();
        cursor.setColumn(0);
    }

    // EFFECTS: Returns list of lines converted to a list of string
    public List<String> getLines() {
        List<String> content = new ArrayList<String>();
        for (StringBuilder s : lines) {
            content.add(s.toString());
        }
        return content;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
