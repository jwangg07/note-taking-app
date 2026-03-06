package ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONObject;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;
import model.NoteBook;
import persistance.ReadJson;
import persistance.WriteJson;

// Represents the GUI of the notes application
@ExcludeFromJacocoGeneratedReport
public class NoteApp extends JFrame implements ActionListener {

    private final String filePath = "data/noteBook.json";
    private NoteBook notebook;
    private boolean loaded;
    private boolean saved;
    private ReadJson readjson;
    private WriteJson writejson;
    private LinkedList<String> notifications;

    private GridBagConstraints c = new GridBagConstraints();
    private Helpers helper = new Helpers(this);
    private final int WIDTH = 1200;
    private final int HEIGHT = 900;
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);
    private JPanel workspace = new JPanel();
    private JPanel notificationPanel = new JPanel();

    // EFFECTS: Initializes the application with new notebook and input handler
    public NoteApp() {
        super("Notebook");
        notebook = new NoteBook();
        loaded = false;
        saved = false;
        readjson = new ReadJson(filePath);
        writejson = new WriteJson(filePath);
        notifications = new LinkedList<String>();

        // GUI SETUP
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new GridBagLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        drawNoteBook();
    }

    private void drawNotifications() {
        notificationPanel.removeAll();

        JLabel welcomeMessage = helper.createLabel("Welcome to your note app!", NOTE_COLOR, 16);
        notificationPanel.add(welcomeMessage);

        if (notifications.size() > 5) {
            notifications.removeLast();
        }

        for (int i = 0; i < notifications.size(); i++) {
            Color notificationColor = NOTE_COLOR;
            for (int j = 0; j < i; j++) {
                notificationColor = notificationColor.darker();
            }
            JLabel notificationLabel = helper.createLabel(notifications.get(i), notificationColor, 12);
            notificationPanel.add(notificationLabel);
        }

        notificationPanel.revalidate();
        notificationPanel.repaint();
    }

    // EFFECTS: draws all background elements: buttons top right, notifications top
    // left
    private void drawNoteBook() {
        workspace.setBackground(BACKGROUND_COLOR);
        workspace.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        workspace.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setGridBagConstraints(0, 1, 2, GridBagConstraints.BOTH, 1, 1, GridBagConstraints.CENTER);
        add(workspace, c);

        // NOTIFICATIONS
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        notificationPanel.setBackground(BACKGROUND_COLOR);
        notificationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (!loaded && !saved) {
            notifications.addFirst("You have a notebook saved!");
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JLabel saveAvailable = helper.createLabel("You have unsaved changes!", NOTE_COLOR.darker(), 16);
            notificationPanel.add(saveAvailable);
        }
        setGridBagConstraints(0, 0, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_START);

        drawNotifications();
        add(notificationPanel, c);

        // BUTTONS
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttons.setBackground(BACKGROUND_COLOR);

        JButton addNoteButton = helper.createButton("Add Note", NOTE_COLOR, "addNote");
        buttons.add(addNoteButton);

        if (!loaded && !saved) {
            JButton loadButton = helper.createButton("Load Notes", NOTE_COLOR, "loadNotes");
            buttons.add(loadButton);
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JButton saveAvailable = helper.createButton("Save Notes", NOTE_COLOR, "saveNotes");
            buttons.add(saveAvailable);
        }

        setGridBagConstraints(1, 0, 1, GridBagConstraints.NONE, 1, 0, GridBagConstraints.FIRST_LINE_END);
        add(buttons, c);

        revalidate();
        repaint();
    }

    // EFFECTS: Modifies GridBagConstraints based on parameters
    private void setGridBagConstraints(int beginX, int beginY, int widthSpan, int fill, int weightX, int weightY,
            int anchor) {
        c.gridx = beginX;
        c.gridy = beginY;
        c.gridwidth = widthSpan;
        c.fill = fill;
        c.weightx = weightX;
        c.weighty = weightY;
        c.anchor = anchor;
    }

    // EFFECTS: calls methods based on user interaction
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addNote")) {
            createNote();
        } else if (e.getActionCommand().equals("loadNotes")) {
            loadNoteBook();
        } else if (e.getActionCommand().equals("saveNotes")) {
            saveNoteBook();
        }
    }

    // EFFECTS: returns true if the current notebook is the same as the saved JSON
    // file, false otherwise
    private boolean compareNoteBookToFile() {
        JSONObject fileJson;
        try {
            fileJson = readjson.toJsonObject();
            return notebook.toJson().similar(fileJson);
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filePath);
        }
        return false;
    }

    // EFFECTS: displays each note in notebook as a box with title and content
    private void displayNotes() {
        workspace.removeAll();
        for (Note note : notebook.getAllNotes()) {
            JPanel noteContainer = new JPanel();
            noteContainer.setLayout(null);
            noteContainer.setPreferredSize(new Dimension(200, 200));
            noteContainer.setBackground(NOTE_COLOR);

            JLabel title = helper.createLabel(note.getTitle(), Color.BLACK, 18);
            title.setBounds(10, 10, 180, 20);
            noteContainer.add(title);

            // Code adapted from Stack Overflow:
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
                displayNote(note);
            });

            workspace.add(noteContainer);
        }
        workspace.revalidate();
        workspace.repaint();
    }

    // EFFECTS: create a popup prompting user for title and content of the note and
    // adds note to notebook
    private void createNote() {
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

        JTextArea contentTextArea = helper.createTextArea("", Color.BLACK, NOTE_COLOR);
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
            notebook.addNote(note);
            JOptionPane.showMessageDialog(newNote, "Note Created!");
            createNotification("Note \'" + title + "\' Created!");
            newNote.dispose();
            displayNotes();
        });
    }

    // EFFECTS: displays title and content of a note, prompts user for commands in
    // the note
    public void displayNote(Note note) {
        JDialog noteView = new JDialog(this, note.getTitle(), false);
        noteView.setSize(400, 400);
        noteView.setLayout(null);
        noteView.getContentPane().setBackground(NOTE_COLOR);

        noteView.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        noteView.setLocationRelativeTo(this);
        noteView.setVisible(true);

        JTextArea title = helper.createTextArea(note.getTitle(), Color.BLACK, NOTE_COLOR);
        title.setBounds(20, 20, 400, 25);
        noteView.add(title);

        JTextArea content = helper.createTextArea(note.getContent(), Color.BLACK, NOTE_COLOR);
        content.setBounds(20, 60, 350, 200);
        noteView.add(content);

        // Code Adapted from Stack Overflow:
        // https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        noteView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                note.setTitle(title.getText());
                note.setContent(content.getText());
                displayNotes();
            }
        });

        JButton deleteButton = helper.createButton("delete note", BACKGROUND_COLOR, null);
        deleteButton.setBounds(10, 330, 120, 30);
        deleteButton.setForeground(NOTE_COLOR);
        noteView.add(deleteButton);

        deleteButton.addActionListener(event -> {
            deleteNote(note);
            JOptionPane.showMessageDialog(noteView, "Note Deleted!");
            createNotification("Note \'" + note.getTitle() + "\' Deleted!");
            noteView.dispose();
            displayNotes();
        });
    }

    // MODIFIES: this
    // EFFECTS: adds a notification to notifications and draws notification panel
    private void createNotification(String message) {
        notifications.addFirst(message);
        drawNotifications();
    }

    // MODIFIES: notebook
    // EFFFECTS: removes given note from notebook
    private void deleteNote(Note note) {
        notebook.deleteNote(note);
    }

    // EFFECTS: saves the notebook to a JSON file
    private void saveNoteBook() {
        try {
            writejson.write(notebook);
            saved = true;
            System.out.println("Saved notebook to " + filePath);
            createNotification("Saved notebook to " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write notebook to file " + filePath);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads notebook from JSON file
    private void loadNoteBook() {
        try {
            notebook = readjson.read();
            loaded = true;
            createNotification("Loaded notebook from " + filePath);
            displayNotes();
        } catch (IOException e) {
            System.out.println("Unable to read file " + filePath);
        }
    }
}
