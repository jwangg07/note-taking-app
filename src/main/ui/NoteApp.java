package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import org.json.JSONObject;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;
import model.NoteBook;
import persistance.ReadJson;
import persistance.WriteJson;

// Represents the ui of the notes application
@ExcludeFromJacocoGeneratedReport
public class NoteApp implements ActionListener {

    private final String filePath = "data/noteBook.json";
    private NoteBook notebook;
    private InputHandler input;
    private boolean running;
    private boolean loaded;
    private boolean saved;
    private ReadJson readjson;
    private WriteJson writejson;
    private Editor editor;

    private JFrame frame = new JFrame("Notebook");
    private GridBagConstraints c = new GridBagConstraints();
    private final int WIDTH = 1200;
    private final int HEIGHT = 900;
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);
    private JPanel workspace = new JPanel();

    // EFFECTS: Initializes the application with new notebook and input handler
    public NoteApp() {
        running = true;
        input = new InputHandler();
        notebook = new NoteBook();
        loaded = false;
        saved = false;
        readjson = new ReadJson(filePath);
        writejson = new WriteJson(filePath);
        editor = new Editor(notebook);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        frame.setLayout(new GridBagLayout());

        drawNoteBook();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    // EFFECTS: run application until user quits
    public void run() {
        while (running) {
            // printInstructions();
            String command = input.promptInput("");
            handleCommands(command);
        }
    }

    // EFFECTS: draws all background elements: buttons top right, notifications top
    // left
    private void drawNoteBook() {
        workspace.setBackground(BACKGROUND_COLOR);
        workspace.setLayout(null);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        frame.add(workspace, c);

        // NOTIFICATIONS
        JPanel notifications = new JPanel();
        notifications.setLayout(new BoxLayout(notifications, BoxLayout.Y_AXIS));
        notifications.setBackground(BACKGROUND_COLOR);
        notifications.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeMessage = new JLabel("Welcome to your note app!");
        welcomeMessage.setForeground(NOTE_COLOR);
        welcomeMessage.setFont(new Font("Default", Font.PLAIN, 16));
        notifications.add(welcomeMessage);

        if (!loaded && !saved) {
            JLabel loadAvailable = new JLabel("You have a notebook saved!");
            loadAvailable.setForeground(NOTE_COLOR.darker());
            loadAvailable.setFont(new Font("Default", Font.PLAIN, 16));
            notifications.add(loadAvailable);
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JLabel saveAvailable = new JLabel("You have unsaved changes!");
            saveAvailable.setForeground(NOTE_COLOR.darker());
            saveAvailable.setFont(new Font("Default", Font.PLAIN, 16));
            notifications.add(saveAvailable);
        }

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.weighty = 0;
        frame.add(notifications, c);

        // BUTTONS
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttons.setBackground(BACKGROUND_COLOR);

        JButton addNoteButton = new JButton("Add Note");
        addNoteButton.setBackground(NOTE_COLOR);
        addNoteButton.setBorderPainted(false);
        addNoteButton.setFocusPainted(false);
        addNoteButton.setActionCommand("addNote");
        addNoteButton.addActionListener(this);
        buttons.add(addNoteButton);

        if (!loaded && !saved) {
            JButton loadButton = new JButton("Load Notes");
            loadButton.setBackground(NOTE_COLOR);
            loadButton.setBorderPainted(false);
            loadButton.setFocusPainted(false);
            loadButton.setActionCommand("loadNotes");
            loadButton.addActionListener(this);
            buttons.add(loadButton);
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JButton saveAvailable = new JButton("Save Notes");
            saveAvailable.setBackground(NOTE_COLOR);
            saveAvailable.setBorderPainted(false);
            saveAvailable.setFocusPainted(false);
            saveAvailable.setActionCommand("saveNotes");
            saveAvailable.addActionListener(this);
            buttons.add(saveAvailable);
        }

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 1;
        c.weighty = 0;
        frame.add(buttons, c);
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

    // EFFECTS: calls methods based on commands from user
    private void handleCommands(String command) {
        switch (command) {
            case "a":
                createNote();
                break;
            case "s":
                selectNote();
                break;
            case "q":
                end();
                break;
            case "load":
                loadNoteBook();
                break;
            case "save":
                saveNoteBook();
                break;
            default:
                System.out.println("I didn't understand the command: " + command);
                break;
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
        for (Note note : notebook.getAllNotes()) {
            System.out.println(note.getTitle());
        }
    }

    // EFFECTS: create a popup prompting user for title and content of the note and
    // adds note to notebook
    private void createNote() {
        JDialog newNote = new JDialog(frame, "Create Note", false);
        newNote.setSize(400, 300);
        newNote.setLayout(null);
        newNote.getContentPane().setBackground(NOTE_COLOR);

        newNote.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        newNote.setLocationRelativeTo(frame);
        newNote.setVisible(true);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(20, 20, 50, 25);
        newNote.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBackground(NOTE_COLOR);
        titleField.setBounds(80, 20, 290, 25);
        titleField.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        newNote.add(titleField);

        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setForeground(Color.BLACK);
        contentLabel.setBounds(20, 60, 60, 25);
        newNote.add(contentLabel);

        JTextArea contentTextArea = new JTextArea();
        contentTextArea.setBounds(20, 90, 350, 120);
        contentTextArea.setBackground(NOTE_COLOR);
        contentTextArea.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        newNote.add(contentTextArea);

        JButton createButton = new JButton("Create Note");
        createButton.setBounds(250, 220, 120, 30);
        createButton.setForeground(Color.WHITE);
        createButton.setBackground(BACKGROUND_COLOR);
        createButton.setBorderPainted(false);
        createButton.setFocusPainted(false);
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
            newNote.dispose();
        });
    }

    // EFFECTS: handle input selecting note by mouseclick position
    private void selectNote() {
        String noteTitle = input.promptInput("Enter note title (or 'b' to back): ");
        Note note = notebook.getNote(noteTitle);
        if (note != null) {
            editor.displayNote(note);
        } else {
            System.out.println("Note with the name " + noteTitle + " was not found.");
            selectNote();
        }
    }

    // EFFECTS: saves the notebook to a JSON file
    private void saveNoteBook() {
        try {
            writejson.write(notebook);
            System.out.println("Saved notebook to " + filePath);
            saved = true;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write notebook to file " + filePath);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads notebook from JSON file
    private void loadNoteBook() {
        try {
            notebook = readjson.read();
            System.out.println("Loaded notebook from " + filePath);
            editor.setNoteBook(notebook);
            loaded = true;
        } catch (IOException e) {
            System.out.println("Unable to read file " + filePath);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets program running to false
    private void end() {
        running = false;
    }
}
