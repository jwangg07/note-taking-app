package ui;

import java.util.ArrayList;
import java.util.List;

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
            input.parseInputMain(input.promptInput(""), this);
        }
    }

    // EFFECTS: prints initial instructions to use notebook
    public void printInstructions() {
        for(int i = 0; i < 2; i++) System.out.println("");
        System.out.println("Welcome to your note app!");
        if (notebook.getAllNotes().isEmpty()) {
            System.out.println("You currently do not have any notes.");
            System.out.println("");
        } else {
            System.out.println("Your notes:");
            displayNotes();
            System.out.println("");
            System.out.println("s to select a note");
        }
        System.out.println("a to add a new note");
        System.out.println("q to quit");
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
        while (!checkValidTitle(title)) {
            title = input.promptInput("Create a title for this note: ");
        }
        Note note = new Note(title);
        String content = input.promptInput("Write the content for this note: ");
        note.setContent(content);

        notebook.addNote(note);
        System.out.println("Note Created!");
        displayNote(note);
    }

    // EFFECTS: handle input selecting note by title
    public void selectNote() {
        String noteTitle = input.promptInput("Enter note title (or 'b' to back): ");
        Note note = notebook.getNote(noteTitle);
        if (noteTitle.equals("b")) {
            printInstructions();
        } else if (note != null) {
            displayNote(note);
        } else {
            System.out.println("Note with the name " + noteTitle + " was not found.");
            selectNote();
        }
    }

    // EFFECTS: displays title and content of a note, prompts user for commands in the note
    public void displayNote(Note note) {
        insertLine();
        System.out.println("Currently viewing Note");
        System.out.println("Title: " + note.getTitle());
        System.out.println(note.getContent());
        
        System.out.println("");

        System.out.println("t to edit note title");
        System.out.println("c to edit note content");
        System.out.println("d to delete note");
        System.out.println("b to go back");

        input.parseInputNote(input.promptInput(""), note, this);
    }

    // EFFECTS: prompts user for confirmation and deletes note from notebook
    public void deleteNote(Note note) {
        String confirmation = input.promptInput("Enter note title (\"" + note.getTitle() + "\") to confirm deletion (or 'b' to back): ");
        if (confirmation.equals("b")) {
            displayNote(note);
        } else if (confirmation.equals(note.getTitle())) {
            notebook.deleteNote(note);
            System.out.println("Note Deleted!");
            printInstructions();
        } else {
            System.out.println("title does not match");
            deleteNote(note);
        }
    }

    // EFFECTS: sets title of selected note to user input
    public void editNoteTitle(Note note) {
        String newTitle = input.promptInput("Enter new title: ");
        while (!checkValidTitle(newTitle)) {
            newTitle = input.promptInput("Enter new title: ");
        }
        note.setTitle(newTitle);
        System.out.println("Title set!");
        displayNote(note);
    }

    // EFFECTS: sets content of selected note to user input
    public void editNoteContent(Note note) {
        String newContent = input.promptInput("Enter new content: ");
        note.setContent(newContent);
        System.out.println("Content set!");
        displayNote(note);
    }

    // EFFECTS: returns true if title doesn't conflict with commands, false otherwise
    private boolean checkValidTitle(String title) {
        List<String> commands = new ArrayList<String>();
        commands.add("a");
        commands.add("s");
        commands.add("q");
        commands.add("t");
        commands.add("c");
        commands.add("d");
        commands.add("b");

        if (commands.contains(title)) {
            System.out.println("Given title conflicts with a command");
            return false;
        } 
        return true;
    }

    // EFFECTS: inserts a horizontal line
    public void insertLine() {
        System.out.println("----------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: sets program running to false
    public void end() {
        running = false;
    }
}
