package org.elephant.mapper;

import freemarker.template.TemplateException;
import org.jdom.CDATA;
import org.jdom.Element;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//      ${monster.filePath}
//      ${monster.name}
//      From the Elephant Mudlib
//      AutoCoded by EleMapper at ${monster.updated}
public abstract class LpcObject {
    int maxWrapLength = 75; // Best guess based on typical indent being 4
    protected static final String XML_UUID = "UUID";
    protected static final String XML_NAME = "NAME";
    protected static final String XML_FILENAME = "FILENAME";
    protected static final String XML_CODEDESC = "CODEDESC";
    protected static final String XML_SHORT = "SHORT";
    protected static final String XML_LONG = "LONG";
    protected static final String XML_IDS = "IDS";
    protected static final String XML_ID = "ID";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LpcObject lpcObject = (LpcObject) o;
        return Objects.equals(uuid, lpcObject.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "LpcObject{" +
                "type='" + getType() + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", short='" + shortDesc + '\'' +
                '}';
    }

    private String uuid = "";
    private String name = "";
    private String shortDesc = "";
    private String longDesc = "";

    private List<String> ids = new ArrayList<>();

    private String codeDescription = "";

    private String fileName = "";

    private String template = "";

    private String modelName = "";

    protected abstract String getType();

    public LpcObject(String template, String modelName,
                     String uuid) {
        this.template = template;
        this.modelName = modelName;
        this.uuid = uuid;
    }

    public LpcObject(String template, String modelName) {
        this.codeDescription = codeDescription;
        this.fileName = fileName;
        this.template = template;
        this.modelName = modelName;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath(String path) {
        return path + File.pathSeparator + fileName;
    }

    public String getUpdated() {
        return new SimpleDateFormat("HH:mm, dd MMMM yyyy").format(new Date());
    }

    public String getTemplate() {
        return template;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }
    public String getShortDescWrapped() { return String.join("\"\n        \"", EleUtils.breakString(shortDesc, 64));}

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }
    public String getLongDescWrapped() { return String.join("\"\n        \"", EleUtils.breakString(longDesc, 64));}
    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public void addId(String id) {
        this.ids.add(id);
    }


    String replace_tokens(String str) {
        return str.
                replaceAll("\\%N", "\"+query_name()+\"").
                replaceAll("\\%S", "\"+query_subjective()+\"").
                replaceAll("\\%P", "\"+query_possessive()+\"").
                replaceAll("\\%O", "\"+query_objective()+\"");
    }

    public Element writeToXML() {
        Element obj = new Element(getType());
        obj.addContent(EleUtils.createElement(XML_UUID, String.valueOf(getUuid())));
        obj.addContent(EleUtils.createElement(XML_FILENAME, getFileName()));
        obj.addContent(EleUtils.createElement(XML_NAME, getName()));
        obj.addContent(EleUtils.createElement(XML_CODEDESC, getCodeDescription()));
        obj.addContent(EleUtils.createElement(XML_SHORT, getShortDesc()));
        obj.addContent(EleUtils.createElement(XML_LONG, getLongDesc()));
        Element ids = new Element(XML_IDS);

        for(String id: getIds()) {
            ids.addContent(EleUtils.createElement(XML_ID, id));
        }
        obj.addContent(ids);
        return obj;
    }

    public static LpcObject rebuild(LpcObject base, Element root) {
        Element tmp = null;
        if (root != null && base != null) {
            tmp = root.getChild(XML_UUID);
            base.setUuid(tmp.getTextTrim());

            tmp = root.getChild(XML_FILENAME);
            base.setFileName(tmp.getTextTrim());
            tmp = root.getChild(XML_NAME);
            base.setName(tmp.getTextTrim());
            tmp = root.getChild(XML_CODEDESC);
            base.setCodeDescription(tmp.getTextTrim());
            tmp = root.getChild(XML_SHORT);
            base.setShortDesc(tmp.getTextTrim());
            tmp = root.getChild(XML_LONG);
            base.setLongDesc(tmp.getTextTrim());
            tmp = root.getChild(XML_IDS);
            if (tmp != null) {
                List<String> ids = new ArrayList<>();
                Iterator iter = tmp.getChildren(XML_ID).iterator();
                while (iter.hasNext()) {
                    ids.add(((Element) iter.next()).getTextTrim());
                }
                base.setIds(ids);
            }

        }

        return base;
    }


    public String template() {
        try {
            return EleUtils.generateFreemarkerFromTemplate(this);
        } catch (TemplateException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }



}
