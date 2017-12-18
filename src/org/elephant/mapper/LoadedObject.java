package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.jdom.Element;

/**
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class LoadedObject implements Exportable {
    public static final String XML_LOADED_OBJECT = "LOADOBJ";
    public static final String XML_LOADED_OBJECT_OBJECT = "LOADOBJOBJECT";
    public static final String XML_LOADED_OBJECT_MONSTER = "LOADOBJMONSTER";
    public static final String XML_LOADED_OBJECT_NAME = "LOADOBJNAME";
    public static final String XML_LOADED_OBJECT_MESSAGE = "LOADOBJMESSAGE";
    public static final String XML_LOADED_OBJECT_TRACK = "LOADOBJTRACK";
    public static final String XML_LOADED_OBJECT_UNIQUE = "LOADOBJUNIQUE";
    public static final String XML_LOADED_OBJECT_PRESENT = "LOADOBJPRESENT";
    public static final String XML_LOADED_OBJECT_UUID = "LOADOBJUUID";

    public static final int OBJECT_TYPE_OBJ = 1;
    public static final int OBJECT_TYPE_MON = 2;
    public static final int LOAD_TYPE_TRACK = 1;
    public static final int LOAD_TYPE_PRESENT = 2;
    public static final int LOAD_TYPE_UNIQUE = 3;

    private int _type;
    private int _loadType;
    private String _fileName;
    private String _message;

    // UUID for object/monster
    private String uuid;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getType() {return XML_LOADED_OBJECT;}

    public int getObjectType() {return _type;}
    public void setObjectType(int data) {_type = data;}

    public int getLoadType() {return _loadType;}
    public void setLoadType(int data) {_loadType = data;}

    public String getFileName() {return _fileName;}
    public void setFileName(String data) {_fileName = data;}

    public String getMessage() {return _message;}
    public void setMessage(String data) {_message = data;}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }



    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public LoadedObject(int objectType, int loadType, String fileName, String message) {
        _type = objectType;
        _loadType = loadType;
        _fileName = fileName;
        _message = message;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static Exportable rebuild(Element root) {
        Element tmp;
        LoadedObject object = null;
        int objectType = 0;
        int loadType = 0;
        String fileName = null;
        String message = null;
        String uuid = null;

        if (root != null) {
            tmp = root.getChild(XML_LOADED_OBJECT_OBJECT);
            if (tmp != null) objectType = OBJECT_TYPE_OBJ;
            tmp = root.getChild(XML_LOADED_OBJECT_MONSTER);
            if (tmp != null) objectType = OBJECT_TYPE_MON;
            tmp = root.getChild(XML_LOADED_OBJECT_NAME);
            if (tmp != null) fileName = tmp.getText().trim();
            tmp = root.getChild(XML_LOADED_OBJECT_MESSAGE);
            if (tmp != null) message = tmp.getText().trim();
            tmp = root.getChild(XML_LOADED_OBJECT_PRESENT);
            if (tmp != null) loadType = LOAD_TYPE_PRESENT;
            tmp = root.getChild(XML_LOADED_OBJECT_TRACK);
            if (tmp != null) loadType = LOAD_TYPE_TRACK;
            tmp = root.getChild(XML_LOADED_OBJECT_UNIQUE);
            if (tmp != null) loadType = LOAD_TYPE_UNIQUE;

            tmp = root.getChild(XML_LOADED_OBJECT_UUID);
            if (tmp != null) uuid = tmp.getTextTrim();


            if (objectType != 0 && loadType != 0 && fileName != null && message != null) {
                object = new LoadedObject(objectType, loadType, fileName, message);
                if(uuid!=null){
                    object.setUuid(uuid);
                }
            }
        }

        return object;
    }

    //----------------------------------------------------------
    // Exportable Functions
    //----------------------------------------------------------

    public Element writeToXML() {
        Element obj = new Element(getType());

        if (_type == OBJECT_TYPE_MON) {
            obj.addContent(new Element(XML_LOADED_OBJECT_MONSTER));
        } else {
            obj.addContent(new Element(XML_LOADED_OBJECT_OBJECT));
        }

        obj.addContent(EleUtils.createElement(XML_LOADED_OBJECT_NAME, _fileName));
        obj.addContent(EleUtils.createElement(XML_LOADED_OBJECT_MESSAGE, _message));

        if (_loadType == LOAD_TYPE_PRESENT) {
            obj.addContent(new Element(XML_LOADED_OBJECT_PRESENT));
        } else if (_loadType == LOAD_TYPE_TRACK) {
            obj.addContent(new Element(XML_LOADED_OBJECT_TRACK));
        } else {
            obj.addContent(new Element(XML_LOADED_OBJECT_UNIQUE));
        }
        if(uuid != null) {
            obj.addContent(EleUtils.createElement(XML_LOADED_OBJECT_UUID, uuid));

        }

        return obj;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        String loadType = "";
        String objectType = "";

        if (bw != null) {
            if (_type == OBJECT_TYPE_MON) {
                objectType = EleConstants.DEFINE_MON_PATH;
            } else if (_type == OBJECT_TYPE_OBJ) {
                objectType = EleConstants.DEFINE_OBJ_PATH;
            }

            if (_loadType == LOAD_TYPE_PRESENT) {
                loadType = "RT_PRESENT";
            } else if (_loadType == LOAD_TYPE_TRACK) {
                loadType = "RT_TRACK";
            } else if (_loadType == LOAD_TYPE_UNIQUE) {
                loadType = "RT_UNIQUE";
            }

            bw.write("    add_object("+objectType+" + \""+_fileName+"\", \""+_message+"\", "+loadType+");\n");
        }
    }

    public void checkForExport() throws EleMapExportException {
        if (_fileName == null || _fileName.length() < 1) {
            throw new EleMapExportException("Loaded Object found with no name: "+this);
        }
        if ((_type != OBJECT_TYPE_MON) && (_type != OBJECT_TYPE_OBJ)) {
            throw new EleMapExportException("Loaded Object found with an invalid type: "+this);
        }
        if ((_loadType != LOAD_TYPE_PRESENT) && (_loadType != LOAD_TYPE_TRACK) && (_loadType != LOAD_TYPE_UNIQUE)) {
            throw new EleMapExportException("Loaded Object found with an invalid load type: "+this);
        }
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    public String toString() {
        // This is what is displayed in lists.
        return getFileName();
    }
}
