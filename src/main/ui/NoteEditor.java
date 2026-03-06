package ui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;

// Represents the GUI for an individual Note viewer and editor
@ExcludeFromJacocoGeneratedReport
public class NoteEditor extends JDialog {

    NoteApp app;
    NotificationPanel notificationPanel;
    WorkspacePanel workspacePanel;
    private Helpers helper = new Helpers();
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);

    // Creates a new window connecting to NoteApp, NotificationPanel, and WorkspacePanel
    public NoteEditor(NoteApp app, NotificationPanel notificationPanel, WorkspacePanel workspacePanel) {
        this.app = app;
        this.notificationPanel = notificationPanel;
        this.workspacePanel = workspacePanel;
    }

    // EFFECTS: displays title and content of a note, the user can edit title and
    // content, updating the note when closed.
    // User can also press the delete button to delete the note
    public void displayNote(Note note) {
        // JDialog noteView = new JDialog(this, note.getTitle(), false);
        setSize(400, 400);
        setLayout(null);
        getContentPane().setBackground(NOTE_COLOR);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(this);
        setVisible(true);

        JTextArea title = helper.createTextArea(note.getTitle(), Color.BLACK, NOTE_COLOR);
        title.setBounds(20, 20, 400, 25);
        add(title);

        JTextArea content = helper.createTextArea(note.getContent(), Color.BLACK, NOTE_COLOR);
        content.setBounds(20, 60, 350, 200);
        add(content);

        // Code inspired from Stack Overflow:
        // https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                note.setTitle(title.getText());
                note.setContent(content.getText());
                workspacePanel.displayNotes();
            }
        });

        JButton deleteButton = helper.createButton("delete note", BACKGROUND_COLOR, null);
        deleteButton.setBounds(10, 330, 120, 30);
        deleteButton.setForeground(Color.WHITE);
        add(deleteButton);

        deleteButton.addActionListener(event -> {
            app.deleteNote(note);
            JOptionPane.showMessageDialog(this, "Note Deleted!");
            notificationPanel.createNotification("Note \'" + note.getTitle() + "\' Deleted!");
            dispose();
            workspacePanel.displayNotes();
        });
    }
}
