package ui;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;
import model.NoteBook;

@ExcludeFromJacocoGeneratedReport
public class Editor {

    private NoteBook notebook;
    private InputHandler input;

    // EFFECTS: Initializes the note editor in notebook with input handler
    public Editor(NoteBook notebook) {
        this.notebook = notebook;
        input = new InputHandler();
    }

    // EFFECTS: displays title and content of a note, prompts user for commands in
    // the note
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

        String command = input.promptInput("");
        handleCommands(command, note);
    }

    // EFFECTS: calls methods based on commands from user
    private void handleCommands(String command, Note note) {
        switch (command) {
            case "t":
                editNoteTitle(note);
                break;
            case "c":
                editNoteContent(note);
                break;
            case "d":
                deleteNote(note);
                break;
            case "b":
                break;
            default:
                System.out.println("I didn't understand the command: " + command);
                handleCommands(input.promptInput(""), note);
                break;
        }
    }

    // EFFECTS: sets title of selected note to user input
    private void editNoteTitle(Note note) {
        String newTitle = input.promptInput("Enter new title: ");
        while (!checkValidTitle(newTitle)) {
            newTitle = input.promptInput("Enter new title: ");
        }
        note.setTitle(newTitle);
        System.out.println("Title set!");
        displayNote(note);
    }

    // EFFECTS: sets content of selected note to user input
    private void editNoteContent(Note note) {
        String newContent = input.promptInput("Enter new content: ");
        note.setContent(newContent);
        System.out.println("Content set!");
        displayNote(note);
    }

    // EFFECTS: returns true if title doesn't conflict with commands, false
    // otherwise
    private boolean checkValidTitle(String title) {
        List<String> commands = new ArrayList<String>();
        commands.add("a");
        commands.add("s");
        commands.add("q");
        commands.add("t");
        commands.add("c");
        commands.add("d");
        commands.add("b");
        commands.add("load");
        commands.add("save");

        if (commands.contains(title)) {
            System.out.println("Given title conflicts with a command");
            return false;
        }
        return true;
    }

    // EFFECTS: prompts user for confirmation and deletes note from notebook
    private void deleteNote(Note note) {
        String confirmation = input.promptInput("Enter note title (\"" + note.getTitle()
                + "\") to confirm deletion (or 'b' to back): ");
        if (confirmation.equals("b")) {
            displayNote(note);
        } else if (confirmation.equals(note.getTitle())) {
            notebook.deleteNote(note);
            System.out.println("Note Deleted!");
        } else {
            System.out.println("title does not match");
            deleteNote(note);
        }
    }

    // EFFECTS: inserts a horizontal line
    private void insertLine() {
        System.out.println("----------------------------------");
    }

    public void setNoteBook(NoteBook notebook) {
        this.notebook = notebook;
    } 
}
