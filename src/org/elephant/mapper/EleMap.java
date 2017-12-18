package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Graphics;
import java.awt.Point;

import org.jdom.Element;

/**
 * The <code>EleMap</code> class defines the map object which contains all the
 * information required to build an area in LPC.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleMap implements EleMappable {
    // Note: These must be kept in line with the DTD.
    public static final String XML_MAP = "MAP";
    public static final String XML_CURRENT_ROOM_SIZE = "ROOMSIZE";
    public static final String XML_ROOM_NUMBER = "ROOMNUMBER";
    public static final String XML_EXIT_NUMBER = "EXITNUMBER";
    public static final String XML_CURRENT_LEVEL = "LEVEL";
    public static final String XML_PATH = "PATH";
    public static final int INITIAL_ROOM_SIZE = 20;

    private EleMappableCollection<EleMappable> _rooms = new EleMappableCollection<EleMappable>(EleConstants.XML_ROOMS);
    private EleMappableCollection<EleMappable> _exits = new EleMappableCollection<EleMappable>(EleConstants.XML_EXITS);
    private EleExportableCollection<Monster> _monsters = new EleExportableCollection<>(EleConstants.XML_MONSTERS);
    private int _roomSize = INITIAL_ROOM_SIZE;
    private long _roomNumber = 1;
    private long _exitNumber = 1;
    private long _level = 0;
    private boolean _showLower = true;
    private boolean _showUpper = true;
    private String _path = "";

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public void setRooms(EleMappableCollection<EleMappable> data) {
        _rooms = data;
    }
    public EleMappableCollection<EleMappable> getRooms() {return _rooms;}

    public void setExits(EleMappableCollection<EleMappable> data) {_exits = data;}
    public EleMappableCollection<EleMappable> getExits() {return _exits;}

    public EleExportableCollection<Monster> getMonsters() {
        return _monsters;
    }

    public void setMonsters(EleExportableCollection<Monster> monsters) {
        _monsters = monsters;
    }

    public void addOrUpdateMonster(Monster monster) {
        if(!_monsters.contains(monster)) {
            _monsters.add(monster);
        }
    }

    public void setRoomSize(int data) {_roomSize = data;}
    public int getRoomSize() {return _roomSize;}

    public void setRoomNumber(long data) {_roomNumber = data;}
    public long getRoomNumber() {return _roomNumber;}

    public void setExitNumber(long data) {_exitNumber = data;}
    public long getExitNumber() {return _exitNumber;}

    public void setLevel(long data) {_level = data;}
    public long getLevel() {return _level;}

    public void setShowLower(boolean data) {_showLower = data;}
    public boolean getShowLower() {return _showLower;}

    public void setShowUpper(boolean data) {_showUpper = data;}
    public boolean getShowUpper() {return _showUpper;}

    public void setPath(String data) {_path = data;}
    public String getPath() {return _path;}

    public String getType() {return XML_MAP;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleMap() {}

    //----------------------------------------------------------
    // Static Functions
    //----------------------------------------------------------

    public static EleMappable rebuild(Element root) {
        EleMap newMap = new EleMap();
        Element tmp;

        if (root != null) {
            tmp = root.getChild(XML_CURRENT_ROOM_SIZE);
            if (tmp != null) newMap.setRoomSize(Integer.valueOf(tmp.getTextTrim()));
            tmp = root.getChild(XML_ROOM_NUMBER);
            if (tmp != null) newMap.setRoomNumber(Integer.valueOf(tmp.getTextTrim()));
            tmp = root.getChild(XML_EXIT_NUMBER);
            if (tmp != null) newMap.setExitNumber(Integer.valueOf(tmp.getTextTrim()));
            tmp = root.getChild(XML_CURRENT_LEVEL);
            if (tmp != null) newMap.setLevel(Integer.valueOf(tmp.getTextTrim()));
            tmp = root.getChild(XML_PATH);
            if (tmp != null) newMap.setPath(tmp.getText().trim());
            tmp = root.getChild(EleConstants.XML_ROOMS);
            if (tmp != null) newMap.setRooms(EleMappableCollection.rebuild(newMap, tmp));
            //Important to rebuild the exits AFTER the rooms, as they rely on them.
            tmp = root.getChild(EleConstants.XML_EXITS);
            if (tmp != null) newMap.setExits(EleMappableCollection.rebuild(newMap, tmp));

            tmp = root.getChild(EleConstants.XML_MONSTERS);
            if (tmp != null) newMap.setMonsters(Monster.rebuildMonsters(tmp));
        }

        return newMap;
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    public void incrementRoomNumber() {
        ++_roomNumber;
    }

    public void incrementExitNumber() {
        ++_exitNumber;
    }

    public void incrementLevel() {
        ++_level;
    }

    public void decrementLevel() {
        --_level;
    }

    //----------------------------------------------------------
    // EleMappable Functions
    //----------------------------------------------------------

    public void paint(Graphics g, long level) {
        if (_showLower) paintLowerLevel(g, _level);
        _rooms.paint(g, _level);
        _exits.paint(g, _level);
        if (_showUpper) paintUpperLevel(g, _level);
    }

    public void paintLowerLevel(Graphics g, long level) {
        _rooms.paintLowerLevel(g, _level);
        _exits.paintLowerLevel(g, _level);
    }

    public void paintUpperLevel(Graphics g, long level) {
        _rooms.paintUpperLevel(g, _level);
        _exits.paintUpperLevel(g, _level);
    }

    public void paintOutline(Graphics g, long level, int x, int y) {
        _rooms.paintOutline(g, _level, x, y);
        _exits.paintOutline(g, _level, x, y);
    }

    public void shift(int y, int x) {
        _rooms.shift(y, x);
        _exits.shift(y, x);
    }

    public void zoom(boolean in) {
        _rooms.zoom(in);  //NOTE: Rooms MUST be zoomed first.
        _exits.zoom(in);
    }

    public boolean contains(Point p, long level) {
        return false;
    }

    public Element writeToXML() {
        Element root = new Element(getType());

        root.addContent(EleUtils.createElement(XML_CURRENT_ROOM_SIZE, String.valueOf(_roomSize)));
        root.addContent(EleUtils.createElement(XML_ROOM_NUMBER, String.valueOf(_roomNumber)));
        root.addContent(EleUtils.createElement(XML_EXIT_NUMBER, String.valueOf(_exitNumber)));
        root.addContent(EleUtils.createElement(XML_CURRENT_LEVEL, String.valueOf(_level)));
        root.addContent(EleUtils.createElement(XML_PATH, _path));
        root.addContent(_rooms.writeToXML());
        root.addContent(_exits.writeToXML());
        root.addContent(_monsters.writeToXML());

        return root;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        File roomsDir = new File(directory.getAbsolutePath() + File.separator + "rooms");
        File monDir = new File(directory.getAbsolutePath() + File.separator + "mon");
        File includeDir = new File(directory.getAbsolutePath() + File.separator + "include");
        File includes = new File(includeDir.getAbsolutePath() + File.separator + "include.h");

        checkForExport();

        if (!roomsDir.exists()) {
            roomsDir.mkdir();
        }

        if (!includeDir.exists()) {
            includeDir.mkdir();
        }

        if (!monDir.exists()) {
            monDir.mkdir();
        }

        if (includes.exists()) {
            if (!includes.canWrite()) {
                throw new IOException("Cannot write to file "+includes.getAbsolutePath());
            }
            includes.delete();
        }

        includes.createNewFile();
        bw = new BufferedWriter(new FileWriter(includes));

        bw.write("#include <std.h>\n"+
                 "#include <defs.h>\n"+
                 "#include <daemons.h>\n"+
                 "#include <comdesc.h>\n\n"+
                 "#define PATH      \""+path+"\"\n"+
                 "#define "+EleConstants.DEFINE_OBJ_PATH+"   PATH + \"obj/\"\n"+
                 "#define "+EleConstants.DEFINE_MON_PATH+"   PATH + \"mon/\"\n"+
                 "#define "+EleConstants.DEFINE_ROOM_PATH+"  PATH + \"rooms/\"\n");
        bw.flush();
        bw.close();

        _rooms.export(roomsDir, path + "rooms/", bw, seperator);

        _monsters.export(monDir, path+"mon/", bw, seperator);
    }

    public void checkForExport() throws EleMapExportException {
        _rooms.checkForExport();
        _exits.checkForExport();
        _monsters.checkForExport();
    }

    //----------------------------------------------------------
    // Private Functions
    //----------------------------------------------------------


}
