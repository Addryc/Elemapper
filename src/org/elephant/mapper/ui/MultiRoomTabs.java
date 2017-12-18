package org.elephant.mapper.ui;

import com.borland.jbcl.layout.VerticalFlowLayout;
import org.elephant.mapper.*;
import org.elephant.mapper.helper.RoomHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author UKJamesCook
 */
public class MultiRoomTabs extends JTabbedPane {
    private static final int DEFAULT_ROOM_PANE = 0;

    private final EleFrame eleFrame;

    private JButton btnRoomColour = new JButton();
    private ButtonGroup buttonGroupRoomOutDoors = new ButtonGroup();
    private JRadioButton optIndoors = new JRadioButton();
    private JRadioButton optOutdoors = new JRadioButton();
    private JList lstTerrains = new JList();
    private JSlider sldrLightLevel = new JSlider();
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
    private JPanel pnlRoomMisc = new JPanel();
    private JPanel pnlRoomMiscLayout1 = new JPanel();
    private JPanel pnlRoomMiscLayout2 = new JPanel();
    private JPanel pnlRoomMiscLayout3 = new JPanel();
    private JPanel pnlRoomMiscLayout4 = new JPanel();
    private JPanel pnlRoomMiscLayout5 = new JPanel();

    private JPanel pnlRoomEleMapper = new JPanel();
    private JPanel pnlRoomEleMapperLayout1 = new JPanel();
    private BorderLayout borderLayout21 = new BorderLayout();
    private BorderLayout borderLayout22 = new BorderLayout();
    private BorderLayout borderLayout23 = new BorderLayout();
    private BorderLayout borderLayout24 = new BorderLayout();
    private BorderLayout borderLayout25 = new BorderLayout();
    private BorderLayout borderLayout26 = new BorderLayout();

    private BorderLayout borderLayout49 = new BorderLayout();
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


    public MultiRoomTabs(EleFrame eleFrame, JPanel pnlMap) {
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
        btnRoomColour.setPreferredSize(new Dimension(40, 40));
        btnRoomColour.setFocusPainted(false);
        btnRoomColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRoomColour_actionPerformed(btnRoomColour, pnlMap, e);
            }
        });

        // Other controls
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
        // Misc
        buttonGroupRoomOutDoors.add(optIndoors);
        buttonGroupRoomOutDoors.add(optOutdoors);
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
        // Misc
        pnlRoomMiscLayout3.setLayout(borderLayout21);
        pnlRoomMiscLayout3.add(optIndoors, BorderLayout.WEST);
        pnlRoomMiscLayout3.add(optOutdoors, BorderLayout.EAST);
        pnlRoomMiscLayout3.add(jLabel13, BorderLayout.NORTH);

        lstTerrains.setListData(Room.TERRAINS);
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

        // EleMapper
        pnlRoomEleMapperLayout1.add(jLabel24, null);
        pnlRoomEleMapperLayout1.add(btnRoomColour, null);
        pnlRoomEleMapper.setLayout(borderLayout49);
        pnlRoomEleMapper.add(pnlRoomEleMapperLayout1, BorderLayout.WEST);

        // Add To Tabs
        setBorder(borderInset5);
        add(pnlRoomMisc, "Miscellaneous");
        add(pnlRoomEleMapper, "EleMapper");

    }

    //-------------------------------------------------
    // Button Functions
    //-------------------------------------------------

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

    //-------------------------------------------------
    // Other Controls functions
    //-------------------------------------------------
    //-------------------------------------------------
    // Package local functions
    //-------------------------------------------------

    void saveCurrentRoomSettings(JPanel pnlProperties, boolean viewProperties) {
        int[] tmp;


        if (eleFrame.getRoomHelper().hasMultipleRoomSelected()) {
            for(Room room: eleFrame.getRoomHelper().getSelectedRooms()) {
                room.setLight(sldrLightLevel.getValue());
                room.resetTerrains();
                tmp = lstTerrains.getSelectedIndices();
                for (int i = 0; i < tmp.length && i < Room.MAX_TERRAINS; i++) {
                    room.setTerrain(i, tmp[i]);
                }
            }
            clearRoomSettings(pnlProperties, viewProperties);
        }
    }

    void clearRoomSettings(JPanel pnlProperties, boolean viewProperties) {
        pnlProperties.remove(this);
        if (viewProperties) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }
    }

    void drawTabs(int currentTool, JPanel pnlProperties, boolean viewProperties) {
        for(Room room: eleFrame.getRoomHelper().getSelectedRooms()) {
            if (room.isIndoors()) {
                optIndoors.doClick();
            } else {
                optOutdoors.doClick();
            }
            lstTerrains.setSelectedIndices(room.getTerrains());
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

}
