package org.elephant.mapper.ui;

import org.elephant.mapper.Exportable;
import org.elephant.mapper.Monster;
import org.elephant.mapper.Room;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

public class MonsterDialog extends JDialog {
    private EleFrame eleFrame;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField monName;
    private JTextArea monShort;
    private JTextArea monLong;
    private JTextPane previewArea;
    private JTabbedPane detailsPane;
    private JComboBox genderSelection;
    private JTextField fileName;
    private JComboBox bodyType;
    private JComboBox race;
    private JComboBox monLevel;
    private JComboBox monPrimaryClass;
    private JComboBox monSecondClass;
    private JTable monsterListTable;
    private JScrollPane monsterListPane;
    private JButton buttonSave;
    private JPanel monsterListButtons;
    private JButton buttonNew;
    private JButton buttonLoad;
    private JButton buttonDelete;
    private JScrollPane previewAreaScrollPane;
    private JButton buttonAddToRoom;
    private JLabel labelCodeComment;
    private JLabel labelFileName;
    private JTextField codeComment;
    private JTable monIdsTable;
    private JTable monEmotes;
    private JTextField monEmoteFreq;
    private Monster monster;

    private Monster getMonster() {
        return monster;
    }

    public MonsterDialog(EleFrame owner) {
        super(owner);
        init(owner, null);
    }

    public MonsterDialog(EleFrame owner, String uuid) {
        super(owner);
        init(owner, null);
        monsterLoad(uuid);
    }


    public MonsterDialog(EleFrame owner, Monster monster) {
        super(owner);
        init(owner, monster);

    }

    private void initMonValues(Monster monster) {
        this.monster = monster;
//        s.println(monster);

        if (monster != null) {
            fileName.setText(monster.getFileName());
            monName.setText(monster.getName());
            monShort.setText(monster.getShortDesc());
            monLong.setText(monster.getLongDesc());
            bodyType.setSelectedItem(monster.getBodyType());
            race.setSelectedItem(monster.getRace());
            monLevel.setSelectedItem(String.valueOf(monster.getLevel()));
            genderSelection.setSelectedItem(monster.getGender());
            monPrimaryClass.setSelectedItem(monster.getPrimaryClass());
            monSecondClass.setSelectedItem(monster.getSecondClass());
            codeComment.setText(monster.getCodeDescription());
            if(eleFrame.getRoomHelper().hasRoomSelected()) {
                buttonAddToRoom.setEnabled(true);
            }

            ((LpcObjectEditableArrayTable)monIdsTable.getModel()).refresh(monster.getIds());
            ((LpcObjectEditableArrayTable)monEmotes.getModel()).refresh(monster.getEmotes());
            monEmoteFreq.setText(String.valueOf(getMonster().getEmoteFrequency()));


            buttonSave.setEnabled(true);
        } else {
            buttonSave.setEnabled(false);
            buttonAddToRoom.setEnabled(false);
            fileName.setText("");
            monName.setText("");
            monShort.setText("");
            monLong.setText("");
            codeComment.setText("");
            bodyType.setSelectedItem("");
            race.setSelectedItem("");
            monLevel.setSelectedItem("1");
            genderSelection.setSelectedItem("");
            monPrimaryClass.setSelectedItem("");
            monSecondClass.setSelectedItem("");
            ((LpcObjectEditableArrayTable)monIdsTable.getModel()).reset();
            ((LpcObjectEditableArrayTable)monEmotes.getModel()).reset();
            monEmoteFreq.setText("");

        }
        updatePreview();


    }
    private void init(EleFrame eleFrame, Monster monster) {
        this.eleFrame = eleFrame;
        if(monster != null) {
            initMonValues(monster);
        }
        monsterListTable.setModel(new MonsterTableModel(eleFrame.getMonsters()));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        Action buttonSaveActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                System.out.println("Saving Monster");
                getMonster().setFileName(fileName.getText());
                getMonster().setName(monName.getText());
                getMonster().setShortDesc(monShort.getText());
                getMonster().setLongDesc(monLong.getText());
                getMonster().setCodeDescription(codeComment.getText());
                updatePreview();
                eleFrame.addOrUpdateMonster(getMonster());
                ((MonsterTableModel)monsterListTable.getModel()).refresh(eleFrame.getMonsters());
            }
        };

        // Bind to Ctrl-S
        buttonSaveActionListener.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        buttonSave.setAction(buttonSaveActionListener);
        buttonSave.getActionMap().put("buttonSaveActionListener", buttonSaveActionListener);
        buttonSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                (KeyStroke) buttonSaveActionListener.getValue(Action.ACCELERATOR_KEY), "buttonSaveActionListener");
        buttonSave.setText("Save");
        buttonSave.setEnabled(false);

        buttonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(getMonster() != null) {
                    eleFrame.addOrUpdateMonster(getMonster());
                    ((MonsterTableModel)monsterListTable.getModel()).refresh(eleFrame.getMonsters());
                }
                initMonValues(new Monster());
                eleFrame.addOrUpdateMonster(getMonster());
                monsterListTable.clearSelection();
                buttonLoad.setEnabled(false);
            }
        });

        buttonAddToRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(getMonster() != null) {
                    eleFrame.addOrUpdateMonster(getMonster());
                    ((MonsterTableModel)monsterListTable.getModel()).refresh(eleFrame.getMonsters());
                    eleFrame.getRoomHelper().addObject(getMonster().getFileName(), true, true,
                            true, true, "", getMonster().getUuid());
                    eleFrame.getRoomTabs().updateObjectLists();
                }
            }
        });

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (monsterListTable.getSelectedRow()>=0) {
                    String uuid = monsterListTable.getValueAt(monsterListTable.getSelectedRow(), 0).toString();
                    monsterLoad(uuid);
                }

            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (monsterListTable.getSelectedRow()>=0) {
                    String uuid = monsterListTable.getValueAt(monsterListTable.getSelectedRow(), 0).toString();
                    monsterDelete(uuid);
                    ((MonsterTableModel)monsterListTable.getModel()).refresh(eleFrame.getMonsters());
                    if (getMonster().getUuid() == uuid) {
                        initMonValues(null);
                    }
                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        codeComment.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                getMonster().setCodeDescription(codeComment.getText());
                updatePreview();
            }
        });

        monName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                getMonster().setName(monName.getText());
                updatePreview();
            }
        });
        monShort.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getMonster().setShortDesc(monShort.getText());
                updatePreview();
            }
        });
        monLong.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getMonster().setLongDesc(monLong.getText());
                updatePreview();
            }
        });
        genderSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getMonster().setGender(genderSelection.getSelectedItem().toString());
                updatePreview();

            }
        });
        fileName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getMonster().setFileName(fileName.getText());
                updatePreview();
            }
        });

        bodyType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getMonster().setBodyType(bodyType.getSelectedItem().toString());
                updatePreview();

            }
        });

        race.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getMonster().setRace(race.getSelectedItem().toString());
                updatePreview();

            }
        });

        monLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getMonster().setLevel(Integer.parseInt(monLevel.getSelectedItem().toString()));
                updatePreview();

            }
        });


        monPrimaryClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getMonster().setPrimaryClass(monPrimaryClass.getSelectedItem().toString());
                updatePreview();
            }
        });
        monSecondClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getMonster().setSecondClass(monSecondClass.getSelectedItem().toString());
                updatePreview();
            }
        });


        LpcObjectEditableArrayTable monIdsTableModel = new LpcObjectEditableArrayTable("Id");
        monIdsTable.setTableHeader(null);
        monIdsTable.setModel(monIdsTableModel);

        monIdsTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_INSERT ||
                        (monIdsTable.getSelectedRow() == monIdsTable.getRowCount()-1) && e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(monIdsTable.getSelectedRow() == monIdsTable.getRowCount()-1) {
                        monIdsTableModel.addRow(new String[]{""});
                    } else {
                        monIdsTableModel.insertRow(monIdsTable.getSelectedRow(), new String[]{""});

                    }
                } else if(e.getKeyCode() == KeyEvent.VK_DELETE && monIdsTable.getSelectedRow()>=0) {
                    monIdsTableModel.removeRow(monIdsTable.getSelectedRow());
//                    table.setRowSelectionInterval(oldRow-1, oldRow-1);
                }
            }
        });
        monIdsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                List<String> values = monIdsTableModel.getValues();
                getMonster().setIds(values);
                updatePreview();
            }
        });


        monEmoteFreq.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(monEmoteFreq.getText()!=null && monEmoteFreq.getText().length()>0) {
                    try {
                        int i = Integer.valueOf(monEmoteFreq.getText());
                        if (i<1 || i>100) {
                            throw new Exception();
                        }
                        getMonster().setEmoteFrequency(i);
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Emote Frequency has to be a number " +
                                        "between 1 and 100.", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                        monEmoteFreq.setText(String.valueOf(getMonster().getEmoteFrequency()));
                    }
                }
            }
        });

        LpcObjectEditableArrayTable monEmotesTableModel = new LpcObjectEditableArrayTable("Emote");
        monEmotes.setTableHeader(null);
        monEmotes.setModel(monEmotesTableModel);


        monEmotes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_INSERT ||
                        (monEmotes.getSelectedRow() == monEmotes.getRowCount()-1) && e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(monEmotes.getSelectedRow() == monEmotes.getRowCount()-1) {
                        monEmotesTableModel.addRow(new String[]{""});
                    } else {
                        monEmotesTableModel.insertRow(monEmotes.getSelectedRow(), new String[]{""});

                    }
                } else if(e.getKeyCode() == KeyEvent.VK_DELETE && monEmotes.getSelectedRow()>=0) {
                    monEmotesTableModel.removeRow(monEmotes.getSelectedRow());
//                    table.setRowSelectionInterval(oldRow-1, oldRow-1);
                }
            }
        });
        monEmotes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                List<String> values = monEmotesTableModel.getValues();

                getMonster().setEmotes(values);
                updatePreview();
            }
        });


        monsterListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
//                System.out.println(monsterListTable.getValueAt(monsterListTable.getSelectedRow(), 0).toString());
                buttonLoad.setEnabled(true);
                buttonDelete.setEnabled(true);
            }
        });

        monsterListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String uuid = monsterListTable.getValueAt(monsterListTable.getSelectedRow(), 0).toString();
                if (e.getClickCount() == 2) {
//                    System.out.println("Double Click on "+ uuid);
                    monsterLoad(uuid);

                }
            }
        });
    }
    private void monsterLoad(String uuid) {
        for (Exportable monster: eleFrame.getMonsters()) {
            Monster mon = (Monster)monster;
            if(mon.getUuid().equalsIgnoreCase(uuid)) {
                initMonValues(mon);
                break;
            }
        }
    }

    private void monsterDelete(String uuid) {
        Monster mon = null;
        for (Exportable monster: eleFrame.getMonsters()) {
            mon = (Monster)monster;
            if(mon.getUuid().equalsIgnoreCase(uuid)) {
                break;
            }
        }
        if (mon != null) {
            eleFrame.getMonsters().remove(mon);
        }
    }

    private void updatePreview() {
//        System.out.println(monster);
        if(monster != null) {
            previewArea.setText(monster.template());
        } else {
            previewArea.setText("");
        }
    }
    private void onOK() {
        eleFrame.addOrUpdateMonster(monster);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Monster monster = new Monster();
        MonsterDialog dialog = new MonsterDialog(null, monster);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
