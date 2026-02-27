package ui;

import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Represents the inputs made by the user
@ExcludeFromJacocoGeneratedReport
public class InputHandler {

    private Scanner scanner;

    // EFFECTS: creates InputHandler with scanner
    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    // EFFECTS: asks user for input with a message and returns user input
    public String promptInput(String message) {
        System.out.print(message);
        return getUserInput();
    }

    // EFFECTS: returns user input
    private String getUserInput() {
        return scanner.nextLine().strip();
    }
}
