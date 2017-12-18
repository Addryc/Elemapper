package org.elephant.mapper.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.inet.jortho.SpellChecker;
import org.elephant.mapper.*;
import org.elephant.mapper.helper.RoomHelper;

/**
 * @author UKJamesCook
 */
public class RoomTabs extends JTabbedPane {
    private static final int DEFAULT_ROOM_PANE = 0;

    private JRadioButton senseSelected = null;
    private final EleFrame eleFrame;

    private JButton btnItemAdd = new JButton();
    private JButton btnItemDelete = new JButton();
    private JButton btnSenseAdd = new JButton();
    private JButton btnSenseDelete = new JButton();
    private JButton btnObjectDelete = new JButton();
    private JButton btnObjectAdd = new JButton();
    private JButton btnRoomColour = new JButton();
    private JButton btnFunctionAdd = new JButton();
    private JButton btnFunctionDelete = new JButton();
    private JButton btnGuardAdd = new JButton();
    private JButton btnGuardDelete = new JButton();
    private ButtonGroup buttonGroupRoomOutDoors = new ButtonGroup();
    private ButtonGroup buttonGroupRoomSense = new ButtonGroup();
    private ButtonGroup buttonGroupRoomObjectType = new ButtonGroup();
    private ButtonGroup buttonGroupRoomObjectLoadType = new ButtonGroup();
    private ButtonGroup buttonGroupGuardCheck = new ButtonGroup();
    private ButtonGroup buttonGroupGuardMessage = new ButtonGroup();
    private ButtonGroup buttonGroupGuardWizard = new ButtonGroup();
    private JRadioButton optIndoors = new JRadioButton();
    private JRadioButton optOutdoors = new JRadioButton();
    private JRadioButton optSounds = new JRadioButton();
    private JRadioButton optSmells = new JRadioButton();
    private JRadioButton optObjectTypeObj = new JRadioButton();
    private JRadioButton optObjectTypeMon = new JRadioButton();
    private JRadioButton optObjectLoadUnique = new JRadioButton();
    private JRadioButton optObjectLoadPresent = new JRadioButton();
    private JRadioButton optObjectLoadTrack = new JRadioButton();
    private JRadioButton optGuardCheckDefault = new JRadioButton();
    private JRadioButton optGuardCheckGPRESENT = new JRadioButton();
    private JRadioButton optGuardCheckFunction = new JRadioButton();
    private JRadioButton optGuardCheckInline = new JRadioButton();
    private JRadioButton optGuardMessageDefault = new JRadioButton();
    private JRadioButton optGuardMessageGMSG = new JRadioButton();
    private JRadioButton optGuardMessageFunction = new JRadioButton();
    private JRadioButton optGuardMessageInline = new JRadioButton();
    private JRadioButton optGuardWizardDefault = new JRadioButton();
    private JRadioButton optGuardWizardFunction = new JRadioButton();
    private JRadioButton optGuardWizardInline = new JRadioButton();
    private JList lstItems = new JList();
    private JList lstTerrains = new JList();
    private JList lstSenses = new JList();
    private JList lstObjects = new JList();
    private JList lstAvailableGuardObjects = new JList();
    private JList lstGuardDirections = new JList();
    private JList lstFunctions = new JList();
    private JList lstGuards = new JList();
    private JTextField txtRoomName = new JTextField();
    private JTextField txtItemNames = new JTextField();
    private JTextField txtVisibleDefaultSense = new JTextField();
    private JTextField txtDefaultSense = new JTextField();
    private JTextField txtSenseName = new JTextField();
    private JTextField txtObjectFileName = new JTextField();
    private JTextField txtObjectMessage = new JTextField();
    private JTextField txtRoomShortDesc = new JTextField();
    private JTextField txtRoomCodeDesc = new JTextField();
    private JTextField txtBoundary = new JTextField();
    private JTextField txtGuardCheckInline = new JTextField();
    private JTextField txtGuardMessageInline = new JTextField();
    private JTextField txtGuardWizardInline = new JTextField();
    private JTextField txtGuardMessagePlayer = new JTextField();
    private JTextField txtGuardMessageRoom = new JTextField();
    private JTextField txtFunctionArguments = new JTextField();
    private JTextField txtFunctionName = new JTextField();
    private JTextField txtFunctionReturnType = new JTextField();
    private JTextArea txtSenseDescription = new JTextArea();
    private JTextArea txtRoomLongDesc = new JTextArea();
    private JTextArea txtItemDescription = new JTextArea();
    private JTextArea txtRoomLongDescCopy = new JTextArea();
    private JTextArea txtRoomCode = new JTextArea();
    private JTextArea txtFunctionBody = new JTextArea();
    private JSlider sldrLightLevel = new JSlider();
    private JComboBox selGuardCheckFunctions = new JComboBox();
    private JComboBox selGuardMessageFunctions = new JComboBox();
    private JComboBox selGuardWizardFunctions = new JComboBox();
    private JCheckBox cbRoomDescLongQuotes = new JCheckBox();

    private JLabel lblRoomNumber = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JLabel jLabel14 = new JLabel();
    private JLabel jLabel15 = new JLabel();
    private JLabel jLabel16 = new JLabel();
    private JLabel jLabel17 = new JLabel();
    private JLabel jLabel18 = new JLabel();
    private JLabel jLabel19 = new JLabel();
    private JLabel jLabel20 = new JLabel();
    private JLabel jLabel21 = new JLabel();
    private JLabel jLabel22 = new JLabel();
    private JLabel jLabel23 = new JLabel();
    private JLabel jLabel24 = new JLabel();
    private JLabel jLabel25 = new JLabel();
    private JLabel jLabel26 = new JLabel();
    private JLabel jLabel27 = new JLabel();
    private JLabel jLabel29 = new JLabel();
    private JLabel jLabel30 = new JLabel();
    private JLabel jLabel31 = new JLabel();
    private JLabel jLabel32 = new JLabel();
    private JLabel jLabel33 = new JLabel();
    private JLabel jLabel34 = new JLabel();
    private JLabel jLabel35 = new JLabel();
    private JLabel jLabel42 = new JLabel();
    private JLabel jLabel43 = new JLabel();
    private JLabel jLabel44 = new JLabel();
    private JLabel jLabel45 = new JLabel();
    private JLabel jLabel46 = new JLabel();
    private JLabel jLabel47 = new JLabel();
    private JPanel pnlRoomBasics = new JPanel();
    private JPanel pnlRoomBasicsLayout1 = new JPanel();
    private JPanel pnlRoomBasicsLayout2 = new JPanel();
    private JPanel pnlRoomBasicsLayout3 = new JPanel();
    private JPanel pnlRoomBasicsLayout4 = new JPanel();
    private JPanel pnlRoomBasicsLayout5 = new JPanel();
    private JPanel pnlRoomBasicsLayout6 = new JPanel();
    private JPanel pnlRoomBasicsLayout7 = new JPanel();
    private JPanel pnlRoomItems = new JPanel();
    private JPanel pnlRoomItemsLayout1 = new JPanel();
    private JPanel pnlRoomItemsLayout2 = new JPanel();
    private JPanel pnlRoomItemsLayout3 = new JPanel();
    private JPanel pnlRoomItemsLayout4 = new JPanel();
    private JPanel pnlRoomItemsLayout5 = new JPanel();
    private JPanel pnlRoomItemsLayout6 = new JPanel();
    private JPanel pnlRoomMisc = new JPanel();
    private JPanel pnlRoomMiscLayout1 = new JPanel();
    private JPanel pnlRoomMiscLayout2 = new JPanel();
    private JPanel pnlRoomMiscLayout3 = new JPanel();
    private JPanel pnlRoomMiscLayout4 = new JPanel();
    private JPanel pnlRoomMiscLayout5 = new JPanel();
    private JPanel pnlRoomSenses = new JPanel();
    private JPanel pnlRoomSensesLayout1 = new JPanel();
    private JPanel pnlRoomSensesLayout2 = new JPanel();
    private JPanel pnlRoomSensesLayout3 = new JPanel();
    private JPanel pnlRoomSensesLayout4 = new JPanel();
    private JPanel pnlRoomSensesLayout5 = new JPanel();
    private JPanel pnlRoomSensesLayout6 = new JPanel();
    private JPanel pnlRoomSensesLayout7 = new JPanel();
    private JPanel pnlRoomSensesLayout8 = new JPanel();
    private JPanel pnlRoomSensesLayout9 = new JPanel();
    private JPanel pnlRoomSensesLayout10 = new JPanel();
    private JPanel pnlRoomSensesLayout11 = new JPanel();
    private JPanel pnlRoomObjects = new JPanel();
    private JPanel pnlRoomObjectsLayout1 = new JPanel();
    private JPanel pnlRoomObjectsLayout2 = new JPanel();
    private JPanel pnlRoomObjectsLayout3 = new JPanel();
    private JPanel pnlRoomObjectsLayout4 = new JPanel();
    private JPanel pnlRoomObjectsLayout5 = new JPanel();
    private JPanel pnlRoomObjectsLayout6 = new JPanel();
    private JPanel pnlRoomObjectsLayout7 = new JPanel();
    private JPanel pnlRoomObjectsLayout8 = new JPanel();
    private JPanel pnlRoomObjectsLayout9 = new JPanel();
    private JPanel pnlRoomObjectsLayout10 = new JPanel();

    private JPanel pnlRoomGuards = new JPanel();
    private JPanel pnlRoomGuardsLayout1 = new JPanel();
    private JPanel pnlRoomGuardsLayout2 = new JPanel();
    private JPanel pnlRoomGuardsLayout3 = new JPanel();
    private JPanel pnlRoomGuardsLayout4 = new JPanel();
    private JPanel pnlRoomGuardsLayout5 = new JPanel();
    private JPanel pnlRoomGuardsLayout6 = new JPanel();
    private JPanel pnlRoomGuardsLayout7 = new JPanel();
    private JPanel pnlRoomGuardsLayout8 = new JPanel();
    private JPanel pnlRoomGuardsLayout9 = new JPanel();
    private JPanel pnlRoomGuardsLayout10 = new JPanel();
    private JPanel pnlRoomGuardsLayout11 = new JPanel();
    private JPanel pnlRoomGuardsLayout12 = new JPanel();
    private JPanel pnlRoomGuardsLayout13 = new JPanel();
    private JPanel pnlRoomGuardsLayout14 = new JPanel();
    private JPanel pnlRoomGuardsLayout15 = new JPanel();
    private JPanel pnlRoomGuardsLayout16 = new JPanel();
    private JPanel pnlRoomGuardsLayout17 = new JPanel();
    private JPanel pnlRoomGuardsLayout18 = new JPanel();
    private JPanel pnlRoomFunctions = new JPanel();
    private JPanel pnlRoomFunctionsLayout1 = new JPanel();
    private JPanel pnlRoomFunctionsLayout2 = new JPanel();
    private JPanel pnlRoomFunctionsLayout4 = new JPanel();
    private JPanel pnlRoomFunctionsLayout5 = new JPanel();
    private JPanel pnlRoomFunctionsLayout6 = new JPanel();
    private JPanel pnlRoomFunctionsLayout7 = new JPanel();
    private JPanel pnlRoomFunctionsLayout8 = new JPanel();
    private JPanel pnlRoomFunctionsLayout9 = new JPanel();
    private JPanel pnlRoomFunctionsLayout10 = new JPanel();
    private JPanel pnlRoomAdv = new JPanel();
    private JPanel pnlRoomAdvancedLayout1 = new JPanel();
    private JPanel pnlRoomAdvancedLayout2 = new JPanel();
    private JPanel pnlRoomAdvancedLayout3 = new JPanel();
    private JPanel pnlRoomAdvancedLayout4 = new JPanel();
    private JPanel pnlRoomEleMapper = new JPanel();
    private JPanel pnlRoomEleMapperLayout1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private BorderLayout borderLayout4 = new BorderLayout();
    private BorderLayout borderLayout5 = new BorderLayout();
    private BorderLayout borderLayout6 = new BorderLayout();
    private BorderLayout borderLayout12 = new BorderLayout();
    private BorderLayout borderLayout17 = new BorderLayout();
    private BorderLayout borderLayout18 = new BorderLayout();
    private BorderLayout borderLayout19 = new BorderLayout();
    private BorderLayout borderLayout20 = new BorderLayout();
    private BorderLayout borderLayout21 = new BorderLayout();
    private BorderLayout borderLayout22 = new BorderLayout();
    private BorderLayout borderLayout23 = new BorderLayout();
    private BorderLayout borderLayout24 = new BorderLayout();
    private BorderLayout borderLayout25 = new BorderLayout();
    private BorderLayout borderLayout26 = new BorderLayout();
    private BorderLayout borderLayout27 = new BorderLayout();
    private BorderLayout borderLayout28 = new BorderLayout();
    private BorderLayout borderLayout29 = new BorderLayout();
    private BorderLayout borderLayout30 = new BorderLayout();
    private BorderLayout borderLayout31 = new BorderLayout();
    private BorderLayout borderLayout32 = new BorderLayout();
    private BorderLayout borderLayout33 = new BorderLayout();
    private BorderLayout borderLayout34 = new BorderLayout();
    private BorderLayout borderLayout35 = new BorderLayout();
    private BorderLayout borderLayout36 = new BorderLayout();
    private BorderLayout borderLayout37 = new BorderLayout();
    private BorderLayout borderLayout38 = new BorderLayout();
    private BorderLayout borderLayout39 = new BorderLayout();
    private BorderLayout borderLayout40 = new BorderLayout();
    private BorderLayout borderLayout41 = new BorderLayout();
    private BorderLayout borderLayout42 = new BorderLayout();
    private BorderLayout borderLayout43 = new BorderLayout();
    private BorderLayout borderLayout44 = new BorderLayout();
    private BorderLayout borderLayout45 = new BorderLayout();
    private BorderLayout borderLayout46 = new BorderLayout();
    private BorderLayout borderLayout47 = new BorderLayout();
    private BorderLayout borderLayout48 = new BorderLayout();
    private BorderLayout borderLayout49 = new BorderLayout();
    private BorderLayout borderLayout50 = new BorderLayout();
    private BorderLayout borderLayout51 = new BorderLayout();
    private BorderLayout borderLayout53 = new BorderLayout();
    private BorderLayout borderLayout54 = new BorderLayout();
    private BorderLayout borderLayout55 = new BorderLayout();
    private BorderLayout borderLayout56 = new BorderLayout();
    private BorderLayout borderLayout57 = new BorderLayout();
    private BorderLayout borderLayout58 = new BorderLayout();
    private BorderLayout borderLayout59 = new BorderLayout();
    private BorderLayout borderLayout60 = new BorderLayout();
    private BorderLayout borderLayout61 = new BorderLayout();
    private BorderLayout borderLayout62 = new BorderLayout();
    private BorderLayout borderLayout63 = new BorderLayout();
    private BorderLayout borderLayout64 = new BorderLayout();
    private BorderLayout borderLayout65 = new BorderLayout();
    private BorderLayout borderLayout66 = new BorderLayout();
    private BorderLayout borderLayout67 = new BorderLayout();
    private BorderLayout borderLayout68 = new BorderLayout();
    private BorderLayout borderLayout69 = new BorderLayout();
    private BorderLayout borderLayout70 = new BorderLayout();
    private BorderLayout borderLayout71 = new BorderLayout();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout6 = new VerticalFlowLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JScrollPane jScrollPane4 = new JScrollPane();
    private JScrollPane jScrollPane5 = new JScrollPane();
    private JScrollPane jScrollPane6 = new JScrollPane();
    private JScrollPane jScrollPane7 = new JScrollPane();
    private JScrollPane jScrollPane8 = new JScrollPane();
    private JScrollPane jScrollPane9 = new JScrollPane();
    private JScrollPane jScrollPane10 = new JScrollPane();
    private JScrollPane jScrollPane11 = new JScrollPane();
    private JScrollPane jScrollPane12 = new JScrollPane();
    private JScrollPane jScrollPane13 = new JScrollPane();
    private JScrollPane jScrollPane14 = new JScrollPane();


    public RoomTabs(EleFrame eleFrame, JPanel pnlMap) {
        this.eleFrame = eleFrame;
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit(pnlMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit(final JPanel pnlMap) throws Exception {
        Insets zeroInsets = new Insets(0, 0, 0, 0);
        Insets optionInsets = new Insets(0, 2, 0, 0);
        Font textFont = new Font("SansSerif", 0, 12);
        Font codeFont = new Font("Monospaced", 0, 11);
        Border borderInset5 = BorderFactory.createEmptyBorder(5,5,5,5);

        // Buttons
        btnItemAdd.setText("Add/Update");
        btnItemAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnItemAdd_actionPerformed(e);
            }
        });
        btnItemDelete.setText("Clear/Delete");
        btnItemDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnItemDelete_actionPerformed(e);
            }
        });
        btnSenseAdd.setText("Add/Update");
        btnSenseAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSenseAdd_actionPerformed(e);
            }
        });
        btnSenseDelete.setText("Clear/Delete");
        btnSenseDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSenseDelete_actionPerformed(e);
            }
        });
        btnObjectAdd.setText("Add/Update");
        btnObjectAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObjectAdd_actionPerformed(e);
            }
        });
        btnObjectDelete.setText("Clear/Delete");
        btnObjectDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObjectDelete_actionPerformed(e);
            }
        });
        btnFunctionAdd.setText("Add/Update");
        btnFunctionAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFunctionAdd_actionPerformed(e);
            }
        });
        btnFunctionDelete.setText("Clear/Delete");
        btnFunctionDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFunctionDelete_actionPerformed(e);
            }
        });
        btnGuardAdd.setText("Add/Update");
        btnGuardAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnGuardAdd_actionPerformed(e);
            }
        });
        btnGuardDelete.setText("Clear/Del");
        btnGuardDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnGuardDelete_actionPerformed(e);
            }
        });
        btnRoomColour.setPreferredSize(new Dimension(40, 40));
        btnRoomColour.setFocusPainted(false);
        btnRoomColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRoomColour_actionPerformed(btnRoomColour, pnlMap, e);
            }
        });

        // Other controls
        lstItems.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstItems_valueChanged(e);
            }
        });
        lstSenses.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstSenses_valueChanged(e);
            }
        });
        lstObjects.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstObjects_valueChanged(e);
            }
        });

        lstObjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //String uuid = monsterListTable.getValueAt(monsterListTable.getSelectedRow(), 0).toString();
                if (e.getClickCount() == 2) {
                    LoadedObject loadedObject = (LoadedObject) lstObjects.getSelectedValue();
                    if(loadedObject.getObjectType() == LoadedObject.OBJECT_TYPE_MON) {
                        if(loadedObject.getUuid() != null) {
                            eleFrame.showMonsterDialog(loadedObject.getUuid());
                        } else {
                            // Legacy loadable - make best effort to create a new Uuid
                            Monster monster = new Monster();
                            loadedObject.setUuid(monster.getUuid());
                            monster.setFileName(loadedObject.getFileName());
                            eleFrame.showMonsterDialog(monster);
                        }
                    }
                }

            }
        });
        lstFunctions.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstFunctions_valueChanged(e);
            }
        });



        // Other Listeners
        optIndoors.setText("Indoors");
        optIndoors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optIndoors_actionPerformed(e);
            }
        });
        optOutdoors.setText("Outdoors");
        optOutdoors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optOutdoors_actionPerformed(e);
            }
        });
        optSmells.setText("Smells");
        optSmells.setSelected(true);
        optSmells.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                optSmells_itemStateChanged(e);
            }
        });
        optSounds.setText("Sounds");
        optSounds.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                optSounds_itemStateChanged(e);
            }
        });

        // Misc
        buttonGroupRoomOutDoors.add(optIndoors);
        buttonGroupRoomOutDoors.add(optOutdoors);
        buttonGroupRoomSense.add(optSmells);
        buttonGroupRoomSense.add(optSounds);
        buttonGroupRoomObjectType.add(optObjectTypeMon);
        buttonGroupRoomObjectType.add(optObjectTypeObj);
        buttonGroupRoomObjectLoadType.add(optObjectLoadUnique);
        buttonGroupRoomObjectLoadType.add(optObjectLoadPresent);
        buttonGroupRoomObjectLoadType.add(optObjectLoadTrack);
        buttonGroupGuardCheck.add(optGuardCheckDefault);
        buttonGroupGuardCheck.add(optGuardCheckFunction);
        buttonGroupGuardCheck.add(optGuardCheckInline);
        buttonGroupGuardCheck.add(optGuardCheckGPRESENT);
        buttonGroupGuardMessage.add(optGuardMessageDefault);
        buttonGroupGuardMessage.add(optGuardMessageGMSG);
        buttonGroupGuardMessage.add(optGuardMessageFunction);
        buttonGroupGuardMessage.add(optGuardMessageInline);
        buttonGroupGuardWizard.add(optGuardWizardDefault);
        buttonGroupGuardWizard.add(optGuardWizardFunction);
        buttonGroupGuardWizard.add(optGuardWizardInline);
        lstItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstSenses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstAvailableGuardObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstGuards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optObjectTypeObj.setText("Object");
        optObjectTypeMon.setText("Monster");
        optObjectLoadUnique.setText("Only ever load one (Unique)");
        optObjectLoadPresent.setText("Load if object is not present");
        optObjectLoadTrack.setText("Load when previous one is destroyed");
        optGuardCheckDefault.setMargin(optionInsets);
        optGuardCheckDefault.setSelected(true);
        optGuardMessageDefault.setSelected(true);
        optGuardWizardDefault.setSelected(true);
        optGuardCheckDefault.setText("Default");
        optGuardCheckGPRESENT.setMargin(optionInsets);
        optGuardCheckGPRESENT.setText("G_PRESENT");
        optGuardCheckFunction.setMargin(optionInsets);
        optGuardCheckFunction.setText("Function");
        optGuardCheckInline.setMargin(optionInsets);
        optGuardCheckInline.setText("Inline");
        optGuardMessageInline.setMargin(optionInsets);
        optGuardMessageInline.setText("Inline");
        optGuardMessageFunction.setMargin(optionInsets);
        optGuardMessageFunction.setText("Function");
        optGuardMessageDefault.setMargin(optionInsets);
        optGuardMessageDefault.setText("Default");
        optGuardMessageGMSG.setMargin(optionInsets);
        optGuardMessageGMSG.setText("Player msg");
        optGuardWizardDefault.setMargin(optionInsets);
        optGuardWizardDefault.setText("Default");
        optGuardWizardFunction.setMargin(optionInsets);
        optGuardWizardFunction.setText("Function    ");
        optGuardWizardInline.setMargin(optionInsets);
        optGuardWizardInline.setText("Inline");
        lstFunctions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        txtRoomLongDesc.setWrapStyleWord(true);
        txtRoomLongDesc.setLineWrap(true);
        txtRoomLongDesc.setFont(textFont);

        SpellChecker.register(txtRoomLongDesc);

        txtRoomLongDescCopy.setFont(new Font("SansSerif", 0, 12));
        txtRoomLongDescCopy.setDisabledTextColor(Color.black);
        txtRoomLongDescCopy.setLineWrap(true);
        txtRoomLongDescCopy.setEnabled(false);
        txtRoomLongDescCopy.setWrapStyleWord(true);
        txtRoomLongDescCopy.setDocument(txtRoomLongDesc.getDocument());
        txtRoomLongDescCopy.setBackground(EleConstants.DISABLED_COLOUR);
        txtRoomName.setMinimumSize(new Dimension(120, 21));
        txtRoomName.setPreferredSize(new Dimension(120, 21));
        txtItemDescription.setWrapStyleWord(true);
        txtItemDescription.setLineWrap(true);
        txtItemDescription.setBorder(null);
        txtItemDescription.setFont(textFont);
        txtSenseDescription.setWrapStyleWord(true);
        txtSenseDescription.setLineWrap(true);
        txtSenseDescription.setFont(textFont);
        txtGuardCheckInline.setFont(codeFont);
        txtGuardCheckInline.setPreferredSize(new Dimension(100, 21));
        selGuardCheckFunctions.setPreferredSize(new Dimension(100, 21));
        txtGuardMessageInline.setFont(codeFont);
        txtGuardMessageInline.setPreferredSize(new Dimension(100, 21));
        selGuardMessageFunctions.setPreferredSize(new Dimension(100, 21));
        txtGuardWizardInline.setFont(codeFont);
        txtGuardWizardInline.setPreferredSize(new Dimension(100, 21));
        selGuardWizardFunctions.setPreferredSize(new Dimension(100, 21));
        txtGuardMessageRoom.setPreferredSize(new Dimension(100, 21));
        txtGuardMessagePlayer.setPreferredSize(new Dimension(100, 21));
        sldrLightLevel.setMinorTickSpacing(1);
        sldrLightLevel.setMajorTickSpacing(2);
        sldrLightLevel.setPaintLabels(true);
        sldrLightLevel.setMinimum(-4);
        sldrLightLevel.setValue(3);
        sldrLightLevel.setPaintTicks(true);
        sldrLightLevel.setMaximum(10);
        sldrLightLevel.setSnapToTicks(true);
        jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane2.setMinimumSize(new Dimension(150, 0));
        jScrollPane2.setPreferredSize(new Dimension(150, 0));
        jScrollPane3.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane4.setMinimumSize(new Dimension(150, 0));
        jScrollPane4.setPreferredSize(new Dimension(150, 0));
        jScrollPane5.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane6.setMinimumSize(new Dimension(150, 0));
        jScrollPane6.setPreferredSize(new Dimension(150, 0));
        jScrollPane7.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane7.setMinimumSize(new Dimension(150, 0));
        jScrollPane7.setPreferredSize(new Dimension(150, 0));
        jScrollPane8.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane8.setMinimumSize(new Dimension(0, 55));
        jScrollPane8.setPreferredSize(new Dimension(0, 55));
        jScrollPane10.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane10.setMinimumSize(new Dimension(150, 0));
        jScrollPane10.setPreferredSize(new Dimension(150, 0));
        jScrollPane12.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane12.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setMinimumSize(new Dimension(150, 0));
        jScrollPane12.setPreferredSize(new Dimension(150, 0));
        jScrollPane14.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane14.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setMinimumSize(new Dimension(150, 0));
        jScrollPane14.setPreferredSize(new Dimension(150, 0));
        jScrollPane11.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane11.setPreferredSize(new Dimension(100, 110));
        jLabel1.setText("Room Number ");
        jLabel2.setText("Short Description");
        jLabel3.setText("Long Description");
        jLabel4.setText("Room Name");
        jLabel26.setText("Description for code comment");
        jLabel27.setText("Copy of Long Description");
        jLabel8.setText("Items currently in room");
        jLabel11.setText("Item(s) (Comma seperated)");
        jLabel12.setText("Description");
        jLabel13.setText("This room is...");
        jLabel14.setText("Terrains (max " + Room.MAX_TERRAINS + ")");
        jLabel15.setText("Light Level");
        jLabel16.setText("Default (will have to be invoked)");
        jLabel17.setText("Default (will appear in the room)");
        jLabel18.setText("What do you want to sense");
        jLabel19.setText("Description");
        jLabel20.setText("Currently in room");
        jLabel21.setText("Currently in room");
        jLabel22.setText("File Name");
        jLabel23.setText("Message to room when Object is loaded");
        jLabel25.setText("Boundary");
        jLabel29.setText("Extra code - anything entered here will be placed in the file as code");
        jLabel24.setText("Room Colour");
        jLabel35.setText("Objects available");
        jLabel30.setText("Directions to guard");
        jLabel31.setText("Check that object makes");
        jLabel32.setText("Room msg");
        jLabel33.setText("Messages");
        jLabel34.setText("Wizard message");
        jLabel42.setText("Functions");
        jLabel43.setText("Body (Do not include the curly braces)");
        jLabel44.setText("Arguments (Do not enclose in braces)");
        jLabel45.setText("Name");
        jLabel46.setText("Return type");
        jLabel47.setText("Guards");
        //todo check this
        jLabel31.setVerifyInputWhenFocusTarget(true);
        jLabel31.setVerticalAlignment(SwingConstants.TOP);
        jLabel31.setVerticalTextPosition(SwingConstants.TOP);

        // Layout
        // Basics
        pnlRoomBasicsLayout1.setLayout(borderLayout5);
        pnlRoomBasicsLayout1.add(lblRoomNumber, BorderLayout.CENTER);
        pnlRoomBasicsLayout1.add(jLabel1, BorderLayout.WEST);
        pnlRoomBasicsLayout2.setLayout(borderLayout1);
        pnlRoomBasicsLayout2.add(jLabel26, BorderLayout.NORTH);
        pnlRoomBasicsLayout2.add(txtRoomCodeDesc, BorderLayout.SOUTH);

        pnlRoomBasicsLayout3.setLayout(borderLayout2);
        pnlRoomBasicsLayout3.add(jLabel4, BorderLayout.NORTH);
        pnlRoomBasicsLayout3.add(txtRoomName, BorderLayout.SOUTH);
        pnlRoomBasicsLayout4.setBorder(borderInset5);
        pnlRoomBasicsLayout4.setLayout(borderLayout3);
        pnlRoomBasicsLayout4.add(jLabel3, BorderLayout.NORTH);
        cbRoomDescLongQuotes.setText("Quotes/Wrap");
        cbRoomDescLongQuotes.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if(eleFrame.getRoomHelper().hasSingleRoomSelected()) {
                    eleFrame.getRoomHelper().getSelectedRoom().setLongDescQuotes(cbRoomDescLongQuotes.isSelected());
                }
            }
        });

        cbRoomDescLongQuotes.setSelected(true);
        pnlRoomBasicsLayout4.add(cbRoomDescLongQuotes, BorderLayout.EAST);
        pnlRoomBasicsLayout4.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(txtRoomLongDesc, null);
        pnlRoomBasicsLayout5.setLayout(borderLayout4);
        pnlRoomBasicsLayout5.add(pnlRoomBasicsLayout2, BorderLayout.CENTER);
        pnlRoomBasicsLayout5.add(pnlRoomBasicsLayout3, BorderLayout.EAST);
        pnlRoomBasicsLayout7.setLayout(borderLayout47);
        pnlRoomBasicsLayout7.add(jLabel2, BorderLayout.NORTH);
        pnlRoomBasicsLayout7.add(txtRoomShortDesc, BorderLayout.SOUTH);
        pnlRoomBasicsLayout6.setLayout(verticalFlowLayout1);
        pnlRoomBasicsLayout6.add(pnlRoomBasicsLayout1, null);
        pnlRoomBasicsLayout6.add(pnlRoomBasicsLayout5, null);
        pnlRoomBasicsLayout6.add(pnlRoomBasicsLayout7, null);
        pnlRoomBasics.setLayout(borderLayout6);
        pnlRoomBasics.add(pnlRoomBasicsLayout4, BorderLayout.CENTER);
        pnlRoomBasics.add(pnlRoomBasicsLayout6, BorderLayout.NORTH);
        // Items
        jScrollPane2.getViewport().add(lstItems, null);
        pnlRoomItemsLayout1.setLayout(borderLayout12);
        pnlRoomItemsLayout1.setBorder(borderInset5);
        pnlRoomItemsLayout1.add(jLabel8, BorderLayout.NORTH);
        pnlRoomItemsLayout1.add(jScrollPane2, BorderLayout.WEST);
        pnlRoomItemsLayout4.add(btnItemAdd, null);
        pnlRoomItemsLayout4.add(btnItemDelete, null);
        pnlRoomItemsLayout5.setLayout(borderLayout17);
        pnlRoomItemsLayout5.add(jLabel11, BorderLayout.NORTH);
        pnlRoomItemsLayout5.add(txtItemNames, BorderLayout.CENTER);
        pnlRoomItemsLayout5.add(jLabel12, BorderLayout.SOUTH);
        pnlRoomItemsLayout3.setLayout(borderLayout20);
        pnlRoomItemsLayout3.add(pnlRoomItemsLayout4, BorderLayout.NORTH);
        pnlRoomItemsLayout3.add(pnlRoomItemsLayout5, BorderLayout.SOUTH);
        jScrollPane3.getViewport().add(txtItemDescription, null);
        pnlRoomItemsLayout2.setLayout(borderLayout18);
        pnlRoomItemsLayout2.setBorder(borderInset5);
        pnlRoomItemsLayout2.add(pnlRoomItemsLayout3, BorderLayout.NORTH);
        pnlRoomItemsLayout2.add(jScrollPane3, BorderLayout.CENTER);
        jScrollPane8.getViewport().add(txtRoomLongDescCopy, null);
        pnlRoomItemsLayout6.setLayout(borderLayout48);
        pnlRoomItemsLayout6.setBorder(borderInset5);
        pnlRoomItemsLayout6.add(jLabel27, BorderLayout.NORTH);
        pnlRoomItemsLayout6.add(jScrollPane8, BorderLayout.CENTER);
        pnlRoomItems.setLayout(borderLayout19);
        pnlRoomItems.add(pnlRoomItemsLayout1, BorderLayout.WEST);
        pnlRoomItems.add(pnlRoomItemsLayout2, BorderLayout.CENTER);
        pnlRoomItems.add(pnlRoomItemsLayout6, BorderLayout.SOUTH);
        // Misc
        pnlRoomMiscLayout3.setLayout(borderLayout21);
        pnlRoomMiscLayout3.add(optIndoors, BorderLayout.WEST);
        pnlRoomMiscLayout3.add(optOutdoors, BorderLayout.EAST);
        pnlRoomMiscLayout3.add(jLabel13, BorderLayout.NORTH);
        jScrollPane4.getViewport().add(lstTerrains, null);
        pnlRoomMiscLayout4.setLayout(borderLayout22);
        pnlRoomMiscLayout4.add(jLabel14, BorderLayout.NORTH);
        pnlRoomMiscLayout4.add(jScrollPane4, BorderLayout.WEST);
        pnlRoomMiscLayout1.setBorder(borderInset5);
        pnlRoomMiscLayout1.setLayout(borderLayout23);
        pnlRoomMiscLayout1.add(pnlRoomMiscLayout3, BorderLayout.NORTH);
        pnlRoomMiscLayout1.add(pnlRoomMiscLayout4, BorderLayout.CENTER);
        pnlRoomMiscLayout5.setLayout(borderLayout25);
        pnlRoomMiscLayout5.add(sldrLightLevel, BorderLayout.WEST);
        pnlRoomMiscLayout5.add(jLabel15, BorderLayout.NORTH);
        pnlRoomMiscLayout2.setBorder(borderInset5);
        pnlRoomMiscLayout2.setLayout(borderLayout26);
        pnlRoomMiscLayout2.add(pnlRoomMiscLayout5, BorderLayout.NORTH);
        pnlRoomMisc.setLayout(borderLayout24);
        pnlRoomMisc.add(pnlRoomMiscLayout1, BorderLayout.WEST);
        pnlRoomMisc.add(pnlRoomMiscLayout2, BorderLayout.EAST);
        // Senses
        pnlRoomSensesLayout10.setLayout(verticalFlowLayout4);
        pnlRoomSensesLayout10.add(btnSenseAdd, null);
        pnlRoomSensesLayout10.add(btnSenseDelete, null);
        pnlRoomSensesLayout11.setLayout(borderLayout36);
        pnlRoomSensesLayout11.add(jLabel18, BorderLayout.NORTH);
        pnlRoomSensesLayout11.add(txtSenseName, BorderLayout.SOUTH);
        pnlRoomSensesLayout6.add(optSmells, null);
        pnlRoomSensesLayout6.add(optSounds, null);
        pnlRoomSensesLayout7.setLayout(borderLayout30);
        pnlRoomSensesLayout7.add(jLabel19, BorderLayout.SOUTH);
        pnlRoomSensesLayout7.add(pnlRoomSensesLayout11, BorderLayout.CENTER);
        pnlRoomSensesLayout8.setLayout(borderLayout27);
        pnlRoomSensesLayout8.add(jLabel17, BorderLayout.NORTH);
        pnlRoomSensesLayout8.add(txtVisibleDefaultSense, BorderLayout.CENTER);
        pnlRoomSensesLayout9.setLayout(borderLayout28);
        pnlRoomSensesLayout9.add(jLabel16, BorderLayout.NORTH);
        pnlRoomSensesLayout9.add(txtDefaultSense, BorderLayout.CENTER);
        pnlRoomSensesLayout3.setLayout(borderLayout33);
        pnlRoomSensesLayout3.add(pnlRoomSensesLayout6, BorderLayout.NORTH);
        pnlRoomSensesLayout3.add(jLabel20, BorderLayout.SOUTH);
        pnlRoomSensesLayout4.setLayout(borderLayout29);
        pnlRoomSensesLayout4.add(pnlRoomSensesLayout9, BorderLayout.NORTH);
        pnlRoomSensesLayout4.add(pnlRoomSensesLayout8, BorderLayout.SOUTH);
        jScrollPane5.getViewport().add(txtSenseDescription, null);
        pnlRoomSensesLayout5.setLayout(borderLayout31);
        pnlRoomSensesLayout5.add(pnlRoomSensesLayout7, BorderLayout.NORTH);
        pnlRoomSensesLayout5.add(pnlRoomSensesLayout10, BorderLayout.EAST);
        pnlRoomSensesLayout5.add(jScrollPane5, BorderLayout.CENTER);
        jScrollPane6.getViewport().add(lstSenses, null);
        pnlRoomSensesLayout1.setBorder(borderInset5);
        pnlRoomSensesLayout1.setLayout(borderLayout34);
        pnlRoomSensesLayout1.add(pnlRoomSensesLayout3, BorderLayout.NORTH);
        pnlRoomSensesLayout1.add(jScrollPane6, BorderLayout.CENTER);
        pnlRoomSensesLayout2.setBorder(borderInset5);
        pnlRoomSensesLayout2.setLayout(borderLayout32);
        pnlRoomSensesLayout2.add(pnlRoomSensesLayout5, BorderLayout.CENTER);
        pnlRoomSensesLayout2.add(pnlRoomSensesLayout4, BorderLayout.NORTH);
        pnlRoomSenses.setLayout(borderLayout35);
        pnlRoomSenses.add(pnlRoomSensesLayout1, BorderLayout.WEST);
        pnlRoomSenses.add(pnlRoomSensesLayout2, BorderLayout.CENTER);
        // Objects
        pnlRoomObjectsLayout9.add(optObjectTypeMon, null);
        pnlRoomObjectsLayout9.add(optObjectTypeObj, null);
        pnlRoomObjectsLayout10.setLayout(borderLayout38);
        pnlRoomObjectsLayout10.add(txtObjectFileName, BorderLayout.SOUTH);
        pnlRoomObjectsLayout10.add(jLabel22, BorderLayout.NORTH);
        pnlRoomObjectsLayout3.setLayout(borderLayout39);
        pnlRoomObjectsLayout3.add(pnlRoomObjectsLayout9, BorderLayout.NORTH);
        pnlRoomObjectsLayout3.add(pnlRoomObjectsLayout10, BorderLayout.SOUTH);
        pnlRoomObjectsLayout5.setLayout(borderLayout40);
        pnlRoomObjectsLayout5.add(txtObjectMessage, BorderLayout.SOUTH);
        pnlRoomObjectsLayout5.add(jLabel23, BorderLayout.NORTH);
        pnlRoomObjectsLayout6.setLayout(verticalFlowLayout2);
        pnlRoomObjectsLayout6.add(optObjectLoadTrack, null);
        pnlRoomObjectsLayout6.add(optObjectLoadPresent, null);
        pnlRoomObjectsLayout6.add(optObjectLoadUnique, null);
        pnlRoomObjectsLayout8.setLayout(verticalFlowLayout3);
        pnlRoomObjectsLayout8.add(btnObjectAdd, null);
        pnlRoomObjectsLayout8.add(btnObjectDelete, null);
        pnlRoomObjectsLayout7.add(pnlRoomObjectsLayout8, null);
        pnlRoomObjectsLayout4.setLayout(borderLayout41);
        pnlRoomObjectsLayout4.add(pnlRoomObjectsLayout5, BorderLayout.NORTH);
        pnlRoomObjectsLayout4.add(pnlRoomObjectsLayout6, BorderLayout.WEST);
        pnlRoomObjectsLayout4.add(pnlRoomObjectsLayout7, BorderLayout.EAST);
        jScrollPane7.getViewport().add(lstObjects, null);
        pnlRoomObjectsLayout1.setBorder(borderInset5);
        pnlRoomObjectsLayout1.setLayout(borderLayout37);
        pnlRoomObjectsLayout1.add(jLabel21, BorderLayout.NORTH);
        pnlRoomObjectsLayout1.add(jScrollPane7, BorderLayout.CENTER);
        pnlRoomObjectsLayout2.setBorder(borderInset5);
        pnlRoomObjectsLayout2.setLayout(borderLayout42);
        pnlRoomObjectsLayout2.add(pnlRoomObjectsLayout3, BorderLayout.NORTH);
        pnlRoomObjectsLayout2.add(pnlRoomObjectsLayout4, BorderLayout.SOUTH);
        pnlRoomObjects.setLayout(borderLayout43);
        pnlRoomObjects.add(pnlRoomObjectsLayout1, BorderLayout.WEST);
        pnlRoomObjects.add(pnlRoomObjectsLayout2, BorderLayout.CENTER);


        // Guards
        jScrollPane10.getViewport().add(lstAvailableGuardObjects, null);
        pnlRoomGuardsLayout18.setLayout(borderLayout71);
        pnlRoomGuardsLayout18.add(jScrollPane10, BorderLayout.CENTER);
        pnlRoomGuardsLayout18.add(jLabel35, BorderLayout.NORTH);
        jScrollPane14.getViewport().add(lstGuards, null);
        pnlRoomGuardsLayout17.setLayout(borderLayout61);
        pnlRoomGuardsLayout17.add(jScrollPane14, BorderLayout.CENTER);
        pnlRoomGuardsLayout17.add(jLabel47, BorderLayout.NORTH);
        pnlRoomGuardsLayout16.setLayout(gridBagLayout3);
        pnlRoomGuardsLayout16.add(optGuardWizardDefault, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(optGuardWizardFunction, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(selGuardWizardFunctions, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(optGuardWizardInline, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(txtGuardWizardInline, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.setLayout(gridBagLayout1);
        pnlRoomGuardsLayout12.add(optGuardMessageDefault, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(optGuardMessageGMSG, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(txtGuardMessagePlayer, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(jLabel32, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
        pnlRoomGuardsLayout12.add(txtGuardMessageRoom, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(optGuardMessageFunction, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(selGuardMessageFunctions, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(optGuardMessageInline, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(txtGuardMessageInline, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.setLayout(gridBagLayout1);
        pnlRoomGuardsLayout10.add(optGuardCheckDefault, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(optGuardCheckGPRESENT, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(optGuardCheckFunction, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(selGuardCheckFunctions, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(optGuardCheckInline, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(txtGuardCheckInline, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout15.setPreferredSize(new Dimension(-1, 20));
        pnlRoomGuardsLayout15.add(jLabel34, null);
        pnlRoomGuardsLayout14.add(btnGuardAdd, null);
        pnlRoomGuardsLayout14.add(btnGuardDelete, null);
        pnlRoomGuardsLayout13.setBorder(BorderFactory.createEtchedBorder());
        pnlRoomGuardsLayout13.setLayout(borderLayout59);
        pnlRoomGuardsLayout13.add(pnlRoomGuardsLayout15, BorderLayout.NORTH);
        pnlRoomGuardsLayout13.add(pnlRoomGuardsLayout16, BorderLayout.SOUTH);
        pnlRoomGuardsLayout11.setPreferredSize(new Dimension(-1, 20));
        pnlRoomGuardsLayout11.add(jLabel33, null);
        pnlRoomGuardsLayout9.setPreferredSize(new Dimension(-1, 20));
        pnlRoomGuardsLayout9.add(jLabel31, null);
        pnlRoomGuardsLayout8.setLayout(borderLayout60);
        pnlRoomGuardsLayout8.add(pnlRoomGuardsLayout13, BorderLayout.NORTH);
        pnlRoomGuardsLayout8.add(pnlRoomGuardsLayout14, BorderLayout.SOUTH);
        pnlRoomGuardsLayout7.setBorder(BorderFactory.createEtchedBorder());
        pnlRoomGuardsLayout7.setLayout(borderLayout57);
        pnlRoomGuardsLayout7.add(pnlRoomGuardsLayout11, BorderLayout.NORTH);
        pnlRoomGuardsLayout7.add(pnlRoomGuardsLayout12, BorderLayout.SOUTH);
        pnlRoomGuardsLayout6.setBorder(BorderFactory.createEtchedBorder());
        pnlRoomGuardsLayout6.setLayout(borderLayout58);
        pnlRoomGuardsLayout6.add(pnlRoomGuardsLayout9, BorderLayout.NORTH);
        pnlRoomGuardsLayout6.add(pnlRoomGuardsLayout10, BorderLayout.SOUTH);
        jScrollPane11.getViewport().add(lstGuardDirections, null);
        pnlRoomGuardsLayout5.setLayout(borderLayout56);
        pnlRoomGuardsLayout5.add(jScrollPane11, BorderLayout.CENTER);
        pnlRoomGuardsLayout5.add(jLabel30, BorderLayout.NORTH);
        pnlRoomGuardsLayout4.setLayout(borderLayout55);
        pnlRoomGuardsLayout4.add(pnlRoomGuardsLayout7, BorderLayout.NORTH);
        pnlRoomGuardsLayout4.add(pnlRoomGuardsLayout8, BorderLayout.SOUTH);
        pnlRoomGuardsLayout3.setLayout(borderLayout54);
        pnlRoomGuardsLayout3.add(pnlRoomGuardsLayout5, BorderLayout.NORTH);
        pnlRoomGuardsLayout3.add(pnlRoomGuardsLayout6, BorderLayout.SOUTH);
        pnlRoomGuardsLayout2.setBorder(borderInset5);
        pnlRoomGuardsLayout2.setLayout(borderLayout53);
        pnlRoomGuardsLayout2.add(pnlRoomGuardsLayout3, BorderLayout.WEST);
        pnlRoomGuardsLayout2.add(pnlRoomGuardsLayout4, BorderLayout.EAST);
        pnlRoomGuardsLayout1.setBorder(borderInset5);
        pnlRoomGuardsLayout1.setLayout(gridBagLayout2);
        pnlRoomGuardsLayout1.add(pnlRoomGuardsLayout17,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, zeroInsets, 0, 0));
        pnlRoomGuardsLayout1.add(pnlRoomGuardsLayout18,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, zeroInsets, 0, 0));
        pnlRoomGuards.setLayout(borderLayout51);
        pnlRoomGuards.add(pnlRoomGuardsLayout1, BorderLayout.WEST);
        pnlRoomGuards.add(pnlRoomGuardsLayout2, BorderLayout.CENTER);
        // Functions
        pnlRoomFunctionsLayout10.setLayout(borderLayout68);
        pnlRoomFunctionsLayout10.add(jLabel44, BorderLayout.NORTH);
        pnlRoomFunctionsLayout10.add(txtFunctionArguments, BorderLayout.CENTER);
        pnlRoomFunctionsLayout9.setLayout(verticalFlowLayout6);
        pnlRoomFunctionsLayout9.add(btnFunctionAdd, null);
        pnlRoomFunctionsLayout9.add(btnFunctionDelete, null);
        pnlRoomFunctionsLayout8.setLayout(borderLayout67);
        pnlRoomFunctionsLayout8.add(pnlRoomFunctionsLayout10, BorderLayout.NORTH);
        pnlRoomFunctionsLayout8.add(jLabel43, BorderLayout.SOUTH);
        pnlRoomFunctionsLayout7.setLayout(borderLayout69);
        pnlRoomFunctionsLayout7.add(jLabel45, BorderLayout.NORTH);
        pnlRoomFunctionsLayout7.add(txtFunctionName, BorderLayout.SOUTH);
        pnlRoomFunctionsLayout6.setLayout(borderLayout70);
        pnlRoomFunctionsLayout6.add(jLabel46, BorderLayout.NORTH);
        pnlRoomFunctionsLayout6.add(txtFunctionReturnType, BorderLayout.CENTER);
        jScrollPane13.getViewport().add(txtFunctionBody, null);
        pnlRoomFunctionsLayout5.setLayout(borderLayout66);
        pnlRoomFunctionsLayout5.add(jScrollPane13, BorderLayout.CENTER);
        pnlRoomFunctionsLayout5.add(pnlRoomFunctionsLayout8, BorderLayout.NORTH);
        pnlRoomFunctionsLayout5.add(pnlRoomFunctionsLayout9, BorderLayout.EAST);
        pnlRoomFunctionsLayout4.setLayout(borderLayout65);
        pnlRoomFunctionsLayout4.add(pnlRoomFunctionsLayout6, BorderLayout.NORTH);
        pnlRoomFunctionsLayout4.add(pnlRoomFunctionsLayout7, BorderLayout.SOUTH);
        pnlRoomFunctionsLayout2.setBorder(borderInset5);
        pnlRoomFunctionsLayout2.setLayout(borderLayout64);
        pnlRoomFunctionsLayout2.add(pnlRoomFunctionsLayout4, BorderLayout.NORTH);
        pnlRoomFunctionsLayout2.add(pnlRoomFunctionsLayout5, BorderLayout.CENTER);
        jScrollPane12.getViewport().add(lstFunctions, null);
        pnlRoomFunctionsLayout1.setBorder(borderInset5);
        pnlRoomFunctionsLayout1.setLayout(borderLayout63);
        pnlRoomFunctionsLayout1.add(jLabel42, BorderLayout.NORTH);
        pnlRoomFunctionsLayout1.add(jScrollPane12, BorderLayout.CENTER);
        pnlRoomFunctions.setLayout(borderLayout62);
        pnlRoomFunctions.add(pnlRoomFunctionsLayout1, BorderLayout.WEST);
        pnlRoomFunctions.add(pnlRoomFunctionsLayout2, BorderLayout.CENTER);
        // Advanced
        pnlRoomAdvancedLayout3.setLayout(borderLayout44);
        pnlRoomAdvancedLayout3.add(jLabel25, BorderLayout.NORTH);
        pnlRoomAdvancedLayout3.add(txtBoundary, BorderLayout.SOUTH);
        jScrollPane9.getViewport().add(txtRoomCode, null);
        pnlRoomAdvancedLayout4.setBorder(borderInset5);
        pnlRoomAdvancedLayout4.setLayout(borderLayout50);
        pnlRoomAdvancedLayout4.add(jScrollPane9, BorderLayout.CENTER);
        pnlRoomAdvancedLayout4.add(jLabel29, BorderLayout.NORTH);
        pnlRoomAdvancedLayout2.setBorder(borderInset5);
        pnlRoomAdvancedLayout2.setLayout(borderLayout45);
        pnlRoomAdvancedLayout2.add(pnlRoomAdvancedLayout4, null);
        pnlRoomAdvancedLayout1.setBorder(borderInset5);
        pnlRoomAdvancedLayout1.setLayout(verticalFlowLayout5);
        pnlRoomAdvancedLayout1.add(pnlRoomAdvancedLayout3, null);
        pnlRoomAdv.setLayout(borderLayout46);
        pnlRoomAdv.add(pnlRoomAdvancedLayout1, BorderLayout.NORTH);
        pnlRoomAdv.add(pnlRoomAdvancedLayout2, BorderLayout.CENTER);
        // EleMapper
        pnlRoomEleMapperLayout1.add(jLabel24, null);
        pnlRoomEleMapperLayout1.add(btnRoomColour, null);
        pnlRoomEleMapper.setLayout(borderLayout49);
        pnlRoomEleMapper.add(pnlRoomEleMapperLayout1, BorderLayout.WEST);

        // Add To Tabs
        setBorder(borderInset5);
        add(pnlRoomBasics, "Basics");
        add(pnlRoomItems, "Items");
        add(pnlRoomMisc, "Miscellaneous");
        add(pnlRoomSenses, "Senses");
        add(pnlRoomObjects, "Loadable");
        add(pnlRoomGuards, "Guards");
        add(pnlRoomFunctions, "Functions");
        add(pnlRoomAdv, "Advanced");
        add(pnlRoomEleMapper, "EleMapper");

    }

    void initialise() {
        lstTerrains.setListData(Room.TERRAINS);
    }

    //-------------------------------------------------
    // Button Functions
    //-------------------------------------------------

    // Item buttons
    void btnItemAdd_actionPerformed(ActionEvent e) {
        StringTokenizer st = new StringTokenizer(txtItemNames.getText(), ",");
        ArrayList<String> itemNames = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            itemNames.add(st.nextToken().trim());
        }

        eleFrame.getRoomHelper().addItem(itemNames, txtItemDescription.getText().trim());

        updateItemList();
        txtItemDescription.setText("");
        txtItemNames.setText("");
    }

    void btnItemDelete_actionPerformed(ActionEvent e) {
        int index = lstItems.getSelectedIndex();

        eleFrame.getRoomHelper().removeItem(index);

        updateItemList();
        txtItemDescription.setText("");
        txtItemNames.setText("");
    }

    // Sense buttons
    void btnSenseAdd_actionPerformed(ActionEvent e) {
        addSense(findSense());
    }

    void btnSenseDelete_actionPerformed(ActionEvent e) {
        int index = lstSenses.getSelectedIndex();

        eleFrame.getRoomHelper().removeSense(index, findSense());

        updateSenseList(findSense());
        txtSenseName.setText("");
        txtSenseDescription.setText("");
    }

    // Object buttons
    void btnObjectAdd_actionPerformed(ActionEvent e) {
        eleFrame.getRoomHelper().addObject(txtObjectFileName.getText(),
                optObjectLoadPresent.isSelected(),
                optObjectLoadTrack.isSelected(),
                optObjectLoadUnique.isSelected(),
                optObjectTypeMon.isSelected(),
                txtObjectMessage.getText(), null);

        updateObjectLists();
        txtObjectFileName.setText("");
        txtObjectMessage.setText("");
    }

    void btnObjectDelete_actionPerformed(ActionEvent e) {
        eleFrame.getRoomHelper().removeObject((LoadedObject) lstObjects.getSelectedValue());

        updateObjectLists();
        txtObjectFileName.setText("");
        txtObjectMessage.setText("");
    }

    // Guard buttons
    void btnGuardAdd_actionPerformed(ActionEvent e) {
        // todo JPC
    }

    void btnGuardDelete_actionPerformed(ActionEvent e) {
        //todo JPC
    }

    // Function buttons
    void btnFunctionAdd_actionPerformed(ActionEvent e) {
        eleFrame.getRoomHelper().addFunction(txtFunctionName.getText(),
                txtFunctionReturnType.getText(),
                txtFunctionArguments.getText(),
                txtFunctionBody.getText());

        updateFunctionLists();
        txtFunctionReturnType.setText("");
        txtFunctionName.setText("");
        txtFunctionArguments.setText("");
        txtFunctionBody.setText("");
    }

    void btnFunctionDelete_actionPerformed(ActionEvent e) {
        eleFrame.getRoomHelper().removeFunction((Function) lstFunctions.getSelectedValue());

        updateFunctionLists();
        txtFunctionReturnType.setText("");
        txtFunctionName.setText("");
        txtFunctionArguments.setText("");
        txtFunctionBody.setText("");
    }

    // Elemapper tab button
    void btnRoomColour_actionPerformed(JButton btnRoomColour, JPanel pnlMap, ActionEvent e) {
        Color c = JColorChooser.showDialog(
            eleFrame,
            "Choose a colour for this room",
            btnRoomColour.getBackground()
        );
        if (c != null) {
            btnRoomColour.setBackground(c);
            eleFrame.getRoomHelper().setColour(c);
            pnlMap.repaint();
        }
    }

    //-------------------------------------------------
    // Option/Checkbox functions
    //-------------------------------------------------

    void optIndoors_actionPerformed(ActionEvent e) {
        lstTerrains.setSelectedIndices(new int[] {-1});
        lstTerrains.setBackground(EleConstants.DISABLED_COLOUR);
        lstTerrains.setEnabled(false);
    }

    void optOutdoors_actionPerformed(ActionEvent e) {
        lstTerrains.setBackground(EleConstants.ENABLED_COLOUR);
        lstTerrains.setEnabled(true);
        lstTerrains.setSelectedIndex(0);
    }

    void optSmells_itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            saveSenses(eleFrame.getRoomHelper().getSelectedRoom().getSmells());
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
            if (senseSelected != optSmells) {
                loadSenses(eleFrame.getRoomHelper().getSelectedRoom().getSmells());
                senseSelected = optSmells;
            }
        }
    }

    void optSounds_itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            saveSenses(eleFrame.getRoomHelper().getSelectedRoom().getSounds());
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
            if (senseSelected != optSounds) {
                loadSenses(eleFrame.getRoomHelper().getSelectedRoom().getSounds());
                senseSelected = optSounds;
            }
        }
    }

    //-------------------------------------------------
    // Other Controls functions
    //-------------------------------------------------

    void lstItems_valueChanged(ListSelectionEvent e) {
        int index = lstItems.getSelectedIndex();
        if (index >= 0) {
            if (eleFrame.getRoomHelper().hasSingleRoomSelected()) {
                txtItemDescription.setText(eleFrame.getRoomHelper().getSelectedRoom().getItem(index).getDescription());
                txtItemNames.setText(eleFrame.getRoomHelper().getSelectedRoom().getItem(index).getNames());
            }
        }
    }

    void lstSenses_valueChanged(ListSelectionEvent e) {
        EleHashtable senses;
        int index = lstSenses.getSelectedIndex();
        if (index >= 0) {
            senses = findSense();
            if (senses != null) {
                txtSenseName.setText(senses.getKey(index));
                txtSenseDescription.setText(senses.getValue(index));
            }
        }
    }

    void lstObjects_valueChanged(ListSelectionEvent e) {
        LoadedObject loadedObject = (LoadedObject) lstObjects.getSelectedValue();
        int tmp;

        if (loadedObject != null) {
            txtObjectFileName.setText(loadedObject.getFileName());
            txtObjectMessage.setText(loadedObject.getMessage());

            tmp = loadedObject.getObjectType();
            if (tmp == LoadedObject.OBJECT_TYPE_MON) {
                optObjectTypeMon.setSelected(true);
            } else {
                optObjectTypeObj.setSelected(true);
            }

            tmp = loadedObject.getLoadType();
            if (tmp == LoadedObject.LOAD_TYPE_PRESENT) {
                optObjectLoadPresent.setSelected(true);
            } else if (tmp == LoadedObject.LOAD_TYPE_TRACK) {
                optObjectLoadTrack.setSelected(true);
            } else {
                optObjectLoadUnique.setSelected(true);
            }
        }
    }

    void lstFunctions_valueChanged(ListSelectionEvent e) {
        Function function = (Function) lstFunctions.getSelectedValue();

        if (function != null) {
            txtFunctionReturnType.setText(function.getReturnType());
            txtFunctionName.setText(function.getName());
            txtFunctionArguments.setText(function.getArguments());
            txtFunctionBody.setText(function.getBody());
        }
    }

    //-------------------------------------------------
    // Package local functions
    //-------------------------------------------------

    void saveCurrentRoomSettings(JPanel pnlProperties, boolean viewProperties) {
        saveCurrentRoomSettings(eleFrame.getRoomHelper().getSelectedRoom(), pnlProperties, viewProperties);
    }

    void saveCurrentRoomSettings(Room room, JPanel pnlProperties, boolean viewProperties) {
        int[] tmp;

        if (room != null) {
            room.setCodeDescription(txtRoomCodeDesc.getText().trim());
            room.setShortDescription(txtRoomShortDesc.getText().trim());
            room.setLongDescription(txtRoomLongDesc.getText().trim());
            room.setLongDescQuotes(cbRoomDescLongQuotes.isSelected());
            room.setRoomName(txtRoomName.getText().trim());
            room.setLight(sldrLightLevel.getValue());
            saveSenses(findSense(room));
            btnItemAdd.doClick();
            btnObjectAdd.doClick();
            btnFunctionAdd.doClick();
            room.setBoundary(txtBoundary.getText().trim());
            room.setExtraCode(txtRoomCode.getText().trim());

            room.resetTerrains();
            tmp = lstTerrains.getSelectedIndices();
            for (int i = 0; i < tmp.length && i < Room.MAX_TERRAINS; i++) {
                room.setTerrain(i, tmp[i]);
            }
            clearRoomSettings(pnlProperties, viewProperties);
        }
    }

    void clearRoomSettings(JPanel pnlProperties, boolean viewProperties) {
        pnlProperties.remove(this);
        if (viewProperties) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }
        senseSelected = null;
    }

    void updateRoomSettings(Room room, int currentTool, JPanel pnlProperties, boolean viewProperties) {
        if (room != null) {
            lblRoomNumber.setText("" + room.getRoomNumber());
            txtRoomCodeDesc.setText(room.getCodeDescription());
            txtRoomShortDesc.setText(room.getShortDescription());
            txtRoomLongDesc.setText(room.getLongDescription());
            cbRoomDescLongQuotes.setSelected(room.isLongDescQuotes());
            txtRoomName.setText(room.getRoomName());
            updateItemList();
            sldrLightLevel.setValue(room.getLight());
            if (room.isIndoors()) {
                optIndoors.doClick();
            } else {
                optOutdoors.doClick();
            }
            txtBoundary.setText(room.getBoundary());
            txtRoomCode.setText(room.getExtraCode());
            lstTerrains.setSelectedIndices(room.getTerrains());
            loadSenses(findSense(room));
            optObjectTypeMon.setSelected(true);
            optObjectLoadTrack.setSelected(true);
            updateObjectLists();
            updateFunctionLists();
            optGuardCheckDefault.setSelected(true);
            optGuardMessageDefault.setSelected(true);
            optGuardWizardDefault.setSelected(true);
            updateDirectionList();
            btnRoomColour.setBackground(room.getColour());

            if (pnlProperties.getComponentCount() == 0) {
                setSize(pnlProperties.getSize());
                if (currentTool != EleFrame.TOOL_SELECT) {
                    setSelectedIndex(DEFAULT_ROOM_PANE);
                }
                pnlProperties.add(this, BorderLayout.CENTER);
            }

            if (viewProperties) {
                pnlProperties.paint(pnlProperties.getGraphics());
            }
        }
    }

    //-------------------------------------------------
    // Private functions
    //-------------------------------------------------

    private EleHashtable findSense() {
        return eleFrame.getRoomHelper().currentSense(optSmells.isSelected(), optSounds.isSelected());
    }

    private EleHashtable findSense(Room room) {
        return RoomHelper.currentSense(optSmells.isSelected(), optSounds.isSelected(), room);
    }

    private void updateItemList() {
        lstItems.setListData(eleFrame.getRoomHelper().getSelectedRoom().getItems().toArray());
    }

    private void updateDirectionList() {
        lstGuardDirections.setListData(eleFrame.getRoomHelper().getSelectedRoom().getExitDirections());
    }

    public void updateObjectLists() {
        ArrayList loadedObjects = eleFrame.getRoomHelper().getSelectedRoom().getLoadedObjects();

        lstObjects.setListData(loadedObjects.toArray());
        // todo JPC This needs to seperate out the guards from non-guards
        lstAvailableGuardObjects.setListData(loadedObjects.toArray());
    }

    private void updateFunctionLists() {
        ArrayList functions = eleFrame.getRoomHelper().getSelectedRoom().getFunctions();
        Iterator iter;
        Object key;

        lstFunctions.setListData(functions.toArray());

        // Now update the other lists of functions
        selGuardCheckFunctions.removeAllItems();
        selGuardMessageFunctions.removeAllItems();
        selGuardWizardFunctions.removeAllItems();
        iter = functions.iterator();
        while (iter.hasNext()) {
            key = iter.next();
            selGuardCheckFunctions.addItem(key);
            selGuardMessageFunctions.addItem(key);
            selGuardWizardFunctions.addItem(key);
        }
    }

    private void updateSenseList(EleHashtable senses) {
        Vector<String> v = new Vector<String>();

        if (senses != null) {
            v = new Vector<String>(senses.keySet());
        }
        lstSenses.setListData(v);
    }

    private void saveSenses(EleHashtable senses) {
        String default1 = txtDefaultSense.getText().trim();
        String default2 = txtVisibleDefaultSense.getText().trim();

        addSense(senses);

        if (default1.length() > 0) {
            senses.put(Room.DEFAULT_SENSE, default1);
        }
        if (default2.length() > 0) {
            senses.put(Room.VISIBLE_DEFAULT_SENSE, default2);
        }
    }

    private void loadSenses(EleHashtable senses) {
        Object tmp;

        if (senses != null) {
            tmp = senses.get(Room.DEFAULT_SENSE);
            if (tmp == null) tmp = "";
            txtDefaultSense.setText(tmp.toString());

            tmp = senses.get(Room.VISIBLE_DEFAULT_SENSE);
            if (tmp == null) tmp = "";
            txtVisibleDefaultSense.setText(tmp.toString());

            senses.remove(Room.VISIBLE_DEFAULT_SENSE);
            senses.remove(Room.DEFAULT_SENSE);

            updateSenseList(senses);
        }
    }

    private void addSense(EleHashtable senses) {
        if (senses != null) {
            senses.putString(txtSenseName.getText().trim(), txtSenseDescription.getText().trim());
            updateSenseList(senses);
        }

        txtSenseName.setText("");
        txtSenseDescription.setText("");
    }

}
