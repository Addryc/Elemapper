package org.elephant.mapper;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.PopupListener;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;
import org.elephant.mapper.ui.EleFrame;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class DescriptionEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea descriptionEditor;
    private JTextArea previewArea;
    private JButton buttonUpdatePreview;
    private JButton buttonDayChange;
    private JButton buttonClassChange;
    private JButton buttonAlignChange;
    private JButton buttonRaceChange;
    private JButton buttonRandomChange;
    private JButton buttonQuestChange;
    private JButton buttonSeasonChange;
    private int lastEditorPos = -1;
    private EleFrame eleFrame;
    private JTextComponent parent;


    private void insertMacro(EleConstants.MACRO selectedMacro) {
        if (lastEditorPos!=-1) {
            String macro = selectedMacro.getTemplate();
            descriptionEditor.insert(macro+" ", lastEditorPos);
            descriptionEditor.requestFocusInWindow();
            descriptionEditor.setCaretPosition(lastEditorPos+macro.length()+1);
        }
    }

    private void updateParent() {
        if (parent != null) {
            parent.setText(descriptionEditor.getText());
        }
    }

    public DescriptionEditor(EleFrame eleFrame, JTextComponent parent, String type, String name, String editedText) {
        this.eleFrame = eleFrame;
        this.parent = parent;
        setPreferredSize(new Dimension(1024, 768));
        setContentPane(contentPane);
        setModal(true);
        setTitle("Description Editor: "+name+" ("+type+")");
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

        ActionListener dayChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.DAY);
            }
        };

        ActionListener classChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.CLASS);
            }
        };

        ActionListener alignChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.ALIGN);
            }
        };

        ActionListener randomChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.RANDOM);
            }
        };

        ActionListener seasonChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.SEASON);
            }
        };

        ActionListener raceChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.RACE);
            }
        };

        ActionListener questChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                insertMacro(EleConstants.MACRO.QUEST);
            }
        };

        JPopupMenu menu = new JPopupMenu();
        SpellChecker.enableAutoSpell(descriptionEditor, true);
        menu.add(SpellChecker.createCheckerMenu());

        JMenu macroMenu = new JMenu("Macros");
        JMenuItem dayChangeMenuItem = new JMenuItem("DAYCHANGE");
        JMenuItem classChangeMenuItem = new JMenuItem("CLASSCHANGE");
        JMenuItem raceChangeMenuItem = new JMenuItem("RACECHANGE");
        JMenuItem randomChangeMenuItem = new JMenuItem("RANDOMCHANGE");
        JMenuItem alignChangeMenuItem = new JMenuItem("ALIGNCHANGE");
        JMenuItem seasonChangeMenuItem = new JMenuItem("SEASONCHANGE");
        JMenuItem questChangeMenuItem = new JMenuItem("QUESTCHANGE");
        dayChangeMenuItem.addActionListener(dayChangeListener);
        classChangeMenuItem.addActionListener(classChangeListener);
        raceChangeMenuItem.addActionListener(raceChangeListener);
        randomChangeMenuItem.addActionListener(randomChangeListener);
        alignChangeMenuItem.addActionListener(alignChangeListener);
        seasonChangeMenuItem.addActionListener(seasonChangeListener);
        questChangeMenuItem.addActionListener(questChangeListener);

        macroMenu.add(dayChangeMenuItem);
        macroMenu.add(classChangeMenuItem);
        macroMenu.add(raceChangeMenuItem);
        macroMenu.add(randomChangeMenuItem);
        macroMenu.add(alignChangeMenuItem);
        macroMenu.add(seasonChangeMenuItem);
        macroMenu.add(questChangeMenuItem);

        menu.add(macroMenu);
        descriptionEditor.addMouseListener(new PopupListener(menu));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonUpdatePreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String replaced = "";
                try {
                    replaced = Room.replaceMacros("Room", descriptionEditor.getText());
                } catch (EleMapExportException e) {
                    e.printStackTrace();
                }
                previewArea.setText(replaced);
            }
        });
        descriptionEditor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                lastEditorPos = descriptionEditor.getCaretPosition();
            }
        });

        buttonDayChange.addActionListener(dayChangeListener);
        buttonClassChange.addActionListener(classChangeListener);
        buttonAlignChange.addActionListener(alignChangeListener);
        buttonRaceChange.addActionListener(raceChangeListener);
        buttonRandomChange.addActionListener(randomChangeListener);
        buttonQuestChange.addActionListener(questChangeListener);
        buttonSeasonChange.addActionListener(seasonChangeListener);
        descriptionEditor.setText(editedText);
        buttonUpdatePreview.doClick();

    }

    private void onOK() {
        updateParent();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {

        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        SpellChecker.registerDictionaries(EleConstants.class.getResource("/dictionary"), "en");

        DescriptionEditor dialog = new DescriptionEditor(null, null,
                "Long", "Room1", "Testing");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
