package persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;
import model.NoteBook;

@ExcludeFromJacocoGeneratedReport
public class TestWriteJson {

    NoteBook notebook;

    @BeforeEach
    void setup() {
        notebook = new NoteBook();
    }

    @Test
    void testWriteBadPath() {
        WriteJson writeJson = new WriteJson("data/badpath.json");
        try {
            writeJson.write(notebook);
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testWriteEmptyNoteBook() {
        WriteJson writeJson = new WriteJson("data/testWriteEmptyNote.json");
        try {
            writeJson.write(notebook);

            ReadJson readJson = new ReadJson("data/testWriteEmptyNote.json");
            notebook = readJson.read();
            assertEquals(0, notebook.getNumNotes());
        } catch (IOException e) {
            fail("IOException was not expected");
        }
    }

    @Test
    void testWriteNoteBook() {
        WriteJson writeJson = new WriteJson("data/testWriteNoteBook.json");
        notebook.addNote(new Note("note1", "Hello World!"));
        notebook.addNote(new Note("note2", "My Other Note."));
        try {
            writeJson.write(notebook);
            ReadJson readJson = new ReadJson("data/testWriteNoteBook.json");
            notebook = readJson.read();
            assertEquals(0, notebook.getNumNotes());
            assertEquals(2, notebook.getNumNotes());
            List<Note> notes = notebook.getAllNotes();
            checkNote("note1", "Hello World!", notes.get(0));
            checkNote("note2", "My Other Note.", notes.get(1));
        } catch (IOException e) {
            fail("IOException was not expected");
        }
    }

    private void checkNote(String expectedTitle, String exptectedContent, Note testNote) {
        assertEquals(expectedTitle, testNote.getTitle());
        assertEquals(exptectedContent, testNote.getContent());
    }
}
