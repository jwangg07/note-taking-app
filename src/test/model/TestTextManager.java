package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTextManager {

    private TextManager text;

    @BeforeEach
    void setup() {
        text = new TextManager();
    }

    @Test
    void testConstructor() {
        assertEquals(1, text.getLines().size());
        assertEquals("", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(1, text.getCursor().getColumn());
    }

    @Test
    void testInsertCharOnce() {
        text.insertChar('a');
        assertEquals("a", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(2, text.getCursor().getColumn());
    }

    @Test
    void testInsertCharMultiple() {
        text.insertChar('a');
        text.insertChar('b');
        assertEquals("ab", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(3, text.getCursor().getColumn());
    }

    @Test
    void testInsertCharMiddle() {
        text.insertChar('a');
        text.insertChar('b');
        text.getCursor().moveLeft();
        text.insertChar('c');
        assertEquals("acb", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(3, text.getCursor().getColumn());
    }

    @Test
    void testDeleteCharOnce() {
        text.insertChar('a');
        text.insertChar('b');
        text.deleteChar();
        assertEquals("a", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(2, text.getCursor().getColumn());
    }

    @Test
    void testDeleteCharMultiple() {
        text.insertChar('a');
        text.insertChar('b');
        text.deleteChar();
        text.deleteChar();
        assertEquals("", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(1, text.getCursor().getColumn());
    }

    @Test
    void testDeleteCharMiddle() {
        text.insertChar('a');
        text.insertChar('b');
        text.insertChar('c');
        text.getCursor().moveLeft();
        text.deleteChar();
        assertEquals("ac", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(2, text.getCursor().getColumn());
    }

    @Test
    void testDeleteCharEmpty() {
        text.deleteChar();
        assertEquals("", text.getLines().get(0));
        assertEquals(1, text.getCursor().getRow());
        assertEquals(1, text.getCursor().getColumn());
    }

    @Test
    void testNewLineOnce() {
        text.newLine();
        assertEquals(2, text.getLines().size());
        assertEquals("", text.getLines().get(0));
        assertEquals("", text.getLines().get(1));
    }

    @Test
    void testNewLineMultiple() {
        text.newLine();
        text.newLine();
        assertEquals(3, text.getLines().size());
        assertEquals("", text.getLines().get(0));
        assertEquals("", text.getLines().get(1));
        assertEquals("", text.getLines().get(2));
    }

    @Test
    void testNewLineMiddle() {
        text.insertChar('a');
        text.insertChar('b');
        text.getCursor().moveLeft();
        text.newLine();
        assertEquals(2, text.getLines().size());
        assertEquals("a", text.getLines().get(0));
        assertEquals("b", text.getLines().get(1));
    }
}
