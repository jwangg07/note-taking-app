package model;

public class Cursor {

    private int row;
    private int col;

    // EFFECTS: Creates a cursor at row 0 and column 0
    public Cursor() {
        row = 0;
        col = 0;
    }

    // REQUIRES: row > 0, col > 0
    // EFFECTS: Creates a cursor at given row and column (FOR TEST PURPOSES)
    public Cursor(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor up a row
    public void moveUp() {
        if (row > 0) row--;
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor to a column to the right
    public void moveRight() {
        col++;
    }

    // MODIFIES: this
    // EFFECTS: moves the cursow down a row
    public void moveDown() {
        row++;
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor to a column to the left
    public void moveLeft() {
        if (col > 0) col--;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    // TODO: set a char limit, moving right at char limit goes to next row
}
