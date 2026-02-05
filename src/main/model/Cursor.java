package model;

public class Cursor {

    // EFFECTS: Creates a cursor at row 0 and column 0
    public Cursor() {
        // stub
    }

    // EFFECTS: Creates a cursor at given row and column (FOR TEST PURPOSES)
    public Cursor(int row, int col) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor up a row
    public void moveUp() {
        
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor to a column to the right
    public void moveRight() {

    }

    // MODIFIES: this
    // EFFECTS: moves the cursow down a row
    public void moveDown() {

    }

    // MODIFIES: this
    // EFFECTS: moves the cursor to a column to the left
    public void moveLeft() {

    }

    public int getRow() {
        return 0; // stub
    }

    public int getColumn() {
        return 0; // stub
    }

    // TODO: set a char limit, moving right at char limit goes to next row
}
