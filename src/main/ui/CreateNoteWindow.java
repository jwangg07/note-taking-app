package ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;

// Represents the GUI for a window to create a new note
@ExcludeFromJacocoGeneratedReport
public class CreateNoteWindow extends JDialog {
    NoteApp app;
    NotificationPanel notificationPanel;
    WorkspacePanel workspacePanel;
    private Helpers helper = new Helpers();
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);

    // Creates a new window connecting to NoteApp, NotificationPanel, and
    // WorkspacePanel
    public CreateNoteWindow(NoteApp app, NotificationPanel notificationPanel, WorkspacePanel workspacePanel) {
        this.app = app;
        this.notificationPanel = notificationPanel;
        this.workspacePanel = workspacePanel;
    }

    // EFFECTS: create a popup prompting user for title and content of the note and
    // adds note to notebook
    public void createNote() {
        JDialog newNote = new JDialog(this, "Create Note", false);
        newNote.setSize(400, 300);
        newNote.setLayout(null);
        newNote.getContentPane().setBackground(NOTE_COLOR);

        newNote.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        newNote.setLocationRelativeTo(this);
        newNote.setVisible(true);

        JLabel titleLabel = helper.createLabel("Title:", Color.BLACK, 12);
        titleLabel.setBounds(20, 20, 50, 25);
        newNote.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBackground(NOTE_COLOR);
        titleField.setBounds(80, 20, 290, 25);
        titleField.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        newNote.add(titleField);

        JLabel contentLabel = helper.createLabel("Content:", Color.black, 12);
        contentLabel.setBounds(20, 60, 60, 25);
        newNote.add(contentLabel);

        JTextArea contentTextArea = helper.createTextArea("", Color.BLACK, NOTE_COLOR, 11);
        contentTextArea.setBounds(20, 90, 350, 120);
        contentTextArea.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        newNote.add(contentTextArea);

        JButton createButton = helper.createButton("Create Note", BACKGROUND_COLOR, null);
        createButton.setBounds(250, 220, 120, 30);
        createButton.setForeground(Color.WHITE);
        newNote.add(createButton);

        // Code adapted from stack overflow:
        // https://stackoverflow.com/questions/62093192/java-dynamically-create-buttons-and-pass-a-parameter-to-action-performed#:~:text=For%20Swing%2C%20you'd%20need,link%20CC%20BY%2DSA%204.0
        createButton.addActionListener(event -> {
            String title = titleField.getText();
            String content = contentTextArea.getText();
            Note note = new Note(title);
            note.setContent(content);
            app.getNoteBook().addNote(note);
            JOptionPane.showMessageDialog(newNote, "Note Created!");
            notificationPanel.createNotification("Note \'" + title + "\' Created!");
            newNote.dispose();
            app.filterNotes();
        });
    }
}
