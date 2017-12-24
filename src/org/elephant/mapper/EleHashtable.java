package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

/**
 * This class is an extension of hashtable but has some useful accessing
 * methods on it.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleHashtable extends HashMap<String, String> implements Exportable {
    public static final String XML_SENSE = "SENSE";
    public static final String XML_SENSE_NAME = "SENSENAME";
    public static final String XML_SENSE_DESCRIPTION = "SENSEDESCRIPTION";
    private String _type;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getType() {return _type;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleHashtable(String type) {
        _type = type;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static Exportable rebuild(Element root) {
        Element tmp;
        List list;
        Iterator iter;
        EleHashtable hash = null;

        if (root != null) {
            hash = new EleHashtable(root.getName());
            list = root.getChildren(XML_SENSE);
            iter = list.iterator();
            while (iter.hasNext()) {
                tmp = (Element) iter.next();
                hash.put(tmp.getChild(XML_SENSE_NAME).getText().trim(),
                         tmp.getChild(XML_SENSE_DESCRIPTION).getText().trim());
            }
        }

        return hash;
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    /**
     * checks if the <code>key</code> is not null before putting
     * it into this hashtable.
     * <p>
     * @param      key     the hashtable key.
     * @param      value   the value.
     * @return     the previous value of the specified key in this hashtable,
     *             or <code>null</code> if it did not have one or if one of the
     *             paramters is <code>null</code>.
     */
    public synchronized String putString(String key, String value) {
        String tmpValue = null;

        if (value != null && key != null && key.length() > 0) {
            tmpValue = put(key, value);
        }

        return tmpValue;
    }

    /**
     * checks if the specified <code>key</code> already exists in this
     * hashtable (ignoring case) if not, then it maps the specified
     * <code>key</code> to the specified <code>value</code> in this hashtable.
     * Neither the key nor the value can be <code>null</code>.
     * <p>
     * The value can be retrieved by calling the <code>get</code> method
     * with a key that is equal to the original key.
     * <p>
     * @param      key     the hashtable key.
     * @param      value   the value.
     * @return     the previous value of the specified key in this hashtable,
     *             or <code>null</code> if it did not have one.
     * @exception  NullPointerException  if the key or value is
     *               <code>null</code>.
     */
    public synchronized String put(String key, String value) {
        Iterator<String> iter = keySet().iterator();
        String tmpKey;
        String tmpValue = null;

        while (iter.hasNext()) {
            tmpKey = iter.next();
            if (tmpKey.equalsIgnoreCase(key)) {
                tmpValue = get(tmpKey);
                remove(tmpKey);
                break;
            }
        }
        super.put(key, value);
        return tmpValue;
    }

    /**
     * removes both the key and its corresponding value at the specified index
     * from this hashtable.
     * <p>
     * @param   index the index of the pair that need to be removed.
     * @return  the value to which the index had been mapped in this hashtable,
     *          or <code>null</code> if the index did not have a mapping.
     */
    public synchronized String remove(int index) {
        Iterator<String> iter = keySet().iterator();
        String key;
        String value = null;
        int count = 0;

        while (iter.hasNext()) {
            key = iter.next();
            if (index == count) {
                value = get(key);
                super.remove(key);
                break;
            }
            count++;
        }
        return value;
    }

    /**
     * checks if the <code>key</code> is not null before attempting to
     * remove it (and its corresponding value) from this
     * hashtable. This method does nothing if the key is not in the hashtable.
     * <p>
     * @param   key   the key that needs to be removed.
     * @return  the value to which the key had been mapped in this hashtable,
     *          or <code>null</code> if the key did not have a mapping or was
     *          <code>null</code>.
     */
    public synchronized String remove(String key) {
        String tmpValue = null;

        if (key != null) {
            tmpValue = super.remove(key);
        }

        return tmpValue;
    }

    /**
     * gets the key at the specified index from this hashtable.
     * <p>
     * @param   index the index of the key to be returned.
     * @return  the key to which the index had been mapped in this hashtable,
     *          or <code>null</code> if the index did not have a mapping.
     */
    public synchronized String getKey(int index) {
        Iterator<String> iter = keySet().iterator();
        String key;
        String retValue = null;
        int count = 0;

        while (iter.hasNext()) {
            key = iter.next();
            if (index == count) {
                retValue = key;
                break;
            }
            count++;
        }
        return retValue;
    }

    /**
     * gets the value at the specified index from this hashtable.
     * <p>
     * @param   index the index of the value to be returned.
     * @return  the value to which the index had been mapped in this hashtable,
     *          or <code>null</code> if the index did not have a mapping.
     */
    public synchronized String getValue(int index) {
        String key = getKey(index);
        if (key != null) return get(key);
        return null;
    }

    //----------------------------------------------------------
    // Exportable Functions
    //----------------------------------------------------------

    public Element writeToXML() {
        Element item = new Element(getType());
        Element sense;
        Iterator<String> iter = keySet().iterator();
        String key;

        while (iter.hasNext()) {
            key = iter.next();
            sense = new Element(XML_SENSE);
            sense.addContent(EleUtils.createElement(XML_SENSE_NAME, key));
            sense.addContent(EleUtils.createElement(XML_SENSE_DESCRIPTION, get(key)));
            item.addContent(sense);
        }

        return item;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        Iterator<String> iter = keySet().iterator();
        String type = "";
        String key;

        if (bw != null) {
            if (getType().equals(EleConstants.XML_SMELLS)) {
                type = "set_smell";
            } else if (getType().equals(EleConstants.XML_SOUNDS)) {
                type = "set_listen";
            }

            if (iter.hasNext()) {
                bw.write("\n");
            }

            while (iter.hasNext()) {
                key = iter.next();

                bw.write("    "+type+"(\""+ key +"\", ");
                bw.write(Room.replaceMacros(getType()+"-"+key, get(key),5));
                bw.write(");\n");
            }
        }
    }

    public void checkForExport() throws EleMapExportException {
        //not used
    }
}
