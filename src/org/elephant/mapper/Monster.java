package org.elephant.mapper;

import freemarker.template.TemplateException;
import org.jdom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Monster extends LpcObject implements Exportable {
    public static final String XML_MONSTER = "MONSTER";
    protected static final String XML_RACE = "RACE";
    protected static final String XML_BODY_TYPE = "BODYTYPE";
    protected static final String XML_GENDER = "GENDER";
    protected static final String XML_LEVEL = "LEVEL";
    protected static final String XML_PRIMARY_CLASS = "PRICLASS";
    protected static final String XML_SECOND_CLASS = "SECCLASS";
    protected static final String XML_EMOTES = "EMOTES";
    protected static final String XML_EMOTE = "EMOTE";
    protected static final String XML_EMOTE_FREQ = "EMOTEFREQ";

    private String race = "Human";
    private String bodyType = "Human";
    private String gender = "Neuter";
    private String primaryClass = "";
    private String secondClass = "";
    private int level = 1;
    private int emoteFrequency = 0;
    private List<String> emotes = new ArrayList<String>();

    public Monster() {
        super("monster.ftl", "monster");
    }

    public Monster(String uuid) {
        super("monster.ftl", "monster");
        setUuid(uuid);
    }

    @Override
    public String getType() {
        return XML_MONSTER;
    }

    @Override
    public Element writeToXML() {

        Element monster = super.writeToXML();

        monster.addContent(EleUtils.createElement(XML_BODY_TYPE, getBodyType()));
        monster.addContent(EleUtils.createElement(XML_LEVEL, String.valueOf(getLevel())));
        monster.addContent(EleUtils.createElement(XML_GENDER, getGender()));
        monster.addContent(EleUtils.createElement(XML_RACE, getRace()));
        monster.addContent(EleUtils.createElement(XML_PRIMARY_CLASS, getPrimaryClass()));
        monster.addContent(EleUtils.createElement(XML_SECOND_CLASS, String.valueOf(getSecondClass())));
        monster.addContent(EleUtils.createElement(XML_EMOTE_FREQ, String.valueOf(getEmoteFrequency())));

        Element emotes = new Element(XML_EMOTES);

        for(String emote: getEmotes()) {
            emotes.addContent(EleUtils.createElement(XML_EMOTE, emote));
        }
        monster.addContent(emotes);
        return monster;
    }

    @Override
    public void export(File directory, String path, BufferedWriter bw, String seperator) throws EleMapExportException, IOException {
        File monster = new File(directory.getAbsolutePath() + File.separator + getFileName() + ".c");
        String filledTemplate;
        try {
            filledTemplate = EleUtils.generateFreemarkerFromTemplate(this);
        } catch (TemplateException e) {
            e.printStackTrace();
            return;
        }
        monster.createNewFile();

        bw = new BufferedWriter(new FileWriter(monster));
        bw.write(filledTemplate);
        bw.flush();
        bw.close();
    }

    public static EleExportableCollection<Monster> rebuildMonsters(Element root) {
        EleExportableCollection<Monster> newCollection = null;
        List list;
        Iterator iter;

        if (root != null) {
            newCollection = new EleExportableCollection<Monster>(root.getName());

            if (root.getChild(Monster.XML_MONSTER) != null) {
                list = root.getChildren(Monster.XML_MONSTER);
                iter = list.iterator();
                while (iter.hasNext()) {
                    newCollection.add(Monster.rebuild((Element) iter.next()));
                }
            }
        }

        return newCollection;

    }

    public static Monster rebuild(Element root) {
        Monster monster = new Monster();
        LpcObject.rebuild(monster, root);
        Element tmp = null;
        if (root != null) {
            tmp = root.getChild(XML_BODY_TYPE);
            monster.setBodyType(tmp.getTextTrim());
            tmp = root.getChild(XML_LEVEL);
            monster.setLevel(Integer.valueOf(tmp.getTextTrim()));
            tmp = root.getChild(XML_GENDER);
            monster.setGender(tmp.getTextTrim());
            tmp = root.getChild(XML_RACE);
            monster.setRace(tmp.getTextTrim());
            tmp = root.getChild(XML_PRIMARY_CLASS);
            if (tmp != null) {
                monster.setPrimaryClass(tmp.getTextTrim());
            }

            tmp = root.getChild(XML_SECOND_CLASS);
            if (tmp != null) {
                monster.setSecondClass(tmp.getTextTrim());
            }


            tmp = root.getChild(XML_EMOTE_FREQ);
            if (tmp != null) {
                monster.setEmoteFrequency(Integer.valueOf(tmp.getTextTrim()));
            }

            tmp = root.getChild(XML_EMOTES);
            if (tmp != null) {
                List<String> emotes = new ArrayList<>();
                Iterator iter = tmp.getChildren(XML_EMOTE).iterator();
                while (iter.hasNext()) {
                    emotes.add(((Element) iter.next()).getTextTrim());
                }
                monster.setEmotes(emotes);
            }
        }

        return monster;
    }

    @Override
    public void checkForExport() throws EleMapExportException {
        if(getFileName() == null || getFileName().length() == 0) {
            throw new EleMapExportException("Monster "+getUuid()+" has no filename.", this);
        }
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPrimaryClass() {
        return primaryClass;
    }

    public void setPrimaryClass(String _class) {
        this.primaryClass = _class;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String _secondClass) {
        this.secondClass = _secondClass;
    }

    public int getEmoteFrequency() {
        return emoteFrequency;
    }

    public void setEmoteFrequency(int emoteFrequency) {
        this.emoteFrequency = emoteFrequency;
    }

    public List<String> getEmotes() {
        return emotes;
    }

    public List<String> renderEmotes() {
//        return emotes.stream().map(emote->replace_tokens(emote)).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        for (String emote: emotes) {
            if(emote!=null) {
                list.add(replace_tokens(emote));
            }
        }
        return list;
    }

    public void setEmotes(List<String> emotes) {
        this.emotes = emotes;
    }

    public void addEmote(String emote) {
        this.emotes.add(emote);
    }
}
