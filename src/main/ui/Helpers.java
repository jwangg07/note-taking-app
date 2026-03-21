package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Represents helper methods used by various classes
@ExcludeFromJacocoGeneratedReport
public class Helpers {

    NoteApp app;

    // EFFECTS: creates an instance of helper class associated to NoteApp
    public Helpers(NoteApp app) {
        this.app = app;
    }

    // EFFECTS: creates an instance of helper class
    public Helpers() {
    }

    // EFFECTS: Creates a JLabel based on given parameters and returns it
    public JLabel createLabel(String text, Color textColor, int textSize) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(new Font("Default", Font.PLAIN, textSize));
        return label;
    }

    // EFFECTS: creates a JButton based on the given parameters and returns it
    public JButton createButton(String text, Color bgColor, String command) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setActionCommand(command);
        return button;
    }

    // EFFECTS: Creates a JTextArea based on parameters and returns it
    public JTextArea createTextArea(String text, Color textColor, Color bgColor, int textSize) {
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("Default", Font.PLAIN, textSize));
        textArea.setForeground(textColor);
        textArea.setBackground(bgColor);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }
}
