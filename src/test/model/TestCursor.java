package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCursor {

    Cursor cursor;

    @BeforeEach
    void setup() {
        cursor = new Cursor(5, 5);
    }

    @Test
    void testConstructor() {
        cursor = new Cursor();
        assertEquals(0, cursor.getRow());
        assertEquals(0, cursor.getColumn());
    }

    @Test
    void testConstructorForTest() {
        assertEquals(5, cursor.getRow());
        assertEquals(5, cursor.getColumn());
    }

    @Test
    void testMoveUpOnce() {
        cursor.moveUp();
        assertEquals(4, cursor.getRow());
        assertEquals(5, cursor.getColumn());
    }

    @Test
    void testMoveUpMultiple() {
        cursor.moveUp();
        cursor.moveUp();
        assertEquals(3, cursor.getRow());
        assertEquals(5, cursor.getColumn());
    }

    @Test
    void testMoveUpBorder() {
        cursor = new Cursor(0, 2);
        cursor.moveUp();
        assertEquals(0, cursor.getRow());
        assertEquals(2, cursor.getColumn());
    }

    @Test
    void testMoveRightOnce() {
        cursor.moveRight();
        assertEquals(5, cursor.getRow());
        assertEquals(6, cursor.getColumn());
    }

    @Test
    void testMoveRightMultiple() {
        cursor.moveRight();
        cursor.moveRight();
        assertEquals(5, cursor.getRow());
        assertEquals(7, cursor.getColumn());
    }

    @Test
    void testMoveDownOnce() {
        cursor.moveDown();
        assertEquals(6, cursor.getRow());
        assertEquals(5, cursor.getColumn());
    }

    @Test
    void testMoveDownMultiple() {
        cursor.moveDown();
        cursor.moveDown();
        assertEquals(7, cursor.getRow());
        assertEquals(5, cursor.getColumn());
    }

    @Test
    void testMoveLeftOnce() {
        cursor.moveLeft();
        assertEquals(5, cursor.getRow());
        assertEquals(4, cursor.getColumn());
    }

    @Test
    void testMoveLeftMultiple() {
        cursor.moveLeft();
        cursor.moveLeft();
        assertEquals(5, cursor.getRow());
        assertEquals(3, cursor.getColumn());
    }

    @Test
    void testMoveLeftBorder() {
        cursor = new Cursor(2, 0);
        cursor.moveLeft();
        assertEquals(2, cursor.getRow());
        assertEquals(0, cursor.getColumn());
    }
}
