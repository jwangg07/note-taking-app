package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import org.json.JSONObject;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Event;
import model.EventLog;
import model.Note;
import model.NoteBook;
import persistance.ReadJson;
import persistance.WriteJson;

// Represents the GUI of the notes application
@ExcludeFromJacocoGeneratedReport
public class NoteApp extends JFrame implements WindowListener {

    private final String filePath = "data/noteBook.json";
    private NoteBook notebook;
    private List<Note> visibleNotes;
    private ReadJson readjson;
    private WriteJson writejson;

    private GridBagConstraints constraints = new GridBagConstraints();
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;
    private static final Color BACKGROUND_COLOR = new Color(46, 31, 39);

    private NotificationPanel notificationPanel = new NotificationPanel();
    private ButtonsPanel buttonsPanel = new ButtonsPanel(this);
    private WorkspacePanel workspacePanel = new WorkspacePanel(this);

    // EFFECTS: Initializes the application with new notebook and default gui
    // settings
    public NoteApp() {
        super("Notebook");
        notebook = new NoteBook();
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
        notificationPanel.createNotification("You have a notebook saved!");
        drawNoteBook();

        addWindowListener(this);
    }

    // EFFECTS: draws all background elements: buttons top right, notifications top
    // left, and notes in the center
    private void drawNoteBook() {
        setGridBagConstraints(0, 1, 2, GridBagConstraints.BOTH, 1, 1, GridBagConstraints.CENTER);
        add(workspacePanel, constraints);

        setGridBagConstraints(0, 0, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.FIRST_LINE_START);
        notificationPanel.drawNotifications();
        add(notificationPanel, constraints);

        setGridBagConstraints(1, 0, 1, GridBagConstraints.NONE, 1, 0, GridBagConstraints.FIRST_LINE_END);
        add(buttonsPanel, constraints);
        revalidate();
        repaint();
    }

    // EFFECTS: Modifies GridBagConstraints based on parameters
    private void setGridBagConstraints(int beginX, int beginY, int widthSpan, int fill, int weightX, int weightY,
            int anchor) {
        constraints.gridx = beginX;
        constraints.gridy = beginY;
        constraints.gridwidth = widthSpan;
        constraints.fill = fill;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.anchor = anchor;
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

    // EFFECTS: filters all notes in notebook, keeping notes that match the given
    // prefix, and displays them
    public void filterNotes() {
        visibleNotes = notebook.filterNotes();
        workspacePanel.displayNotes(visibleNotes);
    }
    
    // EFFECTS: saves the notebook to a JSON file
    public void saveNoteBook() {
        try {
            writejson.write(notebook);
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
            notificationPanel.createNotification("Loaded notebook from " + filePath);
            filterNotes();
        } catch (IOException e) {
            System.out.println("Unable to read file " + filePath);
        }
    }

    public NoteBook getNoteBook() {
        return notebook;
    }

    public void displayNote(Note note) {
        NoteWindow noteWindow = new NoteWindow(this, note);
        noteWindow.displayNote(note);
    }

    public void createNote() {
        new CreateNoteWindow(this);
    }

    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }

    public WorkspacePanel getWorkspacePanel() {
        return workspacePanel;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
