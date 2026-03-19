package ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;

// Represents the GUI of the main notes section of the application
@ExcludeFromJacocoGeneratedReport
public class WorkspacePanel extends JPanel {

    NoteApp app;
    private Helpers helper = new Helpers();
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);

    // Creates a workspace panel associated to NoteApp, with a background, flow
    // layout, and orientation of items inside from left to right
    public WorkspacePanel(NoteApp app) {
        this.app = app;
        setBackground(BACKGROUND_COLOR);
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    }

    // EFFECTS: redraws each note in notebook as a box displaying the title and
    // content, the user may click on a specific note to view and edit the note
    public void displayNotes() {
        removeAll();
        for (Note note : app.getVisibleNoteBook().getAllNotes()) {
            JPanel noteContainer = new JPanel();
            noteContainer.setLayout(null);
            noteContainer.setPreferredSize(new Dimension(200, 200));
            noteContainer.setBackground(NOTE_COLOR);

            JLabel title = helper.createLabel(note.getTitle(), Color.BLACK, 20);
            title.setBounds(10, 10, 180, 20);
            noteContainer.add(title);

            // Code inspired from Stack Overflow:
            // https://stackoverflow.com/questions/2420742/make-a-jlabel-wrap-its-text-by-setting-a-max-width#:~:text=see%20also%20stackoverflow.com/questions,%22%22);
            JLabel content = helper.createLabel("<html>" + note.getContent() + "</html>", Color.BLACK, 12);
            content.setBounds(10, 40, 180, 150);
            content.setVerticalAlignment(SwingConstants.TOP);
            noteContainer.add(content);

            JButton openNoteButton = helper.createButton("", null, null);
            openNoteButton.setBounds(0, 0, 200, 200);
            openNoteButton.setOpaque(false);
            noteContainer.add(openNoteButton);

            openNoteButton.addActionListener(event -> {
                app.displayNote(note);
            });

            add(noteContainer);
        }
        revalidate();
        repaint();
    }
}
