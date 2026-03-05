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
import java.io.FileNotFoundException;
import java.io.IOException;

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

// Represents the GUI of the notes application
@ExcludeFromJacocoGeneratedReport
public class NoteApp implements ActionListener {

    private final String filePath = "data/noteBook.json";
    private NoteBook notebook;
    private boolean loaded;
    private boolean saved;
    private ReadJson readjson;
    private WriteJson writejson;

    private JFrame frame = new JFrame("Notebook");
    private GridBagConstraints c = new GridBagConstraints();
    private final int WIDTH = 1200;
    private final int HEIGHT = 900;
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);
    private JPanel workspace = new JPanel();

    // EFFECTS: Initializes the application with new notebook and input handler
    public NoteApp() {
        notebook = new NoteBook();
        loaded = false;
        saved = false;
        readjson = new ReadJson(filePath);
        writejson = new WriteJson(filePath);

        // GUI SETUP
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setLayout(new GridBagLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        drawNoteBook();

    }

    // EFFECTS: draws all background elements: buttons top right, notifications top
    // left
    private void drawNoteBook() {
        workspace.setBackground(BACKGROUND_COLOR);
        workspace.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        workspace.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setGridBagConstraints(0, 1, 2, GridBagConstraints.BOTH, 1, 1, GridBagConstraints.CENTER);
        frame.add(workspace, c);

        // NOTIFICATIONS
        JPanel notifications = new JPanel();
        notifications.setLayout(new BoxLayout(notifications, BoxLayout.Y_AXIS));
        notifications.setBackground(BACKGROUND_COLOR);
        notifications.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeMessage = createLabel("Welcome to your note app!", NOTE_COLOR, 16);
        notifications.add(welcomeMessage);

        if (!loaded && !saved) {
            JLabel loadAvailable = createLabel("You have a notebook saved!", NOTE_COLOR.darker(), 16);
            notifications.add(loadAvailable);
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JLabel saveAvailable = createLabel("You have unsaved changes!", NOTE_COLOR.darker(), 16);
            notifications.add(saveAvailable);
        }
        setGridBagConstraints(0, 0, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_START);
        frame.add(notifications, c);

        // BUTTONS
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttons.setBackground(BACKGROUND_COLOR);

        JButton addNoteButton = createButton("Add Note", NOTE_COLOR, "addNote");
        buttons.add(addNoteButton);

        if (!loaded && !saved) {
            JButton loadButton = createButton("Load Notes", NOTE_COLOR, "loadNotes");
            buttons.add(loadButton);
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JButton saveAvailable = createButton("Save Notes", NOTE_COLOR, "saveNotes");
            buttons.add(saveAvailable);
        }

        setGridBagConstraints(1, 0, 1, GridBagConstraints.NONE, 1, 0, GridBagConstraints.FIRST_LINE_END);
        frame.add(buttons, c);

        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: Modifies GridBagConstraints based on parameters
    private void setGridBagConstraints(int beginX, int beginY, int widthSpan, int fill, int weightX, int weightY, int anchor) {
        c.gridx = beginX;
        c.gridy = beginY;
        c.gridwidth = widthSpan;
        c.fill = fill;
        c.weightx = weightX;
        c.weighty = weightY;
        c.anchor = anchor;
    }

    // EFFECTS: Creates a JLabel based on given parameters and returns it
    private JLabel createLabel(String text, Color textColor, int textSize) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(new Font("Default", Font.PLAIN, textSize));
        return label;
    }

    // EFFECTS: creates a JButton based on the given parameters and returns it
    private JButton createButton(String text, Color bgColor, String command) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setActionCommand(command);
        button.addActionListener(this);
        return button;
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
            // noteContainer.setLayout(new BoxLayout(noteContainer, BoxLayout.Y_AXIS));
            noteContainer.setLayout(null);
            noteContainer.setPreferredSize(new Dimension(200, 200));
            noteContainer.setBackground(NOTE_COLOR);

            JLabel title = createLabel(note.getTitle(), Color.BLACK, 18);
            title.setBounds(10, 10, 180, 20);
            // title.setBorder(new EmptyBorder(10, 10, 10, 10));
            noteContainer.add(title);

            JTextArea content = createTextArea(note.getContent(), Color.BLACK, NOTE_COLOR);
            content.setEditable(false);
            content.setFocusable(false);
            content.setBounds(10, 40, 180, 150);
            noteContainer.add(content);
            
            JButton openNoteButton = createButton("", null, null);
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
        JDialog newNote = new JDialog(frame, "Create Note", false);
        newNote.setSize(400, 300);
        newNote.setLayout(null);
        newNote.getContentPane().setBackground(NOTE_COLOR);

        newNote.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        newNote.setLocationRelativeTo(frame);
        newNote.setVisible(true);

        JLabel titleLabel = createLabel("Title:", Color.BLACK, 12);
        titleLabel.setBounds(20, 20, 50, 25);
        newNote.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBackground(NOTE_COLOR);
        titleField.setBounds(80, 20, 290, 25);
        titleField.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        newNote.add(titleField);

        JLabel contentLabel = createLabel("Content:", Color.black, 12);
        contentLabel.setBounds(20, 60, 60, 25);
        newNote.add(contentLabel);

        JTextArea contentTextArea = createTextArea("", Color.BLACK, NOTE_COLOR);
        contentTextArea.setBounds(20, 90, 350, 120);
        contentTextArea.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR, 1));
        newNote.add(contentTextArea);

        JButton createButton = createButton("Create Note", BACKGROUND_COLOR, null);
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
            newNote.dispose();
            displayNotes();
        });
    }

    // EFFECTS: Creates a JTextArea based on parameters and returns it
    private JTextArea createTextArea(String text, Color textColor, Color bgColor) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setForeground(textColor);
        return textArea;
    } 

    // EFFECTS: displays title and content of a note, prompts user for commands in
    // the note
    public void displayNote(Note note) {
        JDialog noteView = new JDialog(frame, note.getTitle(), false);
        noteView.setSize(400, 400);
        noteView.setLayout(null);
        noteView.getContentPane().setBackground(NOTE_COLOR);

        noteView.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        noteView.setLocationRelativeTo(frame);
        noteView.setVisible(true);

        JTextArea title = createTextArea(note.getTitle(), Color.BLACK, NOTE_COLOR);
        title.setBounds(20, 20, 400, 25);
        noteView.add(title);

        JTextArea content = createTextArea(note.getContent(), Color.BLACK, NOTE_COLOR);
        content.setBounds(20, 60, 350, 200);
        noteView.add(content);

        noteView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                note.setTitle(title.getText());
                note.setContent(content.getText());
            }
        });

        JButton deleteButton = createButton("delete note", BACKGROUND_COLOR, null);
        deleteButton.setBounds(10, 330, 120, 30);
        deleteButton.setForeground(NOTE_COLOR);
        noteView.add(deleteButton);
        
        deleteButton.addActionListener(event -> {
            deleteNote(note);
            JOptionPane.showMessageDialog(noteView, "Note Deleted!");
            noteView.dispose();
            displayNotes();
        });
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
            // editor.setNoteBook(notebook);
            loaded = true;
            displayNotes();
            // drawNoteBook();
        } catch (IOException e) {
            System.out.println("Unable to read file " + filePath);
        }
    }
}
