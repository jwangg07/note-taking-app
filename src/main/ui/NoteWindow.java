package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;

// Represents the GUI for an individual Note viewer and editor
@ExcludeFromJacocoGeneratedReport
public class NoteWindow extends JDialog {

    NoteApp app;
    private Helpers helper = new Helpers();
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);
    private final int WIDTH = 400;
    private final int HEIGHT = 400;

    // Creates a new window with default settings connected to NoteApp, displaying
    // Note
    public NoteWindow(NoteApp app, Note note) {
        super(app, note.getTitle(), false);
        this.app = app;
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        getContentPane().setBackground(NOTE_COLOR);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(app);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: displays title and content of a note, the user can edit title and
    // content
    public void displayNote(Note note) {
        JTextArea title = helper.createTextArea(note.getTitle(), Color.BLACK, NOTE_COLOR, 20);
        title.setBounds(20, 20, 400, 25);
        add(title);

        JTextArea content = helper.createTextArea(note.getContent(), Color.BLACK, NOTE_COLOR, 12);
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBounds(20, 60, 350, 250);
        scroll.setBorder(null);
        // Line 55 from stack overflow: https://stackoverflow.com/questions/2648585/jscrollpane-without-scrollbars 
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        add(scroll);

        // Code inspired from Stack Overflow:
        // https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateNote(note, title, content);
            }
        });

        JButton deleteButton = helper.createButton("delete note", BACKGROUND_COLOR, null);
        deleteButton.setBounds(WIDTH-150, HEIGHT-80, 120, 30);
        deleteButton.setForeground(Color.WHITE);
        add(deleteButton);

        deleteButton.addActionListener(event -> {
            deleteNote(note);
        });
    }

    // MODIFIES: note
    // EFFECTS: updates note title and content with text from JTextAreas
    public void updateNote(Note note, JTextArea title, JTextArea content) {
        note.setTitle(title.getText());
        note.setContent(content.getText());
        app.getWorkspacePanel().displayNotes();
        if (!app.compareNoteBookToFile()) {
            app.getNotificationPanel().createNotification("You have unsaved changes!");
        }
    }

    // MODIFIES: notebook
    // EFFECTS: removes the given note from notebook
    public void deleteNote(Note note) {
        app.getNotificationPanel().createNotification("Note \'" + note.getTitle() + "\' Deleted!");
        app.getNoteBook().deleteNote(note);
        JOptionPane.showMessageDialog(this, "Note Deleted!");
        dispose();
        app.getWorkspacePanel().displayNotes();
    }
}
