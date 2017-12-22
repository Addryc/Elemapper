package org.elephant.mapper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.CDATA;
import org.jdom.Element;

/**
 * The <code>Room</code> class defines a room object, one of the two basic components
 * of the map. It holds all the information required to paint a room on the map
 * and also build a room in LPC.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class Room extends Rectangle implements EleMappable {
    // Note: These must be kept in line with the DTD.
    public static final String XML_ROOM = "ROOM";
    public static final String XML_NUMBER = "ROOMID";
    public static final String XML_X = "X";
    public static final String XML_Y = "Y";
    public static final String XML_COLOUR = "COLOUR";
    public static final String XML_LEVEL = "ROOMLEVEL";
    public static final String XML_NAME = "ROOMNAME";
    public static final String XML_CODEDESC = "CODEDESC";
    public static final String XML_SHORT = "SHORT";
    public static final String XML_LONG = "LONG";
    public static final String XML_LONGQUOTES = "LONGQUOTES";
    public static final String XML_LIGHT = "LIGHT";
    public static final String XML_TERRAIN = "TERRAIN";
    public static final String XML_BOUNDARY = "BOUNDARY";
    public static final String XML_CODE = "ROOMCODE";

    public static final int OUT_UP = 16;
    public static final int OUT_DOWN = 32;
    public static final int MAX_TERRAINS = 3;
    public static final String[] TERRAINS = {"forest","plains","hills","mountains","town",
        "coast","sea","swamp","jungle","underwater","desert","river","tundra","snowplains"};
    public static final String VISIBLE_DEFAULT_SENSE = "default";
    public static final String DEFAULT_SENSE = "";

    private boolean _selected = false;
    private long _roomNumber = 0;
    private long _level = 0;
    private Exit[] _exits = new Exit[10];
    private int _numberOfExits = 0;
    private String _shortDesc = "";
    private String _roomName = "";
    private String _longDesc = "";
    private boolean _longDescQuotes = true;
    private String _codeDesc = "";
    private EleExportableCollection<Exportable> _items = new EleExportableCollection<Exportable>(EleConstants.XML_ITEMS);
    private int[] _terrains;
    private int _light = 3;
    private EleHashtable _smells = new EleHashtable(EleConstants.XML_SMELLS);
    private EleHashtable _sounds = new EleHashtable(EleConstants.XML_SOUNDS);
    private EleExportableCollection<Exportable> _objects = new EleExportableCollection<Exportable>(EleConstants.XML_LOADED);
    private EleExportableCollection<Exportable> _functions = new EleExportableCollection<Exportable>(EleConstants.XML_FUNCTIONS);
    private Color _colour = null;
    private String _boundary = "";
    private String _subdir = "";
    private String _code = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Room room = (Room) o;
        return _roomNumber == room._roomNumber;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), _roomNumber);
    }

//----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public long getRoomNumber() {return _roomNumber;}

    public long getLevel() {return _level;}
    public void setLevel(long data) {_level = data;}

    public int getNumberOfExits() {return _numberOfExits;}

    public Exit getExit(int direction) {return _exits[EleUtils.translateDirectionToIndex(direction)];}

    public String getShortDescription() {return _shortDesc;}
    public void setShortDescription(String data) {_shortDesc = data;}

    public String getCodeDescription() {return _codeDesc;}
    public void setCodeDescription(String data) {_codeDesc = data;}

    public String getRoomName() {return _roomName;}
    public void setRoomName(String data) {_roomName = data;}

    public String getBoundary() {return _boundary;}
    public void setBoundary(String data) {_boundary = data;}

    public String getExtraCode() {return _code;}
    public void setExtraCode(String data) {_code = data;}

    public String getSubDir() {return _subdir;}
    public void setSubDir(String data) {_subdir = data;}

    public String getLongDescription() {return _longDesc;}
    public void setLongDescription(String data) {_longDesc = data;}

    public boolean isLongDescQuotes() {
        return _longDescQuotes;
    }

    public void setLongDescQuotes(boolean _longDescQuotes) {
        this._longDescQuotes = _longDescQuotes;
    }



    public String getType() {return XML_ROOM;}

    public EleExportableCollection<Exportable> getItems() {return _items;}
    private void setItems(EleExportableCollection<Exportable> data) {_items = data;}

    public EleExportableCollection<Exportable> getLoadedObjects() {return _objects;}
    private void setLoadedObjects(EleExportableCollection<Exportable> data) {_objects = data;}

    public EleExportableCollection<Exportable> getFunctions() {return _functions;}
    private void setFunctions(EleExportableCollection<Exportable> data) {_functions = data;}

    public int[] getTerrains() {return _terrains;}
    private void setTerrains(int[] data) {_terrains = data;}

    public int getLight() {return _light;}
    public void setLight(int data) {_light = data;}

    public EleHashtable getSmells() {return _smells;}
    private void setSmells(EleHashtable data) {_smells = data;}

    public EleHashtable getSounds() {return _sounds;}
    private void setSounds(EleHashtable data) {_sounds = data;}

    public Color getColour() {return _colour;}
    public void setColour(Color data) {_colour = data;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public Room(Point p, int s, long n, long l) {
        super(p, new Dimension(s, s));
        init(n, l);
    }

    public Room(int x, int y, int s, long n, long l) {
        super(x, y, s, s);
        init(n, l);
    }

    public Room(Point p, int s, long n, long l, Graphics g) {
        super(p, new Dimension(s, s));
        init(n, l);
        _selected = true;
        paint(g);
    }

    public Room(int x, int y, int s, long n, long l, Graphics g) {
        super(x, y, s, s);
        init(n, l);
        _selected = true;
        paint(g);
    }

    private void init(long n, long l) {
        _roomNumber = n;
        _level = l;
        resetTerrains();
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    //Alter a point so that it falls nicely onto a 'grid'
    public static Point alterForRoom(Point p, int roomSize) {
        Point np = new Point();

        np.x = (p.x - (roomSize / 2)) / roomSize;
        if ((np.x % 2) != 0)
            ++np.x;

        np.y = (p.y - (roomSize / 2)) / roomSize;
        if ((np.y % 2) != 0)
            ++np.y;

        np.x *= roomSize;
        np.y *= roomSize;

        return np;
    }

    public static Room getRoomFromNumber(EleExportableCollection<Exportable> rooms, long number) {
        Room tmp = null;

        for (Object room : rooms) {
            if (((Room) room).getRoomNumber() == number) {
                tmp = (Room) room;
                break;
            }
        }
        return tmp;
    }

    public static EleMappableCollection<EleMappable> getAllLinked(EleMappableCollection<EleMappable> v, Room r) {
        Exportable tmp;
        Iterator<Exportable> iter;

        v.add(r);

        iter = r.getAdjacentRoomsAndExits().iterator();
        while (iter.hasNext()) {
            tmp = iter.next();
            if (!v.contains(tmp)) {
                if (tmp instanceof Room) {
                    v = getAllLinked(v, (Room) tmp);
                } else {
                    v.add(tmp);
                }
            }
        }

        return v;
    }

    public static EleMappable rebuild(EleMappable data, Element root) {
        int size = ((EleMap) data).getRoomSize();
        Room r = null;
        Long number = null;
        Integer x = null;
        Integer y = null;
        Long level = null;
        Element tmp;
        List tmpList;
        Iterator iter;

        if (root != null) {
            tmp = root.getChild(XML_NUMBER);
            if (tmp != null) number = Long.valueOf(tmp.getTextTrim());
            tmp = root.getChild(XML_X);
            if (tmp != null) x = Integer.valueOf(tmp.getTextTrim());
            tmp = root.getChild(XML_Y);
            if (tmp != null) y = Integer.valueOf(tmp.getTextTrim());
            tmp = root.getChild(XML_LEVEL);
            if (tmp != null) level = Long.valueOf(tmp.getTextTrim());

            if (number != null && x != null && y != null && level != null) {
                r = new Room(x, y, size, number, level);
                tmp = root.getChild(XML_COLOUR);
                if (tmp != null) r.setColour(Color.decode(tmp.getTextTrim()));
                tmp = root.getChild(XML_NAME);
                if (tmp != null) r.setRoomName(tmp.getTextTrim());
                tmp = root.getChild(XML_CODEDESC);
                if (tmp != null) r.setCodeDescription(tmp.getTextTrim());
                tmp = root.getChild(XML_SHORT);
                if (tmp != null) r.setShortDescription(tmp.getTextTrim());
                tmp = root.getChild(XML_LONG);
                if (tmp != null) r.setLongDescription(tmp.getText().trim());
                tmp = root.getChild(XML_LONGQUOTES);
                if (tmp != null) r.setLongDescQuotes(Boolean.valueOf(tmp.getTextTrim()));
                tmp = root.getChild(XML_LIGHT);
                if (tmp != null) r.setLight(Integer.valueOf(tmp.getTextTrim()));
                tmp = root.getChild(XML_BOUNDARY);
                if (tmp != null) r.setBoundary(tmp.getText().trim());
                tmp = root.getChild(XML_CODE);
                if (tmp != null) r.setExtraCode(tmp.getText().trim());
                tmpList = root.getChildren(XML_TERRAIN);
                iter = tmpList.iterator();
                while (iter.hasNext()) {
                    tmp = (Element) iter.next();
                    r.addTerrain(tmp.getText().trim());
                }
                tmp = root.getChild(EleConstants.XML_SMELLS);
                if (tmp != null) r.setSmells((EleHashtable) EleHashtable.rebuild(tmp));
                tmp = root.getChild(EleConstants.XML_SOUNDS);
                if (tmp != null) r.setSounds((EleHashtable) EleHashtable.rebuild(tmp));
                tmp = root.getChild(EleConstants.XML_LOADED);
                if (tmp != null) r.setLoadedObjects(EleExportableCollection.rebuild(tmp));
                tmp = root.getChild(EleConstants.XML_ITEMS);
                if (tmp != null) r.setItems(EleExportableCollection.rebuild(tmp));
                tmp = root.getChild(EleConstants.XML_FUNCTIONS);
                if (tmp != null) r.setFunctions(EleExportableCollection.rebuild(tmp));
                // functions and items must be loaded before guards...
            }
        }

        return r;
    }

    //----------------------------------------------------------
    // Public Funtions
    //----------------------------------------------------------

    public Room clone(int s, long n) {
        Room clone = new Room(getLocation(), s, n, getLevel());
        clone.setBoundary(getBoundary());
        clone.setCodeDescription(getCodeDescription());
        clone.setColour(getColour());
        clone.setItems(getItems());
        clone.setLight(getLight());
        clone.setLoadedObjects(getLoadedObjects());
        clone.setLongDescription(getLongDescription());
        clone.setShortDescription(getShortDescription());
        clone.setSmells(getSmells());
        clone.setSounds(getSounds());
        clone.setSubDir(getSubDir());
        clone.setTerrains(getTerrains());
        return clone;
    }

    public void update(Room otherRoom) {
        setBoundary(otherRoom.getBoundary());
        setCodeDescription(otherRoom.getCodeDescription());
        setColour(otherRoom.getColour());
        setItems(otherRoom.getItems());
        setLight(otherRoom.getLight());
        setLoadedObjects(otherRoom.getLoadedObjects());
        setLongDescription(otherRoom.getLongDescription());
        setShortDescription(otherRoom.getShortDescription());
        setSmells(otherRoom.getSmells());
        setSounds(otherRoom.getSounds());
        setSubDir(otherRoom.getSubDir());
        setTerrains(otherRoom.getTerrains());
    }

    public Item getItem(int index) {return (Item) _items.get(index);}

    public String getTotalRoomName() {return getSubDir() + getRoomName();}

//    public LoadedObject getLoadedObject(int index) {return (LoadedObject) _objects.get(index);}
//
//    public Function getFunction(int index) {return (Function) _functions.get(index);}

    public Object[] getExitDirections() {
        Collection<String> retValue = new ArrayList<String>();

        for (int i = 0; i < _exits.length; i++) {
            if (_exits[i] != null) {
                retValue.add(EleConstants.DIRECTIONS[i]);
            }
        }

        return retValue.toArray();
    }

    public void resetTerrains() {
        int[] tmp = new int[MAX_TERRAINS];
        for (int i = 0; i < MAX_TERRAINS; i++) {
            tmp[i] = -1;
        }
        setTerrains(tmp);
    }

    public void setTerrain(int i, int data) {
        if (i >= 0 && i < MAX_TERRAINS) {
            _terrains[i] = data;
        }
    }

    public void addTerrain(String data) {
        for (int i = 0; i < MAX_TERRAINS; i++) {
            if (_terrains[i] == -1) {
                for (int j = 0; j < TERRAINS.length; j++) {
                    if (TERRAINS[j].equalsIgnoreCase(data)) {
                        _terrains[i] = j;
                        break;
                    }
                }
                break;
            }
        }
    }

    public boolean isIndoors() {
        return !(numberOfTerrains() > 0);
    }

    public void addExit(Exit e, int d) {
        int i = EleUtils.translateDirectionToIndex(d);
        if (_exits[i] == null) {
            ++_numberOfExits;
            _exits[i] = e;
        }
    }

    public void removeExit(Exit e, int d) {
        int i = EleUtils.translateDirectionToIndex(d);
        if (_exits[i] != null) {
            --_numberOfExits;
            _exits[i] = null;
        }
    }

    public boolean containsExit(int direction) {
        return (_exits[EleUtils.translateDirectionToIndex(direction)] != null);
    }

    public void select(Graphics g) {
        _selected = true;
        paint(g);
    }

    public void deSelect(Graphics g, long level, boolean showUpper, boolean showLower) {
        _selected = false;
        if (_level == level) {
            paint(g, level);
        } else if ((_level == level + 1) && showUpper) {
            paintUpperLevel(g, level);
        } else if ((_level == level - 1) && showLower) {
            paintLowerLevel(g, level);
        }
    }

    public int directionToRoom(Room r) {
        long otherLevel = r.getLevel();
        int out = outcode((double)r.x, (double)r.y);

        if (out == 0) {
            if ((_level - 1) == otherLevel)
                out |= OUT_DOWN;
            else if ((_level + 1) == otherLevel)
                out |= OUT_UP;
        } else {
            if (_level != otherLevel)
                out = 0;
        }

        return out;
    }

    public int adjacentRoom(Room otherRoom) {
        Point p = otherRoom.getLocation();

        if (contains(p))
            return 0;

        Rectangle rt = new Rectangle(x, y, width, height);

        rt.grow(height*2, width*2);

        if (rt.contains(p))
            return directionToRoom(otherRoom);

        return 0;
    }

    public Point getPointForDirection(int d) {
        return EleUtils.getPointForDirection(d, x, y, height, width);
    }

    public Rectangle getExitRectangle(int d) {
        Rectangle r = new Rectangle(x, y, width, height);

        switch(d) {
        case EleConstants.DIRECTION_UP:
            //err?
            break;
        case EleConstants.DIRECTION_NORTH:
            r.translate(0, -(height/2));
            r.height /= 2;
            break;
        case EleConstants.DIRECTION_NORTHEAST:
            r.translate(width, -(height/2));
            r.height /= 2;
            r.width /= 2;
            break;
        case EleConstants.DIRECTION_NORTHWEST:
            r.translate(-(width/2), -(height/2));
            r.height /= 2;
            r.width /= 2;
            break;
        case EleConstants.DIRECTION_EAST:
            r.translate(width, 0);
            r.width /= 2;
            break;
        case EleConstants.DIRECTION_DOWN:
            //err?
            break;
        case EleConstants.DIRECTION_SOUTH:
            r.translate(0, height);
            r.height /= 2;
            break;
        case EleConstants.DIRECTION_SOUTHWEST:
            r.translate(-(width/2), height);
            r.height /= 2;
            r.width /= 2;
            break;
        case EleConstants.DIRECTION_SOUTHEAST:
            r.translate(width, height);
            r.height /= 2;
            r.width /= 2;
            break;
        case EleConstants.DIRECTION_WEST:
            r.translate(-(width/2), 0);
            r.width /= 2;
            break;
        }
        return r;
    }

    //----------------------------------------------------------
    // EleMappable Functions
    //----------------------------------------------------------

    public void paint(Graphics g, long l) {
        Graphics2D g2D = (Graphics2D) g;
        if (g2D != null && l == _level) {
            g2D.setColor(EleConstants.BACKGROUND_COLOUR);
            g2D.drawRect(x, y, width, height);

            g2D.setColor(_colour == null ? EleConstants.ROOM_COLOUR : _colour);
            g2D.fill3DRect(x, y, width, height, true);
            if (_selected) {
                g2D.setColor(EleConstants.SELECT_COLOUR);
                g2D.drawRect(x, y, width, height);
            }
        }
    }

    public void paintLowerLevel(Graphics g, long l) {
        if (g != null && _level == l - 1) {
            g.setColor(EleConstants.LOWER_LEVEL_COLOUR);
            g.fillRect(x - width/3, y + height/3, width, height);

            if (_selected) {
                g.setColor(EleConstants.SELECT_COLOUR);
                g.drawRect(x - width/3, y + height/3, width, height);
            }
        }
    }

    public void paintUpperLevel(Graphics g, long l) {
        if (g != null && _level == l + 1) {
            g.setColor(EleConstants.UPPER_LEVEL_COLOUR);
            g.drawRect(x + width/3, y - height/3, width, height);

            if (_selected) {
                g.setColor(EleConstants.SELECT_COLOUR);
                g.drawRect(x + width/3, y - height/3, width, height);
            }
        }
    }

    public void paintOutline(Graphics g, long l, int newX, int newY) {
        if (g != null && l == _level) {
            g.drawRect(x + newX, y + newY, width, height);
        }
    }

    public void shift(int y, int x) {
        translate(x, y);
    }

    public void zoom(boolean in) {
        if (in) {
            setSize(width*2, height*2);
            x *= 2;
            y *= 2;
        } else {
            setSize(width/2, height/2);
            x /= 2;
            y /= 2;
        }
    }

    public boolean contains(Point p, long l) {
        if (l == _level)
            return super.contains(p);
        return false;
    }

    public Element writeToXML() {
        Element room = new Element(getType());
        Element codeElement;

        room.addContent(EleUtils.createElement(XML_NUMBER, String.valueOf(_roomNumber)));
        room.addContent(EleUtils.createElement(XML_X, String.valueOf(x)));
        room.addContent(EleUtils.createElement(XML_Y, String.valueOf(y)));
        if (_colour != null) {
            room.addContent(EleUtils.createElement(XML_COLOUR, String.valueOf(_colour.getRGB())));
        }
        room.addContent(EleUtils.createElement(XML_LEVEL, String.valueOf(_level)));
        room.addContent(EleUtils.createElement(XML_NAME, _roomName));
        room.addContent(EleUtils.createElement(XML_CODEDESC, _codeDesc));
        room.addContent(EleUtils.createElement(XML_SHORT, _shortDesc));
        room.addContent(EleUtils.createElement(XML_LONG, _longDesc));
        room.addContent(EleUtils.createElement(XML_LONGQUOTES, String.valueOf(_longDescQuotes)));
        room.addContent(EleUtils.createElement(XML_LIGHT, String.valueOf(_light)));
        room.addContent(EleUtils.createElement(XML_BOUNDARY, _boundary));
        codeElement = new Element(XML_CODE);
        codeElement.addContent(new CDATA(_code));
        room.addContent(codeElement);
        for (final int terrain : _terrains) {
            if (terrain == -1) break;
            room.addContent(EleUtils.createElement(XML_TERRAIN, TERRAINS[terrain]));
        }
        room.addContent(_smells.writeToXML());
        room.addContent(_sounds.writeToXML());
        room.addContent(_objects.writeToXML());
        room.addContent(_items.writeToXML());
        room.addContent(_functions.writeToXML());

        return room;
    }

    static final String MACROCHANGE_REGEX = "([A-Z]+)CHANGE\\((.+?)\\)";
    static final Pattern ALL_CHANGE_PATTERN = Pattern.compile(MACROCHANGE_REGEX);

    public static String replaceMacros(String id, String longDesc) throws EleMapExportException {

        Matcher matcher;
        String indent = EleConstants.getIndent(3);
        matcher = ALL_CHANGE_PATTERN.matcher(longDesc);
        List<String> tokens = new ArrayList<>();
        int subSequenceEnd = 0;
        longDesc = EleUtils.replace(longDesc, "\n", "\\n");
        while (matcher.find()) {
            // Add any substrings between matches
            String untokenized  = longDesc.substring(subSequenceEnd, matcher.start());
            tokens.add(wrapText(untokenized));
            String matchBase = matcher.group(0);
            String macroType = matcher.group(1);
            String matchReplace = "";

            EleConstants.MACRO macro = EleConstants.MACRO.find(macroType);
            if (macro!=null) {
                if(!macro.validMacro(matcher.group(2))) {
                    throw new EleMapExportException(id+ " description has an invalid macro ("+
                            matchBase+").", null);

                }
                matchReplace = wrapText(macro.getMacroName(),
                        macro.replaceMacroValues(matcher.group(2)),
                        macro.getType());
            }

            tokens.add(matchReplace);
            subSequenceEnd = matcher.end();

        }

        if(tokens.size()>0) {
            if(subSequenceEnd<longDesc.length()) {
                tokens.add(wrapText(longDesc.substring(subSequenceEnd, longDesc.length())));
            }

            longDesc = "COMBINE(({" +
                    "\n"+indent+
                    String.join(",\n"+indent, tokens)+"}))";
        } else {
            longDesc = "\""+longDesc+"\"";
        }
        return longDesc;
    }

    private static String wrapText(String macro, String[] fragments, EleConstants.MACRO_TYPE macroType) {
        String spacer = "\n"+EleConstants.getIndent(3);

        String ret = "";
        String line = "";
        ret+=macro+"(";
        switch(macroType) {
            case ARRAY:
                ret+="({";
                break;
            case MAPPING:
                ret+="([";
                break;
        }
        spacer+="    ";
        int i= 0;
        for (String fragment: fragments) {
            line = spacer+"\""+fragment+"\"";
            if (line.length() < EleConstants.MAX_LINE_LENGTH) {
                ret+=line;
            } else {
                ret+=spacer+wrapText(fragment, 4);
            }

            if(++i < fragments.length) {
                ret+=",";
            }
        }
        switch(macroType) {
            case ARRAY:
                ret+="})";
                break;
            case MAPPING:
                ret+="])";
                break;
        }
        ret+=")";
        return ret ;


    }
    private static String wrapText(String fragment) {
        return wrapText(fragment, 3);
    }
    private static String wrapText(String fragment, int indents) {
        String ret = "";
        String spacer;

        String[] tmpArray = EleUtils.breakString(fragment, 60);

        spacer = "";
        String indent = EleConstants.getIndent(indents);
        for(int i=0;i<tmpArray.length;i++) {
            if (i>0) {
                spacer = "\n"+indent;
            }
            ret+=(spacer + "\"" + tmpArray[i] + "\"");

        }

        return ret;

    }

    private String getLongDescTokenized(boolean tokenReplace) throws EleMapExportException {
        if (tokenReplace) {
            return Room.replaceMacros("Room "+_roomNumber, _longDesc);
        } else {
            return _longDesc;
        }
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        File room = new File(directory.getAbsolutePath() + File.separator + _roomName + ".c");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMM yyyy");
        String[] tmpArray;
        String tmp = "";
        String spacer;
        List<Exit> doors = new ArrayList<Exit>();
        String doorName;
        boolean optionalExitInfo1;
        boolean optionalExitInfo2;
        boolean optionalExitInfo3;
        int percent;
        int numberOfTerrains = numberOfTerrains();

        if (room.exists()) {
            if (!room.canWrite()) {
                throw new IOException("Cannot write to file "+room.getAbsolutePath());
            }
            room.delete();
        }

        room.createNewFile();
        bw = new BufferedWriter(new FileWriter(room));

        bw.write("//      " + path + _roomName + ".c\n"+
                 "//      " + ("".equals(_codeDesc)?"<description>":_codeDesc) + "\n"+
                 "//      From the Elephant Mudlib\n"+
                 "//      AutoCoded by EleMapper at "+
                 (sdf.format(new Date(System.currentTimeMillis())))+
                 "\n\n#include \"../include/include.h\"\n\n");

        if (_numberOfExits > 0) {
            for (final Exit exit : _exits) {
                if (exit != null) {
                    if (exit.isDoor()) {
                        doors.add(exit);
                    }
                }
            }
        }

        bw.write("inherit ROOM;");

        if (_functions.size() > 0) {
            bw.write("\n");
            for (Object _function : _functions) {
                bw.write("\n" + ((Function) _function).getFunctionDeclaration() + ";");
            }
        }

        bw.write("\n\nvoid create()\n"+
                 "{\n"+
                 "    ::create();\n"+
                 "    set_light("+_light+");\n");

        // Terrains
        if (numberOfTerrains > 0) {
            bw.write("    set_terrain(");
            if (numberOfTerrains == 1) {
                bw.write("\""+TERRAINS[_terrains[0]]+"\"");
            } else {
                percent = 100 / numberOfTerrains;
                bw.write("([");
                spacer = "";
                for (int i = 0; i < numberOfTerrains; i++) {
                    bw.write(spacer+"\""+TERRAINS[_terrains[i]]+"\":"+percent);
                    spacer = ", ";
                }
                bw.write("])");
            }
            bw.write(");\n");
        }

        // Boundary
        if (_boundary.length() > 0) {
            bw.write("    set_boundary(\""+_boundary+"\");\n");
        }

        // Descriptions
        bw.write("    set_short(\""+_shortDesc+"\");\n");

        bw.write("    set_long(");
        bw.write(getLongDescTokenized(_longDescQuotes));
        bw.write(");\n");

        // Items
        bw.write("    set_items(([");
        spacer = "";
        if (_items.size() > 0) {
            spacer = ",\n                ";
            _items.export(null, null, bw, spacer);
        }
        for (Exit door : doors) {
            doorName = door.getDoorName(this);
            bw.write(spacer + "\"" + doorName + "\" : DOORCHANGE(\"" + doorName + "\"");
            spacer = ",\n                ";
            tmp = door.getDoorDescription(this);
            if (tmp == null) tmp = "";
            bw.write(spacer + "    \"" + tmp + "\"");
            tmp = door.getDoorOpenDescription(this);
            if (tmp == null) tmp = "";
            bw.write(spacer + "    \"" + tmp + "\"");
            tmp = door.getDoorClosedDescription(this);
            if (tmp == null) tmp = "";
            bw.write(spacer + "    \"" + tmp + "\")");
        }
        bw.write("]));\n");

        // Exits
        bw.write("    set_exits(([");
        if (_numberOfExits > 0) {
            spacer = "";
            for (int i = 0; i < _exits.length; i++) {
                if (_exits[i] != null) {
                    bw.write(spacer+"\""+EleConstants.DIRECTIONS[i]+"\" : "+EleConstants.DEFINE_ROOM_PATH+" + \""+
                             _exits[i].getOppositeRoom(this).getTotalRoomName()+"\"");
                    spacer = ",\n                ";
                }
            }
        }
        bw.write("]));\n");

        if (_numberOfExits > 0) {
            spacer = "";
            // Invis exits
            for (int i = 0; i < _exits.length; i++) {
                if (_exits[i] != null) {
                    tmp = "";
                    if (_exits[i].isInvisible(this)) {
                        tmp = spacer+"\""+EleConstants.DIRECTIONS[i]+"\"";
                        spacer = ", ";
                    }
                }
            }
            if (tmp.length() > 0) {
                bw.write("    set_invis_exits(({"+tmp+"}));\n");
            }

            // Doors
            if (doors.size() > 0) {
                Exit door;
                for (int i = 0; i < _exits.length; i++) { // Need to loop through the exits rather than doors here so that we have the correct index into EleConstants.DIRECTIONS
                    if (_exits[i] != null) {
                        if (_exits[i].isDoor()) {
                            door = _exits[i];
                            optionalExitInfo1 = door.isLockable();
                            optionalExitInfo2 = !door.getDoorName1().equalsIgnoreCase(door.getDoorName2());
                            optionalExitInfo3 = door.isConcealed(this);

                            bw.write("    set_door(\""+door.getDoorName(this)+"\",\n"+
                                     "             \""+EleConstants.DIRECTIONS[i]+"\"");
                            if (optionalExitInfo1 || optionalExitInfo2 || optionalExitInfo3) {
                                if (optionalExitInfo1) {
                                    bw.write(",\n             \"" + door.getKeyID(this) + "\"");
                                } else {
                                    bw.write(",\n             0");
                                }
                                if (optionalExitInfo2) {
                                    bw.write(",\n             \"EleMapperDoorId"+door.getExitNumber()+"\"");
                                } else {
                                    bw.write(",\n             0");
                                }
                                if (optionalExitInfo3) {
                                    bw.write(",\n             1");
                                }
                            }
                            bw.write(");\n");
                        }
                    }
                }
            }
        }

        // Senses
        _smells.export(null, null, bw, null);
        _sounds.export(null, null, bw, null);

        // Loaded Objects
        _objects.export(null, null, bw, null);

        // End of create
        bw.write("\n}\n");

        // Functions
        _functions.export(null, null, bw, null);

        bw.write("\n");
        bw.write(_code);
        bw.write("\n");
        bw.flush();
        bw.close();
    }

    public void checkForExport() throws EleMapExportException {
        Collection<String> v = new ArrayList<String>();
        String tmp;

        if (_roomName == null || _roomName.length() < 1) {
            throw new EleMapExportException("Room "+_roomNumber+" has no name.", this);
        } else {
            if (_roomName.indexOf(' ') != -1) {
                throw new EleMapExportException("Room "+_roomNumber+" has spaces in it's name.", this);
            }
        }

        if (_numberOfExits > 0) {
            for (final Exit newVar : _exits) {
                if (newVar != null) {
                    if (newVar.isDoor()) { // && _exits[i].isPrimaryRoom(this)) {
                        tmp = newVar.getDoorName(this);
                        if (v.contains(tmp)) {
                            throw new EleMapExportException("Room " + _roomNumber + " has exits which " +
                                    "duplicate door name: " + tmp, this);
                        } else {
                            v.add(tmp);
                        }
                    }
                }
            }
        }

        _objects.checkForExport();
        _items.checkForExport();
        _functions.checkForExport();
    }

    //----------------------------------------------------------
    // Private Functions
    //----------------------------------------------------------

    private void paint(Graphics g) {
        paint(g, _level);
    }

    private int numberOfTerrains() {
        int i;
        for (i = 0; i < _terrains.length; i++) {
            if (_terrains[i] == -1) break;
        }
        return i;
    }

    private EleMappableCollection<EleMappable> getAdjacentRoomsAndExits() {
        EleMappableCollection<EleMappable> v = new EleMappableCollection<EleMappable>(EleConstants.XML_BOTH);

        for (final Exit exit : _exits) {
            if (exit != null) {
                v.add(exit);
                v.add(exit.getOppositeRoom(this));
            }
        }

        return v;
    }

}
