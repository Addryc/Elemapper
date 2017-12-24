package org.elephant.mapper.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.*;

import org.elephant.mapper.*;
import org.elephant.mapper.helper.ExitHelper;
import org.elephant.mapper.helper.RoomHelper;
import org.elephant.mapper.resources.ResourceLoader;

/**
 * The graphical part of the Mapper, this class also contains a lot
 * of the logic for creating a map.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleFrame extends JFrame {
    /* todo Stuff still to do...
     * 1) add_simple_verb (/wizards/ornan/examples/simple_verb)
     * 2) add_guard
     * 3) auto generate exits to adjacent rooms
     * 4) MonsterViewer
     */
    static final int TOOL_SELECT = 1;
    static final int TOOL_DELETE = 2;
    static final int TOOL_ADD_ROOM = 3;
    static final int TOOL_ADD_EXIT = 4;
    static final int TOOL_ADD_MON = 5;
    private static final int NUMBER_OF_ROOMS_ON_MAP = 300;
    private static final int ZOOM_LEVEL = 2;
    private static final String MAPS = "maps/";
    private static final String IMAGES = "images/";

    private String _eleImage = IMAGES + "Ele.gif";
    private String _levelText = "Level ";
    private int _currentTool;
    private JButton _currentButton;
    private EleMap _eleMap;
    private EleMappableCollection<EleMappable> _stuffToMove;
    private RoomHelper roomHelper = new RoomHelper();
    private ExitHelper exitHelper = new ExitHelper();
    private Graphics _mapGraphics;
    private Point _exitStartPoint;
    private boolean _makingExit;
    private boolean _pasting;
    private boolean _movingStuff;
    private File _currentDir;
    private File _currentFile;
    private Dimension _size = new Dimension();

    // Possible Look & Feels
    private String _mac      = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
    private String _metal    = "javax.swing.plaf.metal.MetalLookAndFeel";
    private String _motif    = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private String _windows  = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    // The current Look & Feel
    private String _currentLookAndFeel = _metal;

    // My components
    private Border _borderButtonNormal;
    private Border _borderButtonRaised;
    private Border _borderButtonLowered;
    private ImageIcon _image1;
    private ImageIcon _image2;
    private ImageIcon _image3;
    private ImageIcon _image4;
    private ImageIcon _image5;
    private ImageIcon _image6;
    private ImageIcon _image7;
    private ImageIcon _image8;
    private ImageIcon _image9;

    // Generated Components
    private JPanel pnlContent;
    private JPanel pnlMap = new JPanel();
    private RoomTabs roomTabs;
    private MultiRoomTabs multiRoomTabs;
    private ExitTabs exitTabs;
    private JPanel pnlStatusLayout = new JPanel();
    private JPanel pnlPropertiesLayout = new JPanel();
    private JPanel pnlProperties = new JPanel();
    private JButton btnSelect = new JButton();
    private JButton btnAddRoom = new JButton();
    private JButton btnAddMon = new JButton();
    private JButton btnAddExit = new JButton();
    private JButton btnDelete = new JButton();
    private JButton btnZoomIn = new JButton();
    private JButton btnZoomOut = new JButton();
    private JButton btnLevelUp = new JButton();
    private JButton btnLevelDown = new JButton();
    private JButton btnHelp = new JButton();
    private BorderLayout borderLayoutStatus = new BorderLayout();
    private BorderLayout borderLayoutProperties = new BorderLayout();
    private BorderLayout borderLayout7 = new BorderLayout();
    private BorderLayout borderLayout8 = new BorderLayout();
    private JLabel statusBar = new JLabel();
    private JLabel statusBarLevel = new JLabel();
    private ButtonGroup buttonGrouplafMenu = new ButtonGroup();
    private JScrollPane pnlScrollMapLayout = new JScrollPane();

    // Toolbars/menus
    private JToolBar toolBar1 = new JToolBar();
    private JSeparator separator1 = new JSeparator();
    private JSeparator separator2 = new JSeparator();
    private JSeparator separator3 = new JSeparator();
    private JMenuBar myMenuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileNew = new JMenuItem();
    private JMenuItem menuFileOpen = new JMenuItem();
    private JMenuItem menuFileSave = new JMenuItem();
    private JMenuItem menuFileSaveAs = new JMenuItem();
    private JMenuItem menuFileExport = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuEdit = new JMenu();
    private JMenuItem menuEditCopy = new JMenuItem();
    private JMenuItem menuEditPaste = new JMenuItem();
    private JMenu menuActions = new JMenu();
    private JMenuItem menuActionSetPath = new JMenuItem();
    private JMenu menuTools = new JMenu();
    private JMenuItem menuToolSelect = new JMenuItem();
    private JMenuItem menuToolDelete = new JMenuItem();
    private JMenuItem menuToolAddRoom = new JMenuItem();
    private JMenuItem menuToolAddExit = new JMenuItem();
    private JMenuItem menuToolAddMon = new JMenuItem();
    private JMenuItem menuToolOptions = new JMenuItem();
    private JMenu menuView = new JMenu();
    private JCheckBoxMenuItem menuViewLower = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem menuViewUpper = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem menuViewProperties = new JCheckBoxMenuItem();
    private JMenu menuLaF = new JMenu();
    private JRadioButtonMenuItem menuLaFMac = new JRadioButtonMenuItem();
    private JRadioButtonMenuItem menuLaFMotif = new JRadioButtonMenuItem();
    private JRadioButtonMenuItem menuLaFWindows = new JRadioButtonMenuItem();
    private JRadioButtonMenuItem menuLaFJava = new JRadioButtonMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenu menuToolLevelExitMenu = new JMenu();
    private JMenuItem menuLevelAddUp = new JMenuItem();
    private JMenuItem menuLevelDeleteUp = new JMenuItem();
    private JMenuItem menuLevelSelectUp = new JMenuItem();
    private JMenuItem menuLevelAddDown = new JMenuItem();
    private JMenuItem menuLevelDeleteDown = new JMenuItem();
    private JMenuItem menuLevelSelectDown = new JMenuItem();
    //not sorted


    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);


        try {
            jbInit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------
    // Accessors
    //----------------------------------------------------------

    public RoomHelper getRoomHelper() {
        return roomHelper;
    }

    public RoomTabs getRoomTabs() {
        return roomTabs;
    }

    //-------------------------------------------------
    // Auto built functions
    //-------------------------------------------------

    /** component initialization. */
    private void jbInit() throws Exception  {
        // Setup variables
        _image1 = new ImageIcon(ResourceLoader.load(IMAGES + "Select.gif"));
        _image2 = new ImageIcon(ResourceLoader.load(IMAGES + "AddRoom.gif"));
        _image3 = new ImageIcon(ResourceLoader.load(IMAGES + "AddExit.gif"));
        _image4 = new ImageIcon(ResourceLoader.load(IMAGES + "Delete.gif"));
        _image5 = new ImageIcon(ResourceLoader.load(IMAGES + "ZoomIn.gif"));
        _image6 = new ImageIcon(ResourceLoader.load(IMAGES + "ZoomOut.gif"));
        _image7 = new ImageIcon(ResourceLoader.load(IMAGES + "Up.gif"));
        _image8 = new ImageIcon(ResourceLoader.load(IMAGES + "Down.gif"));
        _image9 = new ImageIcon(ResourceLoader.load(IMAGES + "Help.gif"));
        _currentDir = new File(MAPS);
        _borderButtonNormal = BorderFactory.createEmptyBorder();
        _borderButtonRaised = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray,Color.white,Color.gray,Color.lightGray);
        _borderButtonLowered = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.lightGray,Color.white,Color.gray,Color.lightGray);
        pnlContent = (JPanel) getContentPane();
        Dimension buttonSize = new Dimension(22, 22);

        // Set properties on this.
        setIconImage(Toolkit.getDefaultToolkit().createImage(ResourceLoader.load(_eleImage))); //todo Comment out for designer
        setSize(new Dimension(700, 618));
        updateTitle();

        // Map stuff
        pnlMap = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                _eleMap.paint(g, 0);
                _mapGraphics = g;
            }
        };
        pnlMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        pnlMap.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                map_mouseDragged(e);
            }
        });
        pnlMap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                map_mouseClicked(e);
            }
            public void mousePressed(MouseEvent e) {
                map_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                map_mouseReleased(e);
            }
        });

        // Create the tabs
        exitTabs = new ExitTabs(this);
        roomTabs = new RoomTabs(this, pnlMap);
        multiRoomTabs = new MultiRoomTabs(this, pnlMap);

        // Button stuff
        btnSelect.setBorder(_borderButtonNormal);
        btnSelect.setMaximumSize(buttonSize);
        btnSelect.setMinimumSize(buttonSize);
        btnSelect.setPreferredSize(buttonSize);
        btnSelect.setToolTipText("Select");
        btnSelect.setFocusPainted(false);
        btnSelect.setIcon(_image1);
        btnSelect.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnSelect, TOOL_SELECT);
            }
        });
        btnAddRoom.setBorder(_borderButtonNormal);
        btnAddRoom.setMaximumSize(buttonSize);
        btnAddRoom.setMinimumSize(buttonSize);
        btnAddRoom.setPreferredSize(buttonSize);
        btnAddRoom.setToolTipText("Add Room");
        btnAddRoom.setFocusPainted(false);
        btnAddRoom.setIcon(_image2);
        btnAddRoom.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnAddRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnAddRoom, TOOL_ADD_ROOM);
            }
        });
        btnAddExit.setBorder(_borderButtonNormal);
        btnAddExit.setMaximumSize(buttonSize);
        btnAddExit.setMinimumSize(buttonSize);
        btnAddExit.setPreferredSize(buttonSize);
        btnAddExit.setToolTipText("Add Exit");
        btnAddExit.setFocusPainted(false);
        btnAddExit.setIcon(_image3);
        btnAddExit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnAddExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnAddExit, TOOL_ADD_EXIT);
            }
        });

        btnAddMon.setBorder(_borderButtonNormal);
        btnAddMon.setMaximumSize(buttonSize);
        btnAddMon.setMinimumSize(buttonSize);
        btnAddMon.setPreferredSize(buttonSize);
        btnAddMon.setToolTipText("Monster Manager");
        btnAddMon.setFocusPainted(false);
        btnAddMon.setIcon(_image7);
        btnAddMon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnAddMon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnAddMon, TOOL_ADD_MON);
            }
        });

        btnDelete.setBorder(_borderButtonNormal);
        btnDelete.setMaximumSize(buttonSize);
        btnDelete.setMinimumSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);
        btnDelete.setToolTipText("Delete");
        btnDelete.setFocusPainted(false);
        btnDelete.setIcon(_image4);
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnDelete, TOOL_DELETE);
            }
        });
        btnZoomIn.setBorder(_borderButtonNormal);
        btnZoomIn.setMaximumSize(buttonSize);
        btnZoomIn.setMinimumSize(buttonSize);
        btnZoomIn.setPreferredSize(buttonSize);
        btnZoomIn.setToolTipText("Zoom In");
        btnZoomIn.setFocusPainted(false);
        btnZoomIn.setIcon(_image5);
        btnZoomIn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnZoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnZoomIn_actionPerformed(e);
            }
        });
        btnZoomOut.setBorder(_borderButtonNormal);
        btnZoomOut.setMaximumSize(buttonSize);
        btnZoomOut.setMinimumSize(buttonSize);
        btnZoomOut.setPreferredSize(buttonSize);
        btnZoomOut.setToolTipText("Zoom Out");
        btnZoomOut.setFocusPainted(false);
        btnZoomOut.setIcon(_image6);
        btnZoomOut.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnZoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnZoomOut_actionPerformed(e);
            }
        });
        btnLevelUp.setBorder(_borderButtonNormal);
        btnLevelUp.setMaximumSize(buttonSize);
        btnLevelUp.setMinimumSize(buttonSize);
        btnLevelUp.setPreferredSize(buttonSize);
        btnLevelUp.setToolTipText("Next Level Up");
        btnLevelUp.setFocusPainted(false);
        btnLevelUp.setIcon(_image7);
        btnLevelUp.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnLevelUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLevelUp_actionPerformed(e);
            }
        });
        btnLevelDown.setBorder(_borderButtonNormal);
        btnLevelDown.setMaximumSize(buttonSize);
        btnLevelDown.setMinimumSize(buttonSize);
        btnLevelDown.setPreferredSize(buttonSize);
        btnLevelDown.setToolTipText("Next Level Down");
        btnLevelDown.setFocusPainted(false);
        btnLevelDown.setIcon(_image8);
        btnLevelDown.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnLevelDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLevelDown_actionPerformed(e);
            }
        });
        btnHelp.setBorder(_borderButtonNormal);
        btnHelp.setMaximumSize(buttonSize);
        btnHelp.setMinimumSize(buttonSize);
        btnHelp.setPreferredSize(buttonSize);
        btnHelp.setToolTipText("Help");
        btnHelp.setFocusPainted(false);
        btnHelp.setIcon(_image9);
        btnHelp.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });

        // Menus
        menuFile.setText("File");
        menuFile.setMnemonic('F');
        menuFile.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                menuFile_menuSelected(e);
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuFileNew.setText("New");
        menuFileNew.setMnemonic('N');
        menuFileNew.setAccelerator(KeyStroke.getKeyStroke(78, KeyEvent.CTRL_MASK, true));
        menuFileNew.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileNew_actionPerformed(e);
            }
        });
        menuFileOpen.setText("Open...");
        menuFileOpen.setMnemonic('O');
        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(79, KeyEvent.CTRL_MASK, true));
        menuFileOpen.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileOpen_actionPerformed(e);
            }
        });
        menuFileSave.setText("Save");
        menuFileSave.setMnemonic('S');
        menuFileSave.setAccelerator(KeyStroke.getKeyStroke(83, KeyEvent.CTRL_MASK, true));
        menuFileSave.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileSave_actionPerformed(e);
            }
        });
        menuFileSaveAs.setText("Save As...");
        menuFileSaveAs.setMnemonic('V');
        menuFileSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFileSaveAs_actionPerformed(e);
            }
        });
        menuFileExport.setText("Export to LPC...");
        menuFileExport.setMnemonic('E');
        menuFileExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFileExport_actionPerformed(e);
            }
        });
        menuFileExit.setText("Exit");
        menuFileExit.setMnemonic('X');
        menuFileExit.setAccelerator(KeyStroke.getKeyStroke(115, KeyEvent.ALT_MASK, true));
        menuFileExit.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileExit_actionPerformed(e);
            }
        });
        menuEdit.setText("Edit");
        menuEdit.setMnemonic('E');
        menuEditCopy.setText("Copy");
        menuEditCopy.setMnemonic('C');
        menuEditCopy.setAccelerator(KeyStroke.getKeyStroke(67, KeyEvent.ALT_MASK, true));
        menuEditCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuEditCopy_actionPerformed(e);
            }
        });
        menuEditPaste.setText("Paste");
        menuEditPaste.setMnemonic('P');
        menuEditPaste.setAccelerator(KeyStroke.getKeyStroke(86, KeyEvent.ALT_MASK, true));
        menuEditPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuEditPaste_actionPerformed(e);
            }
        });
        menuActions.setText("Actions");
        menuActions.setMnemonic('A');
        menuActionSetPath.setText("Set Map\'s Path...");
        menuActionSetPath.setMnemonic('S');
        menuActionSetPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuActionSetPath_actionPerformed(e);
            }
        });
        menuTools.setText("Tools");
        menuTools.setMnemonic('T');
        menuTools.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                menuTools_menuSelected(e);
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuToolSelect.setText("Select");
        menuToolSelect.setMnemonic('S');
        menuToolSelect.setAccelerator(KeyStroke.getKeyStroke(83, KeyEvent.ALT_MASK, true));
        menuToolSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolSelect_actionPerformed(e);
            }
        });
        menuToolDelete.setText("Delete");
        menuToolDelete.setMnemonic('D');
        menuToolDelete.setAccelerator(KeyStroke.getKeyStroke(68, KeyEvent.ALT_MASK, false));
        menuToolDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolDelete_actionPerformed(e);
            }
        });
        menuToolAddRoom.setText("Add Room");
        menuToolAddRoom.setMnemonic('R');
        menuToolAddRoom.setAccelerator(KeyStroke.getKeyStroke(82, KeyEvent.ALT_MASK, false));
        menuToolAddRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolAddRoo_actionPerformed(e);
            }
        });

        menuToolAddMon.setText("Monster Manager");
        menuToolAddMon.setMnemonic('M');
        menuToolAddMon.setAccelerator(KeyStroke.getKeyStroke(77, KeyEvent.ALT_MASK, false));
        menuToolAddMon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolAddMon_actionPerformed(e);
            }
        });
        menuToolAddExit.setText("Add Exit");
        menuToolAddExit.setMnemonic('E');
        menuToolAddExit.setAccelerator(KeyStroke.getKeyStroke(69, KeyEvent.ALT_MASK, true));
        menuToolAddExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolAddExit_actionPerformed(e);
            }
        });
        menuToolLevelExitMenu.setText("Level Exit");
        menuToolLevelExitMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                menuToolLevelExitMenu_menuSelected(e);
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuLevelAddUp.setText("Add Up Exit");
        menuLevelAddUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelAddUp_actionPerformed(e);
            }
        });
        menuLevelAddDown.setText("Add Down Exit");
        menuLevelAddDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelAddDown_actionPerformed(e);
            }
        });
        menuLevelSelectUp.setText("Select Up Exit");
        menuLevelSelectUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelSelectUp_actionPerformed(e);
            }
        });
        menuLevelSelectDown.setText("Select Down Exit");
        menuLevelSelectDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelSelectDown_actionPerformed(e);
            }
        });
        menuLevelDeleteUp.setText("Delete Up Exit");
        menuLevelDeleteUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelDeleteUp_actionPerformed(e);
            }
        });
        menuLevelDeleteDown.setText("Delete Down Exit");
        menuLevelDeleteDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelDeleteDown_actionPerformed(e);
            }
        });
        menuView.setText("View");
        menuView.setMnemonic('V');
        menuViewLower.setText("Lower Level");
        menuViewLower.setMnemonic('L');
        menuViewLower.setSelected(true);
        menuViewLower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuViewLower_actionPerformed(e);
            }
        });
        menuViewUpper.setText("Upper Level");
        menuViewUpper.setMnemonic('U');
        menuViewUpper.setSelected(true);
        menuViewUpper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuViewUpper_actionPerformed(e);
            }
        });
        menuViewProperties.setText("Properties");
        menuViewProperties.setMnemonic('P');
        menuViewProperties.setSelected(true);
        menuViewProperties.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuViewProperties_actionPerformed(e);
            }
        });
        menuLaF.setText("Look & Feel");
        menuLaF.setMnemonic('L');
        menuLaFJava.setText("Java");
        menuLaFJava.setMnemonic('J');
        menuSetup(menuLaFJava, _metal);
        menuLaFMac.setText("Macintosh");
        menuLaFMac.setMnemonic('M');
        menuSetup(menuLaFMac, _mac);
        menuLaFMotif.setText("Motif");
        menuLaFMotif.setMnemonic('F');
        menuSetup(menuLaFMotif, _motif);
        menuLaFWindows.setText("Windows");
        menuLaFWindows.setMnemonic('W');
        menuSetup(menuLaFWindows, _windows);
        menuHelp.setText("Help");
        menuHelp.setMnemonic('H');
        menuHelpAbout.setText("About");
        menuHelpAbout.setMnemonic('A');
        menuHelpAbout.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuHelpAbout_actionPerformed(e);
            }
        });

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                popupMenu_popupMenuCanceled(e);
            }
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }
        });
        addLevelExitItems(popupMenu);

        menuToolOptions.setMnemonic('O');
        menuToolOptions.setText("Options...");
        menuToolOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolOptions_actionPerformed(e);
            }
        });
        // --------------------------------------------------------------------------------
        // remove below, this is where Jbuilder likes to put the stuff from the designer...
        // --------------------------------------------------------------------------------

        menuFile.add(menuFileNew);
        menuFile.add(menuFileOpen);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileSaveAs);
        menuFile.addSeparator();
        menuFile.add(menuFileExport);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuEdit.add(menuEditCopy);
        menuEdit.add(menuEditPaste);
        menuActions.add(menuActionSetPath);
        menuTools.add(menuToolSelect);
        menuTools.add(menuToolDelete);
        menuTools.addSeparator();
        menuTools.add(menuToolAddRoom);
        menuTools.add(menuToolAddExit);
        menuTools.add(menuToolAddMon);
        menuTools.add(menuToolLevelExitMenu);
        menuTools.addSeparator();
        menuTools.add(menuToolOptions);
        menuView.add(menuViewUpper);
        menuView.add(menuViewLower);
        menuView.addSeparator();
        menuView.add(menuViewProperties);
        menuLaF.add(menuLaFJava);
        menuLaF.add(menuLaFMac);
        menuLaF.add(menuLaFMotif);
        menuLaF.add(menuLaFWindows);
        menuHelp.add(menuHelpAbout);
        myMenuBar.add(menuFile);
        myMenuBar.add(menuEdit);
        myMenuBar.add(menuActions);
        myMenuBar.add(menuTools);
        myMenuBar.add(menuView);
        myMenuBar.add(menuLaF);
        myMenuBar.add(menuHelp);
        setJMenuBar(myMenuBar);

        // Toolbars
        toolBar1.add(btnSelect);
        toolBar1.add(btnAddRoom);
        toolBar1.add(btnAddExit);
        toolBar1.add(btnDelete);
        toolBar1.add(separator1);
        toolBar1.add(btnZoomIn);
        toolBar1.add(btnZoomOut);
        toolBar1.add(separator2);
        toolBar1.add(btnLevelUp);
        toolBar1.add(btnLevelDown);
        toolBar1.add(separator3);
        toolBar1.add(btnHelp);

        // Misc
        statusBar.setText("Ready");
        toolBar1.setBorder(null);
        toolBar1.setBorderPainted(false);
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setPreferredSize(new Dimension(3, 22));
        separator1.setMaximumSize(new Dimension(3, 22));
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setPreferredSize(new Dimension(3, 22));
        separator2.setMaximumSize(new Dimension(3, 22));
        separator3.setOrientation(SwingConstants.VERTICAL);
        pnlProperties.setMinimumSize(new Dimension(500, 300));
        pnlProperties.setPreferredSize(new Dimension(640, 300));

        // Layout stuff
        // Main stuff
        pnlStatusLayout.setLayout(borderLayoutStatus);
        pnlStatusLayout.add(statusBar, BorderLayout.WEST);
        pnlStatusLayout.add(statusBarLevel, BorderLayout.EAST);

        pnlProperties.setLayout(borderLayout8);
        pnlPropertiesLayout.setLayout(borderLayoutProperties);
        pnlPropertiesLayout.add(pnlProperties, BorderLayout.CENTER);
        pnlPropertiesLayout.add(pnlStatusLayout, BorderLayout.SOUTH);

        pnlScrollMapLayout.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlScrollMapLayout.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pnlScrollMapLayout.getViewport().add(pnlMap, null);
        pnlContent.setLayout(borderLayout7);
        pnlContent.add(toolBar1, BorderLayout.NORTH);
        pnlContent.add(pnlPropertiesLayout, BorderLayout.SOUTH);
        pnlContent.add(pnlScrollMapLayout, BorderLayout.CENTER);

        // Init the variables
        initialise(null, null);
    }

    /** overridden so we can exit when window is closed. */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            menuFileExit.doClick();
        }
    }



    //-------------------------------------------------
    // Menu functions
    //-------------------------------------------------

    void menuFile_menuSelected(MenuEvent e) {
        updateCurrentRoom(null, false);
        updateCurrentExit(null, false);
    }

    void menuFileNew_actionPerformed(ActionEvent e) {
        initialise(new EleMap(), null);
        updateTitle();
    }

    void menuFileOpen_actionPerformed(ActionEvent e) {
        File file;
        FileChooser fileChooser = new FileChooser(FileChooser.OPEN, this, _currentDir);
        file = fileChooser.chooseFile();
        if (file != null) {
            _currentDir = file.getParentFile();
            DocumentHandler doc = new DocumentHandler(file);
            try {
                doc.readFromXML();
                initialise((EleMap) doc.getData(), file);
            } catch (Exception e1){
                JOptionPane.showMessageDialog(this,
                        "Error reading file (" + file.getAbsolutePath() +
                        "):\n" + e1,
                        "XML Read Error",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace(System.err);
            }
        }
        updateTitle();
    }

    void menuFileSaveAs_actionPerformed(ActionEvent e) {
        genericSave(FileChooser.SAVE_AS);
    }

    void menuFileSave_actionPerformed(ActionEvent e) {
        genericSave(FileChooser.SAVE);
    }

    void menuFileExport_actionPerformed(ActionEvent e) {
        FileChooser fileChooser;
        File directory;

        fileChooser = new FileChooser(FileChooser.DIRECTORY, this, _currentDir);
        directory = fileChooser.chooseFile();

        if (directory != null) {
            _currentDir = directory.getParentFile();
            if (_eleMap.getPath().length() < 1) {
                menuActionSetPath.doClick();
            }

            try {
                _eleMap.export(directory, _eleMap.getPath(), null, null);
                JOptionPane.showMessageDialog(this,
                    "Export complete.",
                    "Exporting",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (EleMapExportException e1) {
                JOptionPane.showMessageDialog(this,
                    "Error exporting to LPC:\n" + e1,
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
                System.err.println(e1.toString());
                Exportable ob = e1.getErrorObject();
                if (ob instanceof Room) {
                    gotoRoom((Room) ob);
                } else if (ob instanceof Exit) {
                    gotoExit((Exit) ob);
                }
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(this,
                    "IO Error whilst exporting:\n" + e2,
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
                e2.printStackTrace(System.err);
            } catch (Exception e3) {
                JOptionPane.showMessageDialog(this,
                    "Unknown Error whilst exporting:\n" + e3,
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
                e3.printStackTrace(System.err);
            }
        }
    }

    void menuFileExit_actionPerformed(ActionEvent e) {
// todo: this is a check to see if anything has changed
//        if (_haschanged) {
//            msgbox("really want to exit?");
//        }
        genericSave(FileChooser.SAVE);
        System.exit(0);
    }

    void menuEditCopy_actionPerformed(ActionEvent e) {
        if (!roomHelper.hasSingleRoomSelected()) {
            JOptionPane.showMessageDialog(this,
                "There must be a room selected in order to do a copy.",
                "Copying",
                JOptionPane.ERROR_MESSAGE);
        } else {
            roomTabs.saveCurrentRoomSettings(roomHelper.getSelectedRoom(), pnlProperties, menuViewProperties.isSelected());
            roomHelper.createCopyRoom(_eleMap.getRoomSize(), _eleMap.getRoomNumber());
            _eleMap.incrementRoomNumber();
        }
    }

    void menuEditPaste_actionPerformed(ActionEvent e) {
        if (!roomHelper.hasCopiedRoom()) {
            JOptionPane.showMessageDialog(this,
                "Copy a room first.",
                "Pasting",
                JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Click the map in the position you wish to paste the room.",
                "Pasting",
                JOptionPane.INFORMATION_MESSAGE);
            _pasting = true;
        }
    }

    void menuActionSetPath_actionPerformed(ActionEvent e) {
        String path;

        path = (String) JOptionPane.showInputDialog(this,
                            "What is the root path for this map.\n(e.g. /d/town/market)\n\n"+
                            "The path will have the additional\ndirectories (rooms/include/etc) added to it.",
                            "Enter Path",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            null,
                            _eleMap.getPath());
        if (path != null) {
            if (!path.endsWith("/")) {
                path += "/";
            }
            _eleMap.setPath(path);
        }
    }

    void menuTools_menuSelected(MenuEvent e) {
        if (!roomHelper.hasSingleRoomSelected()) {
            menuToolLevelExitMenu.setEnabled(false);
        } else {
            menuToolLevelExitMenu.setEnabled(true);
        }
    }

    void menuToolSelect_actionPerformed(ActionEvent e) {
        btnSelect.doClick();
    }

    void menuToolDelete_actionPerformed(ActionEvent e) {
        btnDelete.doClick();
    }

    void menuToolAddRoo_actionPerformed(ActionEvent e) {
        btnAddRoom.doClick();
    }

    void menuToolAddExit_actionPerformed(ActionEvent e) {
        btnAddExit.doClick();
    }

    void menuToolAddMon_actionPerformed(ActionEvent e) {
        showMonsterDialog();
    }

    void showMonsterDialog(Monster monster) {
        MonsterDialog dialog = new MonsterDialog(this, monster);
        dialog.pack();
        dialog.setVisible(true);
    }

    void showMonsterDialog(String uuid) {
        MonsterDialog dialog = new MonsterDialog(this, uuid);
        dialog.pack();
        dialog.setVisible(true);
    }

    void showMonsterDialog() {
        MonsterDialog dialog = new MonsterDialog(this);
        dialog.pack();
        dialog.setVisible(true);
    }


    void menuToolOptions_actionPerformed(ActionEvent e) {
        Options.create(this);
        pnlMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        exitTabs.pnlExitMiniMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        pnlMap.repaint();
        // todo options
    }

    void menuToolLevelExitMenu_menuSelected(MenuEvent e) {
        addLevelExitItems(menuToolLevelExitMenu);
    }

    void menuLevelAddUp_actionPerformed(ActionEvent e) {
        addVerticalExit(EleConstants.DIRECTION_UP);
    }

    void menuLevelAddDown_actionPerformed(ActionEvent e) {
        addVerticalExit(EleConstants.DIRECTION_DOWN);
    }

    void menuLevelSelectUp_actionPerformed(ActionEvent e) {
        selectVerticalExit(EleConstants.DIRECTION_UP);
    }

    void menuLevelSelectDown_actionPerformed(ActionEvent e) {
        selectVerticalExit(EleConstants.DIRECTION_DOWN);
    }

    void menuLevelDeleteUp_actionPerformed(ActionEvent e) {
        deleteVerticalExit(EleConstants.DIRECTION_UP);
    }

    void menuLevelDeleteDown_actionPerformed(ActionEvent e) {
        deleteVerticalExit(EleConstants.DIRECTION_DOWN);
    }

    void menuViewLower_actionPerformed(ActionEvent e) {
        _eleMap.setShowLower(menuViewLower.getState());
        pnlMap.repaint();
    }

    void menuViewUpper_actionPerformed(ActionEvent e) {
        _eleMap.setShowUpper(menuViewUpper.getState());
        pnlMap.repaint();
    }

    void menuViewProperties_actionPerformed(ActionEvent e) {
        if (menuViewProperties.getState()) {
            pnlContent.add(pnlPropertiesLayout, BorderLayout.SOUTH);
        } else {
            pnlContent.remove(pnlPropertiesLayout);
        }
        validate();
    }

    void menuHelpAbout_actionPerformed(ActionEvent e) {
        AboutBox dlg = new AboutBox(this, _eleImage);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setVisible(true);
    }

    void popupMenu_popupMenuCanceled(PopupMenuEvent e) {
        pnlMap.repaint();
    }

    //-------------------------------------------------
    // Button Functions
    //-------------------------------------------------

    // Levels
    void btnLevelUp_actionPerformed(ActionEvent e) {
        _eleMap.incrementLevel();
        statusBarLevel.setText(_levelText + _eleMap.getLevel());
        pnlMap.repaint();
    }

    void btnLevelDown_actionPerformed(ActionEvent e) {
        _eleMap.decrementLevel();
        statusBarLevel.setText(_levelText + _eleMap.getLevel());
        pnlMap.repaint();
    }

    // Zoom
    void btnZoomIn_actionPerformed(ActionEvent e) {
        if (_eleMap.getRoomSize() <= (EleMap.INITIAL_ROOM_SIZE * ZOOM_LEVEL)) {
            _eleMap.setRoomSize(_eleMap.getRoomSize() * 2);
            _eleMap.zoom(true);
            _size.setSize(_size.width*2, _size.height*2);
            pnlMap.setPreferredSize(_size);
            pnlMap.revalidate();
            pnlMap.repaint();
        }
    }

    void btnZoomOut_actionPerformed(ActionEvent e) {
        if ((_eleMap.getRoomSize() / 2) >= (EleMap.INITIAL_ROOM_SIZE / (ZOOM_LEVEL * 2))) {
            _eleMap.setRoomSize(_eleMap.getRoomSize() / 2);
            _eleMap.zoom(false);
            _size.setSize(_size.width/2, _size.height/2);
            pnlMap.setPreferredSize(_size);
            pnlMap.revalidate();
            pnlMap.repaint();
        }
    }

    //-------------------------------------------------
    // Generic Button Functions
    //-------------------------------------------------

    void button_mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getComponent();

        if (!button.isSelected()) {
            button.setBorder(_borderButtonRaised);
        }
    }

    void button_mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getComponent();

        if (!button.isSelected()) {
            button.setBorder(_borderButtonNormal);
        }
    }

    void button_mousePressed(MouseEvent e) {
        ((JButton) e.getComponent()).setBorder(_borderButtonLowered);
    }

    void button_mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getComponent();

        if (!button.isSelected()) {
            button.setBorder(_borderButtonRaised);
        }
    }

    private void button_actionPerformed(JButton button, int tool) {
        changeSelection(_currentButton);
        changeSelection(button);
        _currentButton = button;
        _currentTool = tool;
        switch (tool) {
        case TOOL_SELECT:
            statusBar.setText("Selecting");
            break;
        case TOOL_ADD_EXIT:
            statusBar.setText("Adding Exits");
            break;
        case TOOL_ADD_ROOM:
            statusBar.setText("Adding Rooms");
            break;
        case TOOL_DELETE:
            statusBar.setText("Deleting");
            break;
        default:
            statusBar.setText("Ready");
            break;
        }
    }

    private void changeSelection(JButton button) {
        if (button != null) {
            if (button.isSelected()) {
                button.setSelected(false);
                button.setBorder(_borderButtonNormal);
            } else {
                button.setSelected(true);
                button.setBorder(_borderButtonLowered);
            }
        }
    }

    //-------------------------------------------------
    // Map Functions
    //-------------------------------------------------

    void map_mousePressed(MouseEvent e) {
//        System.out.println("Mouse Press Modifiers: "+e.getModifiers());

        Point p = new Point(e.getX(), e.getY());
        switch(_currentTool) {
        case TOOL_ADD_EXIT:
            updateCurrentExit(p, false);
            updateCurrentRoom(p, true);

            if (roomHelper.hasSingleRoomSelected()) {
                _exitStartPoint = p;
                _makingExit = true;
            }
            break;
        case TOOL_SELECT:
            updateCurrentExit(p, false);
            if(!e.isShiftDown() && !e.isControlDown()) {
                updateCurrentRoom(p, true);
                if (roomHelper.hasSingleRoomSelected()) {
                    _movingStuff = true;
                    _stuffToMove = Room.getAllLinked(new EleMappableCollection<EleMappable>(EleConstants.XML_BOTH), roomHelper.getSelectedRoom());
                }
            }

            break;
        }
    }

    void map_mouseDragged(MouseEvent e) {
        Graphics2D g;
        float[] dash1 = {4.0f};
        BasicStroke bs;
        int x;
        int y;

        if (_makingExit || _movingStuff) {
            bs = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g = (Graphics2D) pnlMap.getGraphics();
            pnlMap.paint(g);
            g.setStroke(bs);
            g.setColor(EleConstants.SELECT_COLOUR);

            if (_makingExit) {
                g.drawLine(_exitStartPoint.x, _exitStartPoint.y, e.getX(), e.getY());
            } else if (_movingStuff) {
                x = e.getX() - (_eleMap.getRoomSize() / 2) - (int) roomHelper.getSelectedRoom().getX();
                y = e.getY() - (_eleMap.getRoomSize() / 2) - (int) roomHelper.getSelectedRoom().getY();

                _stuffToMove.paintOutline(g, _eleMap.getLevel(), x, y);
            }
        }
    }

    void map_mouseReleased(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        Point p2;
        EleMappable tmp;
        boolean move = true;
        Room r;
        int direction;

        // Make sure the cursor is still in the bounds of the map.
        if (pnlMap.contains(p)) {
            if (e.isPopupTrigger()) {
                if (!_pasting) {
                    updateCurrentExit(p, false);
                    updateCurrentRoom(p, true);
                    if (roomHelper.hasRoomSelected()) {
                        _makingExit = false;
                        _movingStuff = false;
                        addLevelExitItems(popupMenu);
                        popupMenu.show(pnlMap, e.getX(), e.getY());
                    }
                }
            } else {
                if (_makingExit) {
                    _makingExit = false;
                    Room roomExitLinkedFrom = roomHelper.getSelectedRoom();
                    updateCurrentRoom(p, true);
                    Room roomExitLinkedTo = roomHelper.getSelectedRoom();

                    if (roomExitLinkedTo != null) {
                        direction = roomExitLinkedFrom.adjacentRoom(roomExitLinkedTo);
                        if ((direction != 0) && (!roomExitLinkedFrom.containsExit(direction))) {
                            roomTabs.clearRoomSettings(pnlProperties, menuViewProperties.isSelected());
                            roomHelper.deSelect(_mapGraphics, _eleMap.getLevel(), _eleMap.getShowUpper(), _eleMap.getShowLower());
                            exitHelper.setExit(new Exit(roomExitLinkedFrom, roomExitLinkedTo, _eleMap.getExitNumber(), _eleMap.getLevel(), _mapGraphics));
                            _eleMap.getExits().add(exitHelper.getExit());
                            _eleMap.incrementExitNumber();
                            updateExitSettings();
                        }
                    }

                    pnlMap.repaint();
                } else if (_movingStuff) {
                    _movingStuff = false;
                    p = Room.alterForRoom(p, _eleMap.getRoomSize());

                    // Check if we are overlapping any rooms.
                    for (Object thingToMove : _stuffToMove) {
                        tmp = (EleMappable) thingToMove;
                        if (tmp instanceof Room) {
                            p2 = ((Room) tmp).getLocation();
                            p2.translate((int) (p.getX() - roomHelper.getSelectedRoom().getX()),
                                    (int) (p.getY() - roomHelper.getSelectedRoom().getY()));
                            r = (Room) _eleMap.getRooms().exists(p2, _eleMap.getLevel());
                            if (r != null && !_stuffToMove.contains(r)) {
                                move = false;
                            }
                        }
                    }

                    // If everything is ok, then do the move.
                    if (move) {
                        _stuffToMove.shift((int) (p.getY() - roomHelper.getSelectedRoom().getY()),
                                            (int) (p.getX() - roomHelper.getSelectedRoom().getX()));
                    }

                    // ...and repaint.
                    pnlMap.repaint();
                }
            }
        }

        _exitStartPoint = null;
    }

    void map_mouseClicked(MouseEvent e) {
//        System.out.println("Mouse Click Modifiers: "+e.getModifiers());
        Point p = new Point(e.getX(), e.getY());
        int retValue;
        EleMappableCollection<EleMappable> tmp;

        if (_pasting) {
            updateCurrentRoom(p, true);
            updateCurrentExit(p, false);
            if (roomHelper.hasRoomSelected()) {
                retValue = JOptionPane.showConfirmDialog(this,
                               "There is already a room there. Click ok to update that room with the details from the copied room.",
                               "Warning",
                               JOptionPane.OK_CANCEL_OPTION);
                if (retValue == JOptionPane.OK_OPTION) {
                    roomHelper.updateCurrentRoomWithCopiedRoom();
                    roomTabs.updateRoomSettings(roomHelper.getSelectedRoom(), _currentTool, pnlProperties, menuViewProperties.isSelected());
                }
            } else {
                roomHelper.makeCopiedRoomTheCurrentRoom();
                Room room = roomHelper.getSelectedRoom();
                p = Room.alterForRoom(p, _eleMap.getRoomSize());
                room.setLevel(_eleMap.getLevel());
                room.shift((int) (p.getY() - room.getY()),
                                   (int) (p.getX() - room.getX()));
                _eleMap.getRooms().add(room);
                room.select(_mapGraphics);
                roomTabs.updateRoomSettings(room, _currentTool, pnlProperties, menuViewProperties.isSelected());
            }
        } else {
            switch(_currentTool) {
                case TOOL_SELECT:
                    updateCurrentRoom(p, true, (e.isControlDown() || e.isShiftDown()) );
                    updateCurrentExit(p, !roomHelper.hasSingleRoomSelected());
                    break;
                case TOOL_DELETE:
                    updateCurrentRoom(p, true);
                    updateCurrentExit(p, !roomHelper.hasSingleRoomSelected());
                    if (roomHelper.hasRoomSelected()) {
                        for(Room room: roomHelper.getSelectedRooms()) {
                            if (room.getNumberOfExits() == 0) {
                                roomTabs.clearRoomSettings(pnlProperties, menuViewProperties.isSelected());
                                _eleMap.getRooms().remove(room);
                            } else {
                                retValue = JOptionPane.showConfirmDialog(this,
                                        "This room has exits. Deleting this room will delete everything linked to it.",
                                        "Warning",
                                        JOptionPane.OK_CANCEL_OPTION);
                                if (retValue == JOptionPane.OK_OPTION) {
                                    roomTabs.clearRoomSettings(pnlProperties, menuViewProperties.isSelected());
                                    tmp = Room.getAllLinked(new EleMappableCollection<EleMappable>(EleConstants.XML_BOTH), room);
                                    _eleMap.getRooms().removeAll(tmp);
                                    _eleMap.getExits().removeAll(tmp);
                                }
                            }
                        }
                        roomHelper.clearSelectedRooms();
                    } else if (exitHelper.hasExit()) {
                        clearExitSettings();
                        _eleMap.getExits().remove(exitHelper.getExit());
                        exitHelper.getExit().dispose();
                        exitHelper.clearExit();
                    }
                    button_actionPerformed(btnSelect, TOOL_SELECT);
                    break;
                case TOOL_ADD_ROOM:
                    p = Room.alterForRoom(p, _eleMap.getRoomSize());
                    updateCurrentExit(p, false);
                    updateCurrentRoom(p, true);
                    if (!roomHelper.hasRoomSelected()) {
                        roomHelper.addSelectedRoom(new Room(p, _eleMap.getRoomSize(), _eleMap.getRoomNumber(), _eleMap.getLevel(), _mapGraphics));
                        _eleMap.getRooms().add(roomHelper.getSelectedRoom());
                        _eleMap.incrementRoomNumber();
                        roomTabs.updateRoomSettings(roomHelper.getSelectedRoom(), _currentTool, pnlProperties, menuViewProperties.isSelected());
                    }
                    break;
            }
        }
        _pasting = false;
        pnlMap.repaint();
    }

    //-------------------------------------------------
    // Private functions
    //-------------------------------------------------

    private void updateTitle() {
//        this.setTitle("Ele Mapper (V"+EleConstants.VERSION+")"+(_currentFile == null?"":" - "+_currentFile.getAbsolutePath())); //todo Comment out for designer
    }

    private void menuSetup(AbstractButton menuItem, String laf) {
        menuItem.addActionListener(new ChangeLookAndFeelAction(this, laf));
        menuItem.setEnabled(isAvailableLookAndFeel(laf));
        buttonGrouplafMenu.add(menuItem);
        if (laf.equals(UIManager.getSystemLookAndFeelClassName())) menuItem.setSelected(true);
    }

    /**
     * A utility function that layers on top of the LookAndFeel's
     * isSupportedLookAndFeel() method. Returns true if the LookAndFeel
     * is supported. Returns false if the LookAndFeel is not supported
     * and/or if there is any kind of error checking if the LookAndFeel
     * is supported.
     *
     * The L&F menu will use this method to detemine whether the various
     * L&F options should be active or inactive.
     *
     */
    private boolean isAvailableLookAndFeel(String laf) {
        try {
            Class lnfClass = Class.forName(laf);
            LookAndFeel newLAF = (LookAndFeel) lnfClass.newInstance();
            return newLAF.isSupportedLookAndFeel();
        } catch(Exception e) { // If ANYTHING weird happens, return false
            return false;
        }
    }

    /**
     * Stores the current L&F, and calls updateLookAndFeel, below
     */
    public void setLookAndFeel(String laf) {
        if (!_currentLookAndFeel.equals(laf)) {
            _currentLookAndFeel = laf;
            updateLookAndFeel();
        }
    }

    /**
     * Sets the current L&F
     */
    public void updateLookAndFeel() {
        try {
            UIManager.setLookAndFeel(_currentLookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
            SwingUtilities.updateComponentTreeUI(exitTabs);
            SwingUtilities.updateComponentTreeUI(roomTabs);
        } catch (Exception ex) {
            System.out.println("Failed loading L&F: " + _currentLookAndFeel);
            System.out.println(ex);
        }
    }

    private void initialise(EleMap newMap, File currentFile) {
        int x;

        _currentLookAndFeel = UIManager.getSystemLookAndFeelClassName();
        button_actionPerformed(btnSelect, TOOL_SELECT);
        _currentFile = currentFile;
        _stuffToMove = null;
        roomHelper = new RoomHelper();
        exitHelper = new ExitHelper();
        _exitStartPoint = null;
        _makingExit = false;
        _movingStuff = false;
        if (newMap == null) {
            _eleMap = new EleMap();
        } else {
            _eleMap = newMap;
        }
        statusBarLevel.setText(_levelText + _eleMap.getLevel());
        roomTabs.initialise();

        x = _eleMap.getRoomSize() * NUMBER_OF_ROOMS_ON_MAP;
        _size.setSize(x, x);
        pnlMap.setPreferredSize(_size);
        pnlMap.repaint();
    }

    void paintMiniExitMap(JPanel pnlExitMiniMap, Graphics miniMap) {
        if (exitHelper.hasExit()) {
            Exit currentExit = exitHelper.getExit();
            int x = pnlExitMiniMap.getInsets().left;
            int y = pnlExitMiniMap.getInsets().top;
            int height = pnlExitMiniMap.getHeight();
            int width = pnlExitMiniMap.getWidth();
            Point p1 = EleUtils.getPointForDirection(currentExit.getDirection(currentExit.getRoom1()), x, y, height, width);
            Point p2 = EleUtils.getPointForDirection(currentExit.getDirection(currentExit.getRoom2()), x, y, height, width);
            miniMap.setColor(EleConstants.EXIT_COLOUR);
            miniMap.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void addLevelExitItems(JComponent menu) {
        if (roomHelper.hasSingleRoomSelected()) {
            menu.removeAll();

            if (!roomHelper.getSelectedRoom().containsExit(EleConstants.DIRECTION_UP)) {
                menu.add(menuLevelAddUp);
            } else {
                menu.add(menuLevelSelectUp);
                menu.add(menuLevelDeleteUp);
            }

            if (!roomHelper.getSelectedRoom().containsExit(EleConstants.DIRECTION_DOWN)) {
                menu.add(menuLevelAddDown);
            } else {
                menu.add(menuLevelSelectDown);
                menu.add(menuLevelDeleteDown);
            }
        }
    }

    private void genericSave(int option) {
        FileChooser fileChooser;
        DocumentHandler doc;

        if (option == FileChooser.SAVE_AS || _currentFile == null) {
            fileChooser = new FileChooser(option, this, _currentDir);
            _currentFile = fileChooser.chooseFile();
        }

        if (_currentFile != null) {
            _currentDir = _currentFile.getParentFile();
            doc = new DocumentHandler(_currentFile);
            doc.setData(_eleMap);
            try {
                doc.writeToXML();
            } catch (Exception e){
                JOptionPane.showMessageDialog(
                    this,
                    "Error (" + e + ") writing file (" +
                    _currentFile.getAbsolutePath() + "): " +
                    e.getMessage(), // todo JPC e.getStackTrace()[0],
                    "XML Write Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(System.err);
                _currentFile = null;
            }
            updateTitle();
        }
    }
    private void updateCurrentRoom(Point p, boolean shouldFind) {
        updateCurrentRoom(p, shouldFind, false);
    }

    private void updateCurrentRoom(Point p, boolean shouldFind, boolean multiRoomSelect) {
        Room newRoom = null;
//        new Throwable().printStackTrace();
        if (shouldFind) {
            newRoom = (Room) _eleMap.getRooms().exists(p, _eleMap.getLevel());
        }

        if (roomHelper.hasRoomSelected()) {
            Room currentRoom = roomHelper.getSelectedRoom();
            if ((newRoom != null) && (currentRoom.equals(newRoom))) {
                return;
            }
            if(roomHelper.hasMultipleRoomSelected()) {
                multiRoomTabs.saveCurrentRoomSettings(pnlProperties, menuViewProperties.isSelected());
            } else {
                roomTabs.saveCurrentRoomSettings(currentRoom, pnlProperties, menuViewProperties.isSelected());
            }
            if(!multiRoomSelect) {
                roomHelper.deSelect(_mapGraphics, _eleMap.getLevel(), _eleMap.getShowUpper(), _eleMap.getShowLower());
            }
        }
//        System.out.println("Rooms after update Current: "+roomHelper.getSelectedRooms());;

        selectRoom(newRoom);
    }

    private void selectRoom(Room r) {
        if (r != null) { // if the room was not null
            roomHelper.addSelectedRoom(r);
            r.select(_mapGraphics);
            if(roomHelper.hasSingleRoomSelected()) {
                roomTabs.updateRoomSettings(r, _currentTool, pnlProperties, menuViewProperties.isSelected());
            } else if(roomHelper.hasMultipleRoomSelected()) {
                multiRoomTabs.drawTabs(_currentTool, pnlProperties, menuViewProperties.isSelected());
            }
        }
    }

    private void gotoRoom(Room r) {
        if (r != null) {
            selectRoom(r);
            _eleMap.setLevel(roomHelper.getSelectedRoom().getLevel());
            statusBarLevel.setText(_levelText + _eleMap.getLevel());
            pnlMap.repaint();
        }
    }

    private void updateCurrentExit(Point p, boolean shouldFind) {
        Exit e = null;

        if (shouldFind) {
            e = (Exit) _eleMap.getExits().exists(p, _eleMap.getLevel());
        }

        if (exitHelper.hasExit()) {
            Exit currentExit = exitHelper.getExit();
            if ((e != null) && (currentExit.equals(e))) {
                return;
            }
            exitTabs.saveCurrentExitSettings(currentExit, pnlProperties, menuViewProperties.isSelected());
            exitHelper.deSelect(_mapGraphics, _eleMap.getLevel(), _eleMap.getShowUpper(), _eleMap.getShowLower());
        }

        selectExit(e);
    }

    private void selectExit(Exit exit) {
        exitHelper.setExit(exit);
        if (exitHelper.hasExit()) { // if the exit was not null
            exit.select(_mapGraphics);
            updateExitSettings();
        }
    }

    private void gotoExit(Exit e) {
        if (e != null) {
            selectExit(e);
            _eleMap.setLevel(exitHelper.getExit().getRoom1().getLevel());
            statusBarLevel.setText(_levelText + _eleMap.getLevel());
            pnlMap.repaint();
        }
    }

    private void addVerticalExit(int direction) {
        Room r;
        long level;
        boolean up = (direction == EleConstants.DIRECTION_UP);

        // Change tool to selecting.
        button_actionPerformed(btnSelect, TOOL_SELECT);
        if (roomHelper.hasSingleRoomSelected()) {
            Room currentRoom = roomHelper.getSelectedRoom();
            level = currentRoom.getLevel();
            r = (Room) _eleMap.getRooms().exists(currentRoom.getLocation(),
                                                  (up?level+1:level-1));
            if (r == null) {
                JOptionPane.showMessageDialog(this,
                        "There is no room directly "+
                        (up?"above":"below")+
                        " the currently selected one, therefore you cannot add "+
                        (up?"an up":"a down")+" exit.",
                        "Exit Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!currentRoom.containsExit(direction)) {
                roomTabs.saveCurrentRoomSettings(currentRoom, pnlProperties, menuViewProperties.isSelected());
                roomHelper.deSelect(_mapGraphics, _eleMap.getLevel(),  _eleMap.getShowUpper(), _eleMap.getShowLower());
                exitHelper.setExit(new Exit(r, currentRoom, _eleMap.getExitNumber(), (up?level+1:level), _mapGraphics));
                _eleMap.getExits().add(exitHelper.getExit());
                _eleMap.incrementExitNumber();
                updateExitSettings();
            }
        }
    }

    private void selectVerticalExit(int direction) {
        exitHelper.setExit(roomHelper.getSelectedRoom().getExit(direction));
        updateCurrentRoom(null, false);
        clearExitSettings();
        if (exitHelper.hasExit()) {
            exitHelper.getExit().select(_mapGraphics);
            updateExitSettings();
        }
    }

    private void deleteVerticalExit(int direction) {
        exitHelper.setExit(roomHelper.getSelectedRoom().getExit(direction));
        updateCurrentRoom(null, false);
        if (exitHelper.hasExit()) {
            _eleMap.getExits().remove(exitHelper.getExit());
            exitHelper.clearExit();
        }
    }

    private void updateExitSettings() {
        exitTabs.updateExitSettings(exitHelper.getExit(), pnlProperties, menuViewProperties.isSelected());
    }

    private void clearExitSettings() {
        exitTabs.clearExitSettings(pnlProperties, menuViewProperties.isSelected());
    }


    // Monster
    public void addOrUpdateMonster(Monster monster) {
        if(monster != null) {
            _eleMap.addOrUpdateMonster(monster);
        }
    }

    public EleExportableCollection<Monster> getMonsters() {
        return _eleMap.getMonsters();
    }
    //----------------------------------------------------------
    // Private Class
    //----------------------------------------------------------

    private class ChangeLookAndFeelAction extends AbstractAction {
        private EleFrame frame;
        private String laf;

        protected ChangeLookAndFeelAction(EleFrame frame, String laf) {
            super("ChangeTheme");
            this.frame = frame;
            this.laf = laf;
        }

        public void actionPerformed(ActionEvent e) {
            frame.setLookAndFeel(laf);
        }
    }

} //end of class


