package ui;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Represents the GUI of the notification section of the application
@ExcludeFromJacocoGeneratedReport
public class NotificationPanel extends JPanel {

    private LinkedList<String> notifications;
    private Helpers helper = new Helpers();
    private static final Color BACKGROUND_COLOR = new Color(46, 31, 39);
    private static final Color NOTE_COLOR = new Color(255, 235, 161);

    // EFFECTS: creates a notification panel with box layout, background color, and
    // padding
    public NotificationPanel() {
        notifications = new LinkedList<String>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // EFFECTS: redraws notifications with welcome message and a list of recent
    // notifications, removing the last notification if notifications > 5, and
    // displays most recent notification first
    public void drawNotifications() {
        removeAll();

        JLabel welcomeMessage = helper.createLabel("Welcome to your note app!", NOTE_COLOR, 16);
        add(welcomeMessage);

        if (notifications.size() > 5) {
            notifications.removeLast();
        }

        for (int i = 0; i < notifications.size(); i++) {
            Color notificationColor = NOTE_COLOR;
            for (int j = 0; j < i; j++) {
                notificationColor = notificationColor.darker();
            }
            JLabel notificationLabel = helper.createLabel(notifications.get(i), notificationColor, 12);
            add(notificationLabel);
        }
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds a notification to the front notifications and redraws
    // notification panel
    public void createNotification(String message) {
        notifications.addFirst(message);
        drawNotifications();
    }
}
