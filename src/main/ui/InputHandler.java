package ui;

import java.util.Scanner;

import model.Note;

// Handles user input
public class InputHandler {

    private Scanner scanner;

    // EFFECTS: creates InputHandler with scanner
    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    // EFFECTS: asks user for input with a message and returns user input
    public String promptInput(String message) {
        System.out.println(message);
        return getUserInput();
    }

    // EFFECTS: returns user input
    private String getUserInput() {
        return scanner.nextLine();
    }

    // EFFECTS: handles user input on main screen while no notes are open, 
    // commands to add note, select note, and quit application
    public void parseInputMain(String input, NoteApp app) {
        switch (input) {
            case "a": // add note
                app.createNote();
                break;
            case "s": // select note
                app.selectNote();
                break;
            case "q": // quit application
                app.end();
                break;
            default:
                System.out.println("I didn't understand your command");
                parseInputMain(promptInput(""), app);
                break;
        }
    }

    // EFFECTS: handles user input while a note is open,
    // commands to edit title, edit content, delete note, and go back to previous page
    public void parseInputNote(String input, Note note, NoteApp app) {
        switch (input) {
            case "t": // edit title
                app.editNoteTitle(note);
                break;
            case "c": // edit content
                app.editNoteContent(note);
                break;
            case "d": // delete note
                app.deleteNote(note);
                break;
            case "b": // back
                app.printInstructions();
                break;
            default:
                System.out.println("I didn't understand your command");
                parseInputNote(promptInput(""), note, app);
                break;
        }
    }
}
