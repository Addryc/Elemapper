package org.elephant.mapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class EleExportableCollection<T extends Exportable> extends ArrayList<Exportable> implements Exportable {
    private String _type;

    //----------------------------------------------------------
    // Accessors/Mutators
    //----------------------------------------------------------

    public String getType() {return _type;}

    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleExportableCollection(String type) {
        _type = type;
    }

    //----------------------------------------------------------
    // Static functions
    //----------------------------------------------------------

    public static EleExportableCollection<Exportable> rebuild(Element root) {
        EleExportableCollection<Exportable> newCollection = null;
        List list;
        Iterator iter;

        if (root != null) {
            newCollection = new EleExportableCollection<Exportable>(root.getName());

            if (root.getChild(Item.XML_ITEM) != null) {
                list = root.getChildren(Item.XML_ITEM);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Item.rebuild((Element) iter.next()));
                }
            } else if (root.getChild(LoadedObject.XML_LOADED_OBJECT) != null) {
                list = root.getChildren(LoadedObject.XML_LOADED_OBJECT);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(LoadedObject.rebuild((Element) iter.next()));
                }
            } else if (root.getChild(Function.XML_FUNCTION) != null) {
                list = root.getChildren(Function.XML_FUNCTION);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Function.rebuild((Element) iter.next()));
                }
            }
        }

        return newCollection;
    }

    //----------------------------------------------------------
    // Overridden Functions
    //----------------------------------------------------------

    public boolean add(Exportable o) {
        return super.add(o);
    }

    public Exportable set(int index, Exportable element) {
        return super.set(index, element);
    }

    //----------------------------------------------------------
    // Public Functions
    //----------------------------------------------------------

    public Element writeToXML() {
        Element element = new Element(_type);
        Iterator<Exportable> iter = iterator();

        while (iter.hasNext()) {
            element.addContent(iter.next().writeToXML());
        }

        return element;
    }

    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        String tmp = "";
        Iterator<Exportable> iter = iterator();

        while (iter.hasNext()) {
            iter.next().export(directory, path, bw, tmp);
            tmp = seperator;
        }
    }

    public void checkForExport() throws EleMapExportException {
        Iterator<Exportable> iter = iterator();

        while (iter.hasNext()) {
            iter.next().checkForExport();
        }
    }

}
