package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Represents the GUI of the buttons section of the application
@ExcludeFromJacocoGeneratedReport
public class ButtonsPanel extends JPanel implements ActionListener {

    NoteApp app;
    private Helpers helper = new Helpers();
    private static final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private static final Color NOTE_COLOR = new Color(255, 235, 161);
    private String searchBarValue;

    // EFFECTS: Creates a buttons panel associated to NoteApp, with default panel
    // settings
    public ButtonsPanel(NoteApp app) {
        this.app = app;
        searchBarValue = "";
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        setBackground(BACKGROUND_COLOR);
        drawSearchBar();
        drawButtons();
    }

    // EFFECTS: Creates buttons to add, load, and save notes and adds to panel
    public void drawButtons() {
        JButton addNoteButton = helper.createButton("Add Note", NOTE_COLOR, "addNote");
        addNoteButton.addActionListener(this);
        add(addNoteButton);

        JButton loadButton = helper.createButton("Load Notes", NOTE_COLOR, "loadNotes");
        loadButton.addActionListener(this);
        add(loadButton);

        JButton saveButton = helper.createButton("Save Notes", NOTE_COLOR, "saveNotes");
        saveButton.addActionListener(this);
        add(saveButton);

        JButton backgroundButton = helper.createButton("Change Background", NOTE_COLOR, "changeBackground");
        backgroundButton.addActionListener(this);
        add(backgroundButton);
    }

    // EFFECTS: Creates a search bar and adds to panel,
    // User inputs filters the displayed notes by matching prefixes
    public void drawSearchBar() {
        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(75, 25));
        searchBar.setBackground(NOTE_COLOR);
        searchBar.setBorder(null);

        // Code inspired from Stack Overflow:
        // https://stackoverflow.com/questions/7740465/text-changed-event-in-jtextarea-how-to
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                searchBarValue = searchBar.getText();
                app.filterNotes();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchBarValue = searchBar.getText();
                app.filterNotes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchBarValue = searchBar.getText();
                app.filterNotes();
            }
        });
        add(searchBar);
    }

    public String getSearchBarValue() {
        return searchBarValue;
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
        } else if (e.getActionCommand().equals("changeBackground")) {
            app.getWorkspacePanel().changeBackground();
        }
    }
}
