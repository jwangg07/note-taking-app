package persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Note;
import model.NoteBook;
import persistance.ReadJson;

public class TestReadJson {
    @Test
    void testReadBadPath() {
        ReadJson readJson = new ReadJson("data/badpath.json");
        try {
            NoteBook notebook = readJson.jsonToNoteBook();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReadEmptyJson() {
        ReadJson readJson = new ReadJson("data/testReadEmptyNoteBook.json");
        try {
            NoteBook notebook = readJson.jsonToNoteBook();
            assertEquals(0, notebook.getNumNotes());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    void testReadJson() {
        ReadJson readJson = new ReadJson("data/testReadNoteBook.json");
        try {
            NoteBook notebook = readJson.jsonToNoteBook();
            assertEquals(2, notebook.getNumNotes());
            List<Note> notes = notebook.getAllNotes();
            checkNote("note1", "Hello World!", notes.get(0));
            checkNote("note2", "My Other Note.", notes.get(1));
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    private void checkNote(String expectedTitle, String exptectedContent, Note testNote) {
        assertEquals(expectedTitle, testNote.getTitle());
        assertEquals(exptectedContent, testNote.getContent());
    }
}