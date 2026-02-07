package ui;

import java.util.Scanner;

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

    public void parseInputMain(String input, NoteApp app) {
        switch (input) {
            case "a": // add note
                // stub
                break;
            case "s": // select note
                // stub
                break;
            case "q": // quit
                // stub
                break;
            default:
                break;
        }
    }

    public void parseInputNote(String input, NoteApp app) {
        switch (input) {
            case "t": // edit title
                // stub
                break;
            case "c": // edit content
                // stub
                break;
            case "d": // delete note
                // stub
                break;
            case "b": // back
                // stub
                break;
            default:
                break;
        }
    }
}
