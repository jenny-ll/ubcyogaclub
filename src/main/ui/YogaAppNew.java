package ui;

// Citations:
// https://stackoverflow.com/questions/546144/adding-jtextfield-to-a-jpanel-and-showing-them
// https://stackoverflow.com/questions/23465295/remove-a-selected-row-from-jtable-on-button-click
// https://stackoverflow.com/questions/48913222/how-to-add-a-row-to-a-jtable-using-abstracttablemodel
// https://stackoverflow.com/questions/21110926/put-jlabels-and-jtextfield-in-same-frame-as-jtable
// https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
// https://stackoverflow.com/questions/7391877/how-to-add-checkboxes-to-jtable-swing
// https://www.javatpoint.com/java-jmenuitem-and-jmenu
// Table Demo Example and JWSFileChooserDemo.java from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Simple Drawing Player example from class.

import exception.InvalidEmailException;
import model.Member;
import model.MembershipList;
import org.json.JSONObject;
import org.omg.CORBA.Object;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.Tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class YogaAppNew extends JPanel implements ActionListener {
    private BufferedImage image;
    private JButton openButton;
    private JButton saveButton;
    private JTextArea log;
    private JPanel buttonPanel;
    private JScrollPane logScrollPane;

    private static final AtomicInteger nextMemberID = new AtomicInteger(100000);
    private static final Integer COLS = 4;
    private static final String JSON_STORE = "./data/YogaAppMembershipList.json";

    private Member currentMember;
    private MembershipList members;
    private Tool activeTool;
    private List<Tool> tools;
    private ArrayList<Member> studentMembers;
    private ArrayList<Member> nonStudentMembers;
    private ArrayList<Member> allMembers;

    private JTable table;
    private Object[][] data;
    private JPanel panel;
    private Integer addRowConstant;
    private Boolean studentBoolean;
    private java.lang.Object[] row;
    private String nameText;
    private String emailText;
    private Integer memberID;

    private JTextField name;
    private JTextField email;
    private JMenu menu;
    private JMenuItem save;
    private JMenuItem open;
    private JMenuBar mb;

    private JSONObject jsonObject;
    private String jsonData;
    private JsonWriter jsonWriter;
    private JFileChooser fileChooser;
    private String fileName;
    private JsonReader jsonReader;

    private DefaultTableModel model;

    String[] columnNames = { "ID", "Full Name", "Email", "Student?" };

    JLabel deleteLabel = new JLabel("To delete, please select the member then press 'Delete Member'.");
    JLabel nameLabel = new JLabel("Enter member full name:");
    JLabel emailLabel = new JLabel("Enter member email:");

    JButton addButton = new JButton("Add Member");
    JButton deleteButton = new JButton("Delete Member");

    BufferedImage yogaPic = ImageIO.read(new File("./data/yogaclublogo.png"));
    JLabel picLabel = new JLabel(new ImageIcon(yogaPic.getScaledInstance(50,50, Image.SCALE_FAST)));

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
        members = new MembershipList();
        tools = new ArrayList<Tool>();
    }

    // EFFECTS: initializes the graphics for the app including all of the text fields,
    // buttons, and labels. Also includes the panel itself, which layouts everything.

    public void initializeGraphics() throws IOException {
        model = new DefaultTableModel(data, columnNames);
        name = new JTextField(10);
        email = new JTextField(10);

        assembleTable();

        checkboxTable();

        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        addButton.setActionCommand("click1");
        addButton.addActionListener(this);

        deleteButton.setActionCommand("click2");
        deleteButton.addActionListener(this);
    }

    // EFFECTS: assemble everything onto panel

    private void assembleTable() {
        panel = new JPanel();
        makeMenu();
        panel.add(mb, BorderLayout.NORTH);
        panel.add(picLabel);
        panel.setLayout(new GridLayout(10, 4));
        panel.setSize(new Dimension(100, 100));
        panel.add(nameLabel);
        panel.add(name);
        panel.add(emailLabel);
        panel.add(email);
        panel.add(addButton);
        panel.add(deleteLabel);
        panel.add(deleteButton);
        add(panel);
    }

    public void makeMenu() {
        mb = new JMenuBar();
        menu = new JMenu("Menu");
        save = new JMenuItem("Save file");
        open = new JMenuItem("Open file");
        menu.add(save);
        menu.add(open);
        mb.add(menu);

        save.setActionCommand("saveClick");
        save.addActionListener(this);

        open.setActionCommand("openClick");
        open.addActionListener(this);
    }

    // EFFECTS: make table have checkbox for student

    private void checkboxTable() {
        table = new JTable(model) {

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
    }

    public void initializeRow() {
        addRowConstant = model.getRowCount() - 1;
        studentBoolean = (Boolean) table.getValueAt(addRowConstant,3);
    }

    public void getAddData() {
        nameText = name.getText();
        emailText = email.getText();
        memberID = nextMemberID.incrementAndGet();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click1")) {
            getAddData();

            java.lang.Object[] row = {memberID, nameText, emailText, true};
            model.addRow(row);

            initializeRow();

            try {
                currentMember = new Member(nameText,emailText,studentBoolean);
            } catch (InvalidEmailException invalidEmailException) {
                invalidEmailException.printStackTrace();
            }
            members.addMember(currentMember);

        } else if (e.getActionCommand().equals("click2")) {
            if (table.getSelectedRow() != -1) {
                int rowConstant = table.getSelectedRow();
                Integer memberID = (Integer) table.getValueAt(rowConstant,0);

                model.removeRow(table.getSelectedRow());
                // remove selected row from membership list
                members.deleteMember(memberID);
            }
        } else if (e.getActionCommand().equals("saveClick")) {
            saveMembershipList();
        } else if (e.getActionCommand().equals("openClick")) {
            loadMembershipList();
        }
    }

    // EFFECTS: saves the membership list to file
    private void saveMembershipList() {
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(members);
            jsonWriter.close();
            System.out.println("Saved membership list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads membership list from file
    private void loadMembershipList() {
        jsonReader = new JsonReader(JSON_STORE);
        try {
            members = jsonReader.read();
            System.out.println("Loaded membership list from " + JSON_STORE);
            studentMembers = members.showStudentMembers();
            nonStudentMembers = members.showNonStudentMembers();
            studentMembers.addAll(nonStudentMembers);
            for (Member m : studentMembers) {
                Integer memberID = m.getId();
                String memberName = m.getName();
                String memberEmail = m.getEmail();
                java.lang.Object[] row = {memberID, memberName, memberEmail, true};
                model.addRow(row);
            }
        } catch (IOException | InvalidEmailException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
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

