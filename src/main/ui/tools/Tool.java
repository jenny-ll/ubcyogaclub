package ui.tools;

import ui.YogaAppNew;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class Tool {

    protected JButton button;
    protected YogaAppNew app;
    private boolean active;

    public Tool(YogaAppNew app, JComponent parent) {
        this.app = app;
        createButton(parent);
        addToParent(parent);
        active = false;
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // getters
    public boolean isActive() {
        return active;
    }

    // EFFECTS: sets this Tool's active field to true
    public void activate() {
        active = true;
    }

    // EFFECTS: sets this Tool's active field to false
    public void deactivate() {
        active = false;
    }

    protected abstract void addListener();

    public void addToParent(JComponent parent) {
        parent.add(button);
    }

    protected abstract void createButton(JComponent parent);
}
