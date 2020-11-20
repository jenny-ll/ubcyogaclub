package ui;

/*
 * TableDemo.java requires no other files.
 */

import model.Member;
import model.MembershipList;
import ui.tools.Tool;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class YogaAppNew extends JPanel {

    private Member currentMember;
    private Tool activeTool;
    private List<Tool> tools;

    private boolean debug = false;

    private static final String hireString = "Add Member";
    private static final String fireString = "Delete Member";

    private JButton fireButton;
    private JTextField memberName;
    private JTable table;

    public YogaAppNew() {
        super(new GridLayout(2,0));
        initializeFields();
        initializeGraphics();
    }

    // getters
    public MembershipList getMember() {
        return currentMember;
    }

    private void initializeFields() {
        activeTool = null;
        currentMember = null;
        tools = new ArrayList<Tool>();
    }

    private void initializeGraphics() {
        JTable table = new JTable(new MembershipsTable());
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS:  declares and instantiates a Member (newMember), and adds it to members
    private void addNewMember() {
        Member newMember = new Member();
        currentMember = newMember;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{currentMember.getId(), currentMember.getName(),
                currentMember.getEmail()});
        validate();
    }

    public void setActiveTool(Tool someTool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        someTool.activate();
        activeTool = someTool;
    }

    class MembershipsTable extends AbstractTableModel {
        private String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
        private Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };

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

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (debug) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (debug) {
                System.out.println("New value of data:");
                printDebugData();
            }
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

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
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

