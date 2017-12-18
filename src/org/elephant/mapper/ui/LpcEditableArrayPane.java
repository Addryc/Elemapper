package org.elephant.mapper.ui;

import javax.swing.*;

public class LpcEditableArrayPane extends JScrollPane {
    EleFrame eleFrame;
    JButton buttonAdd = new JButton("Add");
    JButton buttonDel = new JButton("Delete");
    JButton buttonUpdate = new JButton("Update");
    public LpcEditableArrayPane(EleFrame eleFrame) {
        super();
        this.eleFrame = eleFrame;
        jbInit();
    }

    public void jbInit() {
        add(buttonAdd);
        add(buttonDel);
        add(buttonUpdate);
    }

    public static void main(String[] args) {
        LpcEditableArrayPane pane = new LpcEditableArrayPane(null);
        pane.setVisible(true);

        JFrame fr = new JFrame();
        fr.add(pane);
        fr.pack();
        fr.setVisible(true);
    }

}
