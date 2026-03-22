package ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;

// Represents the GUI for a window to create a new note
@ExcludeFromJacocoGeneratedReport
public class CreateNoteWindow extends JDialog {

    private NoteApp app;
    private Helpers helper = new Helpers();
    private JTextArea titleField;
    private JTextArea contentField;
    private static final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private static final Color NOTE_COLOR = new Color(255, 235, 161);
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    // create a popup prompting user for title and content of the note and
    // adds note to notebook
    public CreateNoteWindow(NoteApp app) {
        super(app, "Create Note", false);
        this.app = app;
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        getContentPane().setBackground(NOTE_COLOR);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(app);
        setVisible(true);
        setResizable(false);

        drawTexts();
        drawFields();
        drawButton();
    }

    // EFFECTS: draws the title and content text labels for the popup
    private void drawTexts() {
        JLabel titleLabel = helper.createLabel("Title:", Color.BLACK, 12);
        titleLabel.setBounds(20, 20, 50, 25);
        add(titleLabel);

        JLabel contentLabel = helper.createLabel("Content:", Color.black, 12);
        contentLabel.setBounds(20, 60, 60, 25);
        add(contentLabel);
    }

    // MODIFIES: this
    // EFFECTS: draws the title and content field components for the popup.
    private void drawFields() {
        titleField = helper.createTextArea("", Color.BLACK, NOTE_COLOR, 11);
        titleField.setBounds(60, 25, WIDTH-90, 20);
        titleField.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        add(titleField);

        contentField = helper.createTextArea("", Color.BLACK, NOTE_COLOR, 11);
        contentField.setBounds(20, 90, WIDTH-50, HEIGHT-180);
        contentField.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        add(contentField);
    }

    // EFFECTS: draws the create note button with action listener to create note
    private void drawButton() {
        JButton createButton = helper.createButton("Create Note", BACKGROUND_COLOR, null);
        createButton.setBounds(WIDTH-150, HEIGHT-80, 120, 30);
        createButton.setForeground(Color.WHITE);
        add(createButton);

        // Code adapted from stack overflow:
        // https://stackoverflow.com/questions/62093192/java-dynamically-create-buttons-and-pass-a-parameter-to-action-performed#:~:text=For%20Swing%2C%20you'd%20need,link%20CC%20BY%2DSA%204.0
        createButton.addActionListener(event -> {
            String title = titleField.getText();
            String content = contentField.getText();
            Note note = new Note(title);
            note.setContent(content);
            app.getNoteBook().addNote(note);

            app.getNotificationPanel().createNotification("Note \'" + title + "\' Created!");
            dispose();
            app.filterNotes();
        });
    }
}
