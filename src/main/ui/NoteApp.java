package ui;

import model.Note;
import model.NoteBook;

public class NoteApp {

    NoteBook notebook;
    InputHandler input;
    boolean running;

    // EFFECTS: creates notes app with new notebook
    public NoteApp() {
        running = true;
        input = new InputHandler();
        notebook = new NoteBook();
    }

    // EFFECTS: run application until user quits
    public void run() {
        while (running) {
            // stub
        }
    }

    // EFFECTS: prints initial instructions to use notebook
    public void printInstructions() {
        // stub

    }

    // EFFECTS: displays a list of notes in notebook by title
    public void displayNotes() {
        for (Note note : notebook.getAllNotes()) {
            System.out.println(note.getTitle());
        }
    }

    // EFFECTS: prompts user for title and content of the note and adds note to notebook
    public void createNote() {
        String title = input.promptInput("Create a title for this note: ");
        Note note = new Note(title);
        String content = input.promptInput("Write the content for this note: ");
        note.setContent(content);
        notebook.addNote(note);
    }

    // EFFECTS: displays title and content of a note, prompts user if they want to edit
    public void selectNote() {
        String noteTitle = input.promptInput("Enter note title: ");
        Note note = notebook.getNote(noteTitle);
        if (note == null) {
            System.out.println("Note with the name " + noteTitle + " was not found.");
        } else {
            System.out.println("Title: " + note.getTitle());
            System.out.println(note.getContent());
        }
    }

    // EFFECTS: prompts user for confirmation and deletes note from notebook
    public void deleteNote(Note note) {
        String confirmation = input.promptInput("Enter note title to confirm deletion");
        if (confirmation == note.getTitle()) {
            notebook.deleteNote(note);            
        } else {
            System.out.println("note title invalid");
            deleteNote(note);
        }
    }
    // EFFECTS: sets title of selected note to user input
    public void editNoteTitle(Note note) {
        String newTitle = input.promptInput("Enter new title: ");
        note.setTitle(newTitle);
    }

    // EFFECTS: sets content of selected note to user input
    public void editNoteContent(Note note) {
        String newContent = input.promptInput("Enter new content: ");
        note.setTitle(newContent);
    }

    // EFFECTS: ends the program
    public void end() {
        running = false;
    }
}
