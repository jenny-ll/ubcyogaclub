package ui;

// Citations:
// https://stackoverflow.com/questions/546144/adding-jtextfield-to-a-jpanel-and-showing-them
// https://stackoverflow.com/questions/23465295/remove-a-selected-row-from-jtable-on-button-click
// https://stackoverflow.com/questions/48913222/how-to-add-a-row-to-a-jtable-using-abstracttablemodel
// https://stackoverflow.com/questions/21110926/put-jlabels-and-jtextfield-in-same-frame-as-jtable

import model.Member;
import model.MembershipList;
import org.omg.CORBA.Object;
import ui.tools.AddTool;
import ui.tools.DeleteTool;
import ui.tools.Tool;

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

/**
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class YogaAppNew extends JPanel implements ActionListener {

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

    public YogaAppNew() {
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

    public void initializeGraphics() {
        name = new JTextField(10);
        email = new JTextField(10);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        JPanel panel = new JPanel();

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

        setVisible(true);
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

    class MembershipsTable extends AbstractTableModel {
        // columns
        private String[] columnNames = {"ID",
                "Full Name",
                "Email", "Student?"};
        // data
        private Object[][] data = {};

        public void setData(Object[][] data) {
            this.data = data;
            fireTableDataChanged();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public void removeRow(int row) {
            // remove a row from your internal data structure
            fireTableRowsDeleted(row, row);
        }


        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }


        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
//    private void createTools() {
//        String[] cols = {"ID", "Full Name", "Email", "Student?"};
//        members = (MembershipList) members.getMembers();
//        DefaultTableModel model = new DefaultTableModel(cols, 0);
//        JTextField nameField = new JTextField(10);
//        JTextField emailField = new JTextField(10);
//        JLabel nameLabel = new JLabel("Enter member name");
//        JLabel emailLabel = new JLabel("Enter member email");
//
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String data1 = name.getText();
//                String data2 = email.getText();
//                Integer data3 = nextMemberID.incrementAndGet();
//                Object[] row = {data3, data1, data2,true};
//                model.addRow(row);
//                validate();
//            }
//        });
//
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (table.getSelectedRow() != -1) {
//                    // remove selected row from the model
//                    model.removeRow(table.getSelectedRow());
//                }
//            }
//        });
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(0,1));
//        panel.setSize(new Dimension(0, 0));
//        panel.add(nameLabel);
//        panel.add(nameField);
//        panel.add(emailLabel);
//        panel.add(emailField);
//        JButton addButton = new JButton("Add Member");
//        panel.add(addButton);
//        JLabel deleteLabel = new JLabel("To delete, please select the member.");
//        panel.add(deleteLabel);
//        JButton deleteButton = new JButton("Delete Member");
//        panel.add(deleteButton);
//        add(panel, BorderLayout.SOUTH);
//    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("UBC Yoga Club Memberships");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        YogaAppNew newContentPane = new YogaAppNew();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

