package ui.tools;


import model.Member;
import ui.YogaAppNew;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicInteger;

/* ListDemo.java requires no other files. */
public class AddTool extends Tool {

    private Member memberToAdd;
    private JTextField name;
    private JTextField email;
    private static final AtomicInteger nextMemberID = new AtomicInteger(100000);

    public AddTool(YogaAppNew app, JComponent parent) {
        super(app, parent);
        memberToAdd = null;
    }

    @Override
    protected void addListener() {
        button.addActionListener(new AddToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  constructs an Add Member button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Member");
        addToParent(parent);
    }

    private class AddToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the Add tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            app.setActiveTool(AddTool.this);
            String data1 = name.getText();
            String data2 = email.getText();
            Integer data3 = nextMemberID.incrementAndGet();
            String[] cols = {"ID", "Full Name", "Email", "Student?"};
            DefaultTableModel model = new DefaultTableModel(cols, 0);
            JTable table = new JTable(model);
            Object[] row = {data3, data1, data2,true};
            model.addRow(row);
        }
    }
}
