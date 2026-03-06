package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Helpers implements ActionListener {

    NoteApp app;

    public Helpers(NoteApp app) {
        this.app = app;
    }
    // EFFECTS: calls methods based on user interaction
    public void actionPerformed(ActionEvent e) {
        app.actionPerformed(e);
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
        button.addActionListener(this);
        return button;
    }

    // EFFECTS: Creates a JTextArea based on parameters and returns it
    public JTextArea createTextArea(String text, Color textColor, Color bgColor) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setForeground(textColor);
        return textArea;
    }
    
}
