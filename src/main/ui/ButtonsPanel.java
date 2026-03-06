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
    private boolean loaded;
    private boolean saved;

    public ButtonsPanel(NoteApp app) {
        this.app = app;
        loaded = app.getLoaded();
        saved = app.getSaved();
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        setBackground(BACKGROUND_COLOR);
    }

    public void drawButtons() {
        JButton addNoteButton = helper.createButton("Add Note", NOTE_COLOR, "addNote");
        addNoteButton.addActionListener(this);
        add(addNoteButton);

        if (!loaded && !saved) {
            JButton loadButton = helper.createButton("Load Notes", NOTE_COLOR, "loadNotes");
            loadButton.addActionListener(this);
            add(loadButton);
        }

        if (!app.compareNoteBookToFile() && (loaded || saved) ||
                !app.getNoteBook().getAllNotes().isEmpty() && !loaded && !saved) {
            JButton saveAvailable = helper.createButton("Save Notes", NOTE_COLOR, "saveNotes");
            saveAvailable.addActionListener(this);
            add(saveAvailable);
        }
    }

    // EFFECTS: calls methods based on user interaction
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
