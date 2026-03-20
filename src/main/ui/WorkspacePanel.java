package ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
    private boolean showBackground;

    // Creates a workspace panel associated to NoteApp, with a background, flow
    // layout, and orientation of items inside from left to right
    public WorkspacePanel(NoteApp app) {
        this.app = app;
        setBackground(BACKGROUND_COLOR);
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        showBackground = false;
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

    // MODIFIES: this
    // EFFECTS: changes the state of whether the background is shown or not, and updates JPanel
    public void changeBackground() {
        if (showBackground) {
            showBackground = false;
        } else {
            showBackground = true;
        }
        revalidate();
        repaint();
    }
    
    // Code inspired from Stack Overflow: https://stackoverflow.com/questions/19125707/simplest-way-to-set-image-as-jpanel-background 
    // EFFECTS: updates background with respective image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Image backgroundImage = getBackgroundImage();
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    // EFFECTS: returns the background image to display given state of showBackground
    private Image getBackgroundImage() {
        Image backgroundImage = null;
        try {
            if (showBackground) {
                backgroundImage = ImageIO.read(new File("img\\backgroundImage.png")); 
            } else {
                backgroundImage = ImageIO.read(new File("img\\default.png")); 
            }
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
        return backgroundImage;
    }
}
