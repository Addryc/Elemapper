package org.elephant.mapper.ui;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.borland.jbcl.layout.VerticalFlowLayout;

public class MonsterViewer extends JDialog {
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel pnlMonsterBasics = new JPanel();
    JPanel pnlMonsterBasicsLayout1 = new JPanel();
    JPanel pnlMonsterBasicsLayout2 = new JPanel();
    JPanel pnlMonsterBasicsLayout3 = new JPanel();
    JPanel pnlMonsterBasicsLayout4 = new JPanel();
    JPanel pnlMonsterBasicsLayout5 = new JPanel();
    JPanel pnlMonsterBasicsLayout6 = new JPanel();
    JPanel pnlMonsterBasicsLayout7 = new JPanel();
    JPanel pnlMonsterBasicsLayout8 = new JPanel();
    JPanel pnlMonsterBasicsLayout9 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    BorderLayout borderLayout7 = new BorderLayout();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel lblMonsterNumber = new JLabel();
    JTextField txtMonsterShortDesc = new JTextField();
    JTextField txtMonsterCodeDesc = new JTextField();
    JTextField txtMonsterName = new JTextField();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea txtMonsterLongDesc = new JTextArea();
    // Not sorted

//    public static void create(Frame frame, MonsterDialog monster) {
    public static void create(Frame frame) {
        MonsterViewer monsterViewer = new MonsterViewer(frame, "MonsterDialog", true);
        monsterViewer.setLocationRelativeTo(frame);
        monsterViewer.setVisible(true);

        Dimension dlgSize = monsterViewer.getPreferredSize();
        Dimension frmSize = frame.getSize();
        Point loc = frame.getLocation();
        monsterViewer.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        monsterViewer.setModal(true);
        monsterViewer.setVisible(true);
    }

    private MonsterViewer(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jLabel4.setText("MonsterDialog Name");
        jLabel5.setText("Description for code comment");
        pnlMonsterBasics.setLayout(borderLayout6);
        jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabel1.setText("MonsterDialog Number ");
        jLabel3.setText("Long Description");
        jLabel2.setText("Short Description");
        pnlMonsterBasicsLayout1.setLayout(borderLayout5);
        pnlMonsterBasicsLayout6.setLayout(verticalFlowLayout1);
        pnlMonsterBasicsLayout7.setLayout(borderLayout7);
        txtMonsterName.setMinimumSize(new Dimension(120, 21));
        txtMonsterName.setPreferredSize(new Dimension(120, 21));
        pnlMonsterBasicsLayout4.setLayout(borderLayout3);
        pnlMonsterBasicsLayout5.setLayout(borderLayout4);
        txtMonsterLongDesc.setWrapStyleWord(true);
        txtMonsterLongDesc.setLineWrap(true);
        txtMonsterLongDesc.setFont(new Font("SansSerif", 0, 12));
        pnlMonsterBasicsLayout2.setLayout(borderLayout1);
        pnlMonsterBasicsLayout3.setLayout(borderLayout2);
        pnlMonsterBasicsLayout4.add(jLabel3, BorderLayout.NORTH);
        pnlMonsterBasicsLayout4.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.add(txtMonsterLongDesc, null);
        pnlMonsterBasics.add(pnlMonsterBasicsLayout6, BorderLayout.NORTH);
        pnlMonsterBasicsLayout1.add(lblMonsterNumber, BorderLayout.CENTER);
        pnlMonsterBasicsLayout1.add(jLabel1, BorderLayout.WEST);
        pnlMonsterBasicsLayout6.add(pnlMonsterBasicsLayout7, null);
        pnlMonsterBasicsLayout6.add(pnlMonsterBasicsLayout5, null);
        pnlMonsterBasicsLayout6.add(pnlMonsterBasicsLayout8, null);
        pnlMonsterBasicsLayout6.add(pnlMonsterBasicsLayout9, null);
        pnlMonsterBasicsLayout2.add(jLabel5, BorderLayout.NORTH);
        pnlMonsterBasicsLayout2.add(txtMonsterCodeDesc, BorderLayout.SOUTH);
        pnlMonsterBasicsLayout6.add(pnlMonsterBasicsLayout1, null);
        pnlMonsterBasicsLayout5.add(pnlMonsterBasicsLayout3, BorderLayout.EAST);
        pnlMonsterBasicsLayout3.add(jLabel4, BorderLayout.NORTH);
        pnlMonsterBasicsLayout3.add(txtMonsterName, BorderLayout.SOUTH);
        pnlMonsterBasicsLayout5.add(pnlMonsterBasicsLayout2,
                                    BorderLayout.CENTER);
        pnlMonsterBasicsLayout7.add(jLabel2, BorderLayout.NORTH);
        pnlMonsterBasicsLayout7.add(txtMonsterShortDesc, BorderLayout.SOUTH);
        pnlMonsterBasics.add(pnlMonsterBasicsLayout4, BorderLayout.CENTER);
        getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
        jTabbedPane1.add(pnlMonsterBasics, "Basics");
    }

}
