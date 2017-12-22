package org.elephant.mapper;

import com.inet.jortho.SpellChecker;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import java.awt.Color;

/**
 * This class just contains the global constants used throughout.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public final class EleConstants {

    /** Can't construct this class */
    private EleConstants() {}

    public static final String VERSION              = "2.0";

    public static final int DIRECTION_UP            = Room.OUT_UP;
    public static final int DIRECTION_NORTH         = Room.OUT_TOP;
    public static final int DIRECTION_NORTHEAST	    = Room.OUT_TOP + Room.OUT_RIGHT;
    public static final int DIRECTION_NORTHWEST	    = Room.OUT_TOP + Room.OUT_LEFT;
    public static final int DIRECTION_EAST          = Room.OUT_RIGHT;
    public static final int DIRECTION_DOWN          = Room.OUT_DOWN;
    public static final int DIRECTION_SOUTH         = Room.OUT_BOTTOM;
    public static final int DIRECTION_SOUTHWEST	    = Room.OUT_BOTTOM + Room.OUT_LEFT;
    public static final int DIRECTION_SOUTHEAST	    = Room.OUT_BOTTOM + Room.OUT_RIGHT;
    public static final int DIRECTION_WEST          = Room.OUT_LEFT;

    public static final String[] DIRECTIONS         = {"up",
                                                       "north",
                                                       "northeast",
                                                       "northwest",
                                                       "east",
                                                       "down",
                                                       "south",
                                                       "southwest",
                                                       "southeast",
                                                       "west"};

    public static final String[] DIRECTION_ABBREV   = {"UP", "N", "NE", "NW", "E", "DOWN", "S", "SW", "SE", "W"};

    public static Color SELECT_COLOUR               = Color.green;
    public static Color ROOM_COLOUR                 = Color.lightGray;
    public static Color EXIT_COLOUR                 = Color.black;
    public static Color BACKGROUND_COLOUR	        = new Color(255, 255, 204);
    public static Color LOWER_LEVEL_COLOUR          = new Color(155, 142, 252);
    public static Color UPPER_LEVEL_COLOUR          = Color.red;

    public static final Color DISABLED_COLOUR 	    = Color.lightGray;
    public static final Color ENABLED_COLOUR  	    = Color.white;

    public static final String XML_ROOMS            = "ROOMS";
    public static final String XML_EXITS            = "EXITS";
    public static final String XML_BOTH             = XML_ROOMS + " " + XML_EXITS;
    public static final String XML_ITEMS            = "ITEMS";
    public static final String XML_SOUNDS           = "SOUNDS";
    public static final String XML_SMELLS           = "SMELLS";
    public static final String XML_LOADED           = "LOADED";
    public static final String XML_FUNCTIONS        = "FUNCTIONS";
    public static final String XML_MONSTERS         = "MONSTERS";

    public static final int EXIT_OPTION_NO          = 0;
    public static final int EXIT_OPTION_YES         = 1;
    public static final int EXIT_OPTION_ROOM1       = 2;
    public static final int EXIT_OPTION_ROOM2       = 3;

    public static final String DEFINE_OBJ_PATH      = "OBDIR";
    public static final String DEFINE_MON_PATH      = "MONDIR";
    public static final String DEFINE_ROOM_PATH     = "ROOMDIR";
    private static boolean initialized = false;

    public static final int MAX_LINE_LENGTH = 78;
    public static final String INDENT = "    ";
    public static String getIndent(int indents) {
        String ret = "";
        for(int i=0;i<indents;i++) {
            ret+=INDENT;
        }
        return ret;

    }

    enum MACRO_TYPE {
        ARRAY,
        MAPPING,
        FIXED
    }

    enum MACRO {
        CLASS(MACRO_TYPE.MAPPING, 1),
        RACE(MACRO_TYPE.MAPPING, 1),
        DAY(MACRO_TYPE.FIXED, 4),
        RANDOM(MACRO_TYPE.ARRAY, 1),
        SEASON(MACRO_TYPE.FIXED, 4),
        ALIGN(MACRO_TYPE.FIXED, 3),
        QUEST(MACRO_TYPE.FIXED, 3);

        private MACRO_TYPE type;
        private int expected_args;
        private MACRO(MACRO_TYPE type, int expected_args) {
            this.type = type;
            this.expected_args = expected_args;
        }
        public MACRO_TYPE getType() { return this.type; }
        public String getMacroName() { return this.name()+"CHANGE"; }

        public static MACRO find(String macro) {
            for (MACRO value: MACRO.values()) {
                if (value.name().equals(macro)) {
                    return value;
                }
            }

            return null;
        }

        public String[] replaceMacroValues(String match) {
            if(this.type == MACRO_TYPE.MAPPING) {
                match = match.replaceAll(":","\":\"");
            }

            return match.split("\\|");
        }

        public boolean validMacro(String match) {
            String[] split = match.split("\\|");
            // Check 1: Expected Args
            if(split.length==0 || (this.type == MACRO_TYPE.FIXED && split.length!=expected_args) ||
                    this.type!=MACRO_TYPE.FIXED && split.length<expected_args) {
                return false;
            }

            // Check 2 - Mappings - make sure we have valid key/value pairs
            if(type==MACRO_TYPE.MAPPING) {
                for(String arg: split) {
                    if(!arg.contains(":")) {
                        return false;
                    }
                }
            }

            return true;

        }
    }

    private static File PROPS = new File("EleMapper.properties");

    public static Configuration freeMarkerConfig;

    public static void init() {
        try {

            if(initialized) {
                return;
            }
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
//            cfg.setDirectoryForTemplateLoading(new File("templates"));

            cfg.setClassForTemplateLoading(EleConstants.class, "/templates/");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            freeMarkerConfig = cfg;

            System.out.println(EleConstants.freeMarkerConfig);

            Properties props = new Properties();
            props.load(new FileInputStream(PROPS));
            SELECT_COLOUR = Color.decode(props.getProperty("SELECT_COLOUR", String.valueOf(SELECT_COLOUR.getRGB())));
            ROOM_COLOUR = Color.decode(props.getProperty("ROOM_COLOUR", String.valueOf(ROOM_COLOUR.getRGB())));
            EXIT_COLOUR = Color.decode(props.getProperty("EXIT_COLOUR", String.valueOf(EXIT_COLOUR.getRGB())));
            BACKGROUND_COLOUR = Color.decode(props.getProperty("BACKGROUND_COLOUR", String.valueOf(BACKGROUND_COLOUR.getRGB())));
            LOWER_LEVEL_COLOUR = Color.decode(props.getProperty("LOWER_LEVEL_COLOUR", String.valueOf(LOWER_LEVEL_COLOUR.getRGB())));
            UPPER_LEVEL_COLOUR = Color.decode(props.getProperty("UPPER_LEVEL_COLOUR", String.valueOf(UPPER_LEVEL_COLOUR.getRGB())));


        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            // do nothing, we don't care.
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialized = true;
    }

    public static void save() {
        try {
            Properties props = new Properties();
            props.setProperty("SELECT_COLOUR", String.valueOf(SELECT_COLOUR.getRGB()));
            props.setProperty("ROOM_COLOUR", String.valueOf(ROOM_COLOUR.getRGB()));
            props.setProperty("EXIT_COLOUR", String.valueOf(EXIT_COLOUR.getRGB()));
            props.setProperty("BACKGROUND_COLOUR", String.valueOf(BACKGROUND_COLOUR.getRGB()));
            props.setProperty("LOWER_LEVEL_COLOUR", String.valueOf(LOWER_LEVEL_COLOUR.getRGB()));
            props.setProperty("UPPER_LEVEL_COLOUR", String.valueOf(UPPER_LEVEL_COLOUR.getRGB()));
            props.store(new FileOutputStream(PROPS), "User defined values for EleMapper");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSelectColour(Color data) {SELECT_COLOUR = data;}
    public static void setRoomColour(Color data) {ROOM_COLOUR = data;}
    public static void setExitColour(Color data) {EXIT_COLOUR = data;}
    public static void setBackGroundColour(Color data) {BACKGROUND_COLOUR = data;}
    public static void setLowerLevelColour(Color data) {LOWER_LEVEL_COLOUR = data;}
    public static void setUpperLevelColour(Color data) {UPPER_LEVEL_COLOUR = data;}


}
