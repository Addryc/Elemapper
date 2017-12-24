package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

/**
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class Item implements Exportable {
    public static final String XML_ITEM = "ITEM";
    public static final String XML_ITEM_NAME = "ITEMNAME";
    public static final String XML_ITEM_DESCRIPTION = "ITEMDESCRIPTION";

    private List<String> _names;
    private String _desc;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getNames() {return toString();}
    public void setNames(List<String> data) {_names = data;}

    public String getDescription() {return _desc;}
    public void setDescription(String data) {_desc = data;}

    public String getType() {return XML_ITEM;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public Item(List<String> names, String description) {
        _names = names;
        _desc = description;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static Exportable rebuild(Element root) {
        List list;
        Iterator iter;
        List<String> names = new ArrayList<String>();
        Item item = null;

        if (root != null) {
            list = root.getChildren(XML_ITEM_NAME);
            iter = list.iterator();
            while (iter.hasNext()) {
                names.add(((Element) iter.next()).getText().trim());
            }

            if (names.size() > 0) {
                item = new Item(names, root.getChild(XML_ITEM_DESCRIPTION).getText().trim());
            }
        }

        return item;
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    public String toString() {
        String tmp = "";
        String sep = "";

        for (String _name : _names) {
            tmp += sep + _name;
            sep = ", ";
        }

        return tmp;
    }

    public boolean containsNames(Collection v) {
        return v.containsAll(_names);
    }

    //----------------------------------------------------------
    // Exportable Functions
    //----------------------------------------------------------

    public Element writeToXML() {
        Element item = new Element(getType());

        for (String _name : _names) {
            item.addContent(EleUtils.createElement(XML_ITEM_NAME, _name));
        }
        item.addContent(EleUtils.createElement(XML_ITEM_DESCRIPTION, _desc));

        return item;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        boolean moreThanOne = _names.size() > 1;
        String sep = "";
        String item = "";
        String[] array;
        String spacer;
        String firstPart;
        String tmp;

        if (bw != null) {
            bw.write(seperator);
            if (moreThanOne) {
                item = "({";
            }
            for (String _name : _names) {
                item += sep + "\"" + _name + "\"";
                sep = ", ";
            }
            if (moreThanOne) {
                item += "})";
            }
            item += " : ";
            bw.write(item);

            bw.write(Room.replaceMacros("ITEM "+_names.get(0), _desc, 5));
        }
    }

    public void checkForExport() throws EleMapExportException {
        if (_names == null || _names.size() < 1) {
            throw new EleMapExportException("Item found with no name.");
        }
    }
}
