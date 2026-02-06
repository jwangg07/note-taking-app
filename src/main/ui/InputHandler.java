package ui;

import java.util.Scanner;

public class InputHandler {

    private Scanner scanner;

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

}
