import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Gui {
    private int taskNumber = 1; // Initialize task number

    public Gui() {
        JFrame frame = new JFrame("To-Do List");
        frame.setBounds(500, 100, 500, 500);
        frame.setLayout(null); // Using null layout

        // Create components
        JLabel label1 = new JLabel("<html><u>To-Do List</u></html>", SwingConstants.CENTER); // Underlined text using HTML
        JPanel panel1 = new JPanel(null); // Using null layout for panel1 as well
        JLabel label2 = new JLabel("Enter your task here", SwingConstants.CENTER);
        JTextField enterTask = new JTextField(20);
        JButton submit = new JButton("SUBMIT");
        JTextPane taskPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(taskPane);
        JButton taskCompleted = new JButton("Completed");

        // Set bounds directly with numerical values for center alignment
        label1.setBounds(0, 20, 500, 40); // Increased height to accommodate larger font
        panel1.setBounds(0, 60, 500, 440); // Increased height to accommodate all components
        panel1.setBackground(Color.WHITE); // Set background to white

        // Set bounds for components inside panel1
        label2.setBounds(150, 20, 200, 30);
        enterTask.setBounds(100, 70, 300, 30);
        submit.setBounds(200, 120, 100, 30);
        scrollPane.setBounds(50, 170, 395, 160); // Adjusted to fit within the panel
        taskCompleted.setBounds(200, 350, 100, 30); // Placed below scrollPane

        // Configure the taskPane
        taskPane.setEditable(false); // Make the text pane read-only

        // Set font for label1
        label1.setFont(new Font("Serif", Font.BOLD, 24)); // Larger, bold font

        // Add components to the panel
        panel1.add(label2);
        panel1.add(enterTask);
        panel1.add(submit);
        panel1.add(scrollPane);
        panel1.add(taskCompleted);

        // Add components to the frame
        frame.add(label1);
        frame.add(panel1);

        // Add action listener to the submit button
        ActionListener submitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = enterTask.getText().trim();
                if (!task.isEmpty()) {
                    // Get the current time and format it
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedTime = now.format(formatter);

                    // Append the task with the timestamp and numbering
                    appendTaskWithTime(taskPane, task, formattedTime, taskNumber++);
                    enterTask.setText(""); // Clear the input field
                }
            }
        };

        submit.addActionListener(submitAction);

        // Add key listener to the text field to handle Enter key press
        enterTask.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitAction.actionPerformed(null); // Trigger the same action as submit button
                }
            }
        });

        // Add action listener to the taskCompleted button
        taskCompleted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all tasks from the taskPane
                taskPane.setText("");
                taskNumber = 1; // Reset task number if you want to restart numbering
            }
        });

        // Frame settings
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void appendTaskWithTime(JTextPane textPane, String task, String time, int number) {
        StyledDocument doc = textPane.getStyledDocument();

        // Style for the task
        Style taskStyle = textPane.addStyle("TaskStyle", null);
        StyleConstants.setFontSize(taskStyle, 14);

        // Style for the time
        Style timeStyle = textPane.addStyle("TimeStyle", null);
        StyleConstants.setFontSize(timeStyle, 10);
        StyleConstants.setForeground(timeStyle, Color.GRAY);
        StyleConstants.setAlignment(timeStyle, StyleConstants.ALIGN_RIGHT);

        try {
            doc.insertString(doc.getLength(), number + ". " + task + " ", taskStyle); // Add task number and text
            doc.insertString(doc.getLength(), "[" + time + "]\n", timeStyle); // Add timestamp
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Gui obj = new Gui();
    }
}
