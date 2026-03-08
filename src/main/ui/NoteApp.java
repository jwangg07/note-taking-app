package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.JSONObject;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Note;
import model.NoteBook;
import persistance.ReadJson;
import persistance.WriteJson;

// Represents the GUI of the notes application
@ExcludeFromJacocoGeneratedReport
public class NoteApp extends JFrame {

    private final String filePath = "data/noteBook.json";
    private NoteBook notebook;
    private boolean loaded;
    private boolean saved;
    private ReadJson readjson;
    private WriteJson writejson;

    private GridBagConstraints c = new GridBagConstraints();
    private Helpers helper = new Helpers(this);
    private final int WIDTH = 1200;
    private final int HEIGHT = 900;
    private final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private final Color NOTE_COLOR = new Color(255, 235, 161);

    private NotificationPanel notificationPanel = new NotificationPanel();
    private ButtonsPanel buttonsPanel = new ButtonsPanel(this);
    private WorkspacePanel workspacePanel = new WorkspacePanel(this);
    private CreateNoteWindow createNoteWindow = new CreateNoteWindow(this, notificationPanel, workspacePanel);

    // EFFECTS: Initializes the application with new notebook and input handler
    public NoteApp() {
        super("Notebook");
        notebook = new NoteBook();
        loaded = false;
        saved = false;
        readjson = new ReadJson(filePath);
        writejson = new WriteJson(filePath);

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

    // EFFECTS: draws all background elements: buttons top right, notifications top
    // left
    private void drawNoteBook() {
        setGridBagConstraints(0, 1, 2, GridBagConstraints.BOTH, 1, 1, GridBagConstraints.CENTER);
        add(workspacePanel, c);

        if (!loaded && !saved) {
            notificationPanel.createNotification("You have a notebook saved!");
        }

        if (!compareNoteBookToFile() && (loaded || saved) ||
                !notebook.getAllNotes().isEmpty() && !loaded && !saved) {
            JLabel saveAvailable = helper.createLabel("You have unsaved changes!", NOTE_COLOR.darker(), 16);
            notificationPanel.add(saveAvailable);
        }
        setGridBagConstraints(0, 0, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_START);

        notificationPanel.drawNotifications();
        add(notificationPanel, c);

        setGridBagConstraints(1, 0, 1, GridBagConstraints.NONE, 1, 0, GridBagConstraints.FIRST_LINE_END);
        add(buttonsPanel, c);
        buttonsPanel.drawButtons();
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

    // EFFECTS: returns true if the current notebook is the same as the saved JSON
    // file, false otherwise
    public boolean compareNoteBookToFile() {
        JSONObject fileJson;
        try {
            fileJson = readjson.toJsonObject();
            return notebook.toJson().similar(fileJson);
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filePath);
        }
        return false;
    }

    // EFFECTS: saves the notebook to a JSON file
    public void saveNoteBook() {
        try {
            writejson.write(notebook);
            saved = true;
            System.out.println("Saved notebook to " + filePath);
            notificationPanel.createNotification("Saved notebook to " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write notebook to file " + filePath);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads notebook from JSON file
    public void loadNoteBook() {
        try {
            notebook = readjson.read();
            loaded = true;
            notificationPanel.createNotification("Loaded notebook from " + filePath);
            workspacePanel.displayNotes();
        } catch (IOException e) {
            System.out.println("Unable to read file " + filePath);
        }
    }

    public boolean getLoaded() {
        return loaded;
    }

    public boolean getSaved() {
        return saved;
    }

    public NoteBook getNoteBook() {
        return notebook;
    }

    public void displayNote(Note note) {
        NoteWindow noteWindow = new NoteWindow(this, note);
        noteWindow.displayNote(note);
    }

    public void createNote() {
        createNoteWindow.createNote();
    }

    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }

    public WorkspacePanel getWorkspacePanel() {
        return workspacePanel;
    }
}
