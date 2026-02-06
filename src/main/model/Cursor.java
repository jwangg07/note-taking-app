package model;

public class Cursor {

    private int row;
    private int col;

    // EFFECTS: Creates a cursor at row 1 and column 1
    public Cursor() {
        row = 1;
        col = 1;
    }

    // REQUIRES: row > 1, col > 1
    // EFFECTS: Creates a cursor at given row and column (FOR TEST PURPOSES)
    public Cursor(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor up a row, if not already on the top row
    public void moveUp() {
        if (row > 1) row--;
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
    // EFFECTS: moves the cursor to a column to the left, if not already at the left most column
    public void moveLeft() {
        if (col > 1) col--;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    // TODO: set a char limit, moving right at char limit goes to next row
}
