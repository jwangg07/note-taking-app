package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Represents the GUI of the buttons section of the application
@ExcludeFromJacocoGeneratedReport
public class ButtonsPanel extends JPanel implements ActionListener {

    NoteApp app;
    private Helpers helper = new Helpers();
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);

    // EFFECTS: Creates a buttons panel associated to NoteApp, with default panel
    // settings
    public ButtonsPanel(NoteApp app) {
        this.app = app;
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        setBackground(BACKGROUND_COLOR);
    }

    // EFFECTS: Creates buttons to add, load, and save notes and adds to panel
    public void drawButtons() {
        JButton addNoteButton = helper.createButton("Add Note", NOTE_COLOR, "addNote");
        addNoteButton.addActionListener(this);
        add(addNoteButton);

        JButton loadButton = helper.createButton("Load Notes", NOTE_COLOR, "loadNotes");
        loadButton.addActionListener(this);
        add(loadButton);

        JButton saveBUtton = helper.createButton("Save Notes", NOTE_COLOR, "saveNotes");
        saveBUtton.addActionListener(this);
        add(saveBUtton);
    }

    // EFFECTS: calls respective methods to NoteApp based on user button press
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addNote")) {
            app.createNote();
        } else if (e.getActionCommand().equals("loadNotes")) {
            app.loadNoteBook();
        } else if (e.getActionCommand().equals("saveNotes")) {
            app.saveNoteBook();
        }
    }
}
