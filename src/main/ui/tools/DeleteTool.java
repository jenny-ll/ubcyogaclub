package ui.tools;


import model.Member;
import ui.YogaAppNew;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

// CITATION: SimpleDrawingPlayer from class

public class DeleteTool extends Tool {

    private Member memberToAdd;

    public DeleteTool(YogaAppNew app, JComponent parent) {
        super(app, parent);
        memberToAdd = null;
    }

    @Override
    protected void addListener() {
        button.addActionListener(new DeleteToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  constructs an Delete Member button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Delete Member");
        addToParent(parent);
    }

    private class DeleteToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the Delete tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            app.setActiveTool(DeleteTool.this);
        }
    }
}


