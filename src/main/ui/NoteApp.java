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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

        frame.setBackground(Color.DARK_GRAY);

        // Top Left
        JPanel notifications = new JPanel();
        notifications.setLayout(new BoxLayout(notifications, BoxLayout.PAGE_AXIS));
        notifications.setBackground(BACKGROUND_COLOR);
        notifications.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeMessage = new JLabel("Welcome to your note app!");
        welcomeMessage.setForeground(NOTE_COLOR);
        welcomeMessage.setFont(new Font("Default", Font.PLAIN, 16));
        notifications.add(welcomeMessage);

        if (!loaded && !saved) {
            // System.out.println("You have a notebook saved! 'load' to load in notebook.");
            JLabel loadAvailable = new JLabel("You have a notebook saved!");
            loadAvailable.setForeground(NOTE_COLOR.darker());
            loadAvailable.setFont(new Font("Default", Font.PLAIN, 16));
            notifications.add(loadAvailable);
        }
        
        if (!compareNoteBookToFile() && (loaded || saved) ||
        !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            // System.out.println("You have unsaved changes! 'save' to save to file.");
            JLabel saveAvailable = new JLabel("You have unsaved changes!");
            saveAvailable.setForeground(NOTE_COLOR.darker());
            saveAvailable.setFont(new Font("Default", Font.PLAIN, 16));
            notifications.add(saveAvailable);
        }

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0;
        c.weighty = 0;
        frame.add(notifications, c);

        // Top Right
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
            // System.out.println("You have a notebook saved! 'load' to load in notebook.");
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
            // System.out.println("You have unsaved changes! 'save' to save to file.");
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
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 1;
        c.weighty = 0;
        frame.add(buttons, c);

        // Rest of space
        JPanel workspace = new JPanel();
        workspace.setBackground(Color.DARK_GRAY);
        workspace.setLayout(new BoxLayout(workspace, BoxLayout.PAGE_AXIS));
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 1;
        c.weighty = 1;
        frame.add(workspace, c);
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
        String title = input.promptInput("Create a title for this note: ");
        while (!checkValidTitle(title)) {
            title = input.promptInput("Create a title for this note: ");
        }
        Note note = new Note(title);
        String content = input.promptInput("Write the content for this note: ");
        note.setContent(content);

        notebook.addNote(note);
        System.out.println("Note Created!");
        editor.displayNote(note);
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

    // EFFECTS: returns true if title doesn't conflict with commands,
    // false otherwise
    private boolean checkValidTitle(String title) {
        List<String> commands = new ArrayList<String>();
        commands.add("a");
        commands.add("s");
        commands.add("q");
        commands.add("t");
        commands.add("c");
        commands.add("d");
        commands.add("b");
        commands.add("load");
        commands.add("save");

        if (commands.contains(title)) {
            System.out.println("Given title conflicts with a command");
            return false;
        }
        return true;
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
