package ui;

// Citations:
// https://stackoverflow.com/questions/546144/adding-jtextfield-to-a-jpanel-and-showing-them
// https://stackoverflow.com/questions/23465295/remove-a-selected-row-from-jtable-on-button-click
// https://stackoverflow.com/questions/48913222/how-to-add-a-row-to-a-jtable-using-abstracttablemodel
// https://stackoverflow.com/questions/21110926/put-jlabels-and-jtextfield-in-same-frame-as-jtable
// https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
// Table Demo Example and JWSFileChooserDemo.java from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Simple Drawing Player example from class.

import model.Member;
import model.MembershipList;
import org.omg.CORBA.Object;
import ui.tools.AddTool;
import ui.tools.DeleteTool;
import ui.tools.Tool;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class YogaAppNew extends JPanel implements ActionListener {
    private BufferedImage image;
    private JButton openButton;
    private JButton saveButton;
    private JTextArea log;
    private JPanel buttonPanel;
    private JScrollPane logScrollPane;

    private static final AtomicInteger nextMemberID = new AtomicInteger(100000);
    private static final Integer COLS = 4;

    private Member currentMember;
    private MembershipList members;
    private Tool activeTool;
    private List<Tool> tools;

    private JTable table;
    private Object[][] data;

    private JTextField name;
    private JTextField email;

    String[] columnNames = { "ID", "Full Name", "Email", "Student?" };

    JLabel deleteLabel = new JLabel("To delete, please select the member.");
    JLabel nameLabel = new JLabel("Enter member name");
    JLabel emailLabel = new JLabel("Enter member email");

    JButton addButton = new JButton("Add Member");
    JButton deleteButton = new JButton("Delete Member");

    private DefaultTableModel model = new DefaultTableModel(data, columnNames);

    BufferedImage dogPic = ImageIO.read(new File("./data/yogaclublogo.png"));
    JLabel picLabel = new JLabel(new ImageIcon(dogPic.getScaledInstance(50,50, Image.SCALE_FAST)));

    public YogaAppNew() throws IOException {
        super(new GridLayout(2, 0));
        initializeFields();
        initializeGraphics();
    }

    // getters
    public Member getMember() {
        return currentMember;
    }

    private void initializeFields() {
        activeTool = null;
        currentMember = null;
        tools = new ArrayList<Tool>();
    }

    // EFFECTS: initializes the graphics for the app including all of the text fields,
    // buttons, and labels. Also includes the panel itself, which layouts everything.

    public void initializeGraphics() throws IOException {
        name = new JTextField(10);
        email = new JTextField(10);
        table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        JPanel panel = new JPanel();

        panel.add(picLabel);

        panel.setLayout(new GridLayout(10, 4));
        panel.setSize(new Dimension(100, 100));

        addButton.setActionCommand("click1");
        addButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(name);
        panel.add(emailLabel);
        panel.add(email);
        panel.add(addButton);

        deleteButton.setActionCommand("click2");
        deleteButton.addActionListener(this);

        panel.add(deleteLabel);
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);
    }

    // EFFECTS: Allows user to choose a file to save or open

    public void fileChooser() {

        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        logScrollPane = new JScrollPane(log);

        //Create the open button.
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

        //Create the save button.
        saveButton = new JButton("Save a File...");
        saveButton.addActionListener(this);

        //Put the buttons in a separate panel
        buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click1")) {
            String data1 = name.getText();
            String data2 = email.getText();
            Integer data3 = nextMemberID.incrementAndGet();

            java.lang.Object[] row = {data3, data1, data2, true};
            model.addRow(row);

        } else if (e.getActionCommand().equals("click2")) {
            if (table.getSelectedRow() != -1) {
                // remove selected row from the model
                model.removeRow(table.getSelectedRow());
            }
        }
    }


    public void setActiveTool(Tool someTool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        someTool.activate();
        activeTool = someTool;
    }

    // EFFECTS: creates the GUI and throws IOException

    public static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("UBC Yoga Club Memberships");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane
        YogaAppNew newContentPane = new YogaAppNew();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Runs main to launch the GUI

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

