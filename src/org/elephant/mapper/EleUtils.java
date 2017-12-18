package org.elephant.mapper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import java.awt.Point;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.jdom.Element;

/**
 * A class that holds useful utilities that are need by the rest of the
 * package. It only contains static functions.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleUtils {

    /** Can't construct this class */
    private EleUtils() {}

    public static Element createElement(String name, String value) {
        return new Element(name).setText(value);
    }

    public static String replace(String s, String find, String replace) {
        StringBuffer sb = new StringBuffer();
        int pos = 0;
        int newpos = s.indexOf(find, pos);

        while (newpos != -1) {
            sb.append(s.substring(pos, newpos)).append(replace);
            pos = newpos + find.length();
            newpos = s.indexOf(find, pos);
        }
        sb.append(s.substring(pos));

        return sb.toString();
    }

    public static String[] breakString(String s, int length) {
        List<String> list = new ArrayList<String>();
        List words;
        String[] data;
        String line = "";
        String tmp;
        int size;

        // make sentences
        words = words(s);
        for (int i = 0; i < words.size(); i++) {
            tmp = words.get(i) + " ";
            if (line.length() < 1) {
                line = tmp;
            } else {
                if ((line.length() + tmp.length()) < length) {
                    line += tmp;
                } else {
                    list.add(line);
                    line = tmp;
                }
            }
        }

        if (line.length() > 0) list.add(line);

        // trim the last sentence
        size = list.size();
        if (size > 0) {
            list.set(size-1, list.get(size-1).trim());
        }

        // convert to array
        data = list.toArray(new String[size]);

        return data;
    }

    public static String repeatChar(char c, int n) {
        StringBuffer sb = new StringBuffer(n);

        for (int i = 0; i < n; i++) {
            sb.append(c);
        }

        return sb.toString();
    }

    public static final String firstPart(String s, int n) {
        List<String> list = words(s);
        StringBuffer sb = new StringBuffer();
        String tmp;

        for (int i = 0; i < list.size(); i++) {
            tmp = list.get(i) + " ";
            if ((sb.length() + tmp.length()) < n) {
                sb.append(tmp);
            } else {
                break;
            }
        }

        return sb.toString();
    }

    public static final List<String> words(String s) {
        List<String> list = new ArrayList<String>();
        StringTokenizer st;

        if (s != null) {
            st = new StringTokenizer(s, " ");

            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
        }

        return list;
    }

    public static Point getPointForDirection(int direction, int x, int y, int height, int width) {
        Point p = new Point(x, y);

        switch(direction) {
        case EleConstants.DIRECTION_UP:
            p.x += (width/2);
            break;
        case EleConstants.DIRECTION_NORTH:
            p.x += (width/2);
            break;
        case EleConstants.DIRECTION_NORTHEAST:
            p.x += width;
            break;
        case EleConstants.DIRECTION_NORTHWEST:
            //No need to modify the Point here.
            break;
        case EleConstants.DIRECTION_EAST:
            p.x += width;
            p.y += (height/2);
            break;
        case EleConstants.DIRECTION_DOWN:
            p.x += (width/2);
            p.y += height;
            break;
        case EleConstants.DIRECTION_SOUTH:
            p.x += (width/2);
            p.y += height;
            break;
        case EleConstants.DIRECTION_SOUTHWEST:
            p.y += height;
            break;
        case EleConstants.DIRECTION_SOUTHEAST:
            p.x += width;
            p.y += height;
            break;
        case EleConstants.DIRECTION_WEST:
            p.y += (height/2);
            break;
        }
        return p;
    }

    public static int translateDirectionToIndex(int d) {
        int index = 0;

        switch(d) {
        case EleConstants.DIRECTION_UP:
            index = 0;
            break;
        case EleConstants.DIRECTION_NORTH:
            index = 1;
            break;
        case EleConstants.DIRECTION_NORTHEAST:
            index = 2;
            break;
        case EleConstants.DIRECTION_NORTHWEST:
            index = 3;
            break;
        case EleConstants.DIRECTION_EAST:
            index = 4;
            break;
        case EleConstants.DIRECTION_DOWN:
            index = 5;
            break;
        case EleConstants.DIRECTION_SOUTH:
            index = 6;
            break;
        case EleConstants.DIRECTION_SOUTHWEST:
            index = 7;
            break;
        case EleConstants.DIRECTION_SOUTHEAST:
            index = 8;
            break;
        case EleConstants.DIRECTION_WEST:
            index = 9;
            break;
        }
        return index;
    }

    public static int getOppositeDirection(int d) {
        int newDirection = 0;

        switch(d) {
        case EleConstants.DIRECTION_UP:
            newDirection = EleConstants.DIRECTION_DOWN;
            break;
        case EleConstants.DIRECTION_NORTH:
            newDirection = EleConstants.DIRECTION_SOUTH;
            break;
        case EleConstants.DIRECTION_NORTHEAST:
            newDirection = EleConstants.DIRECTION_SOUTHWEST;
            break;
        case EleConstants.DIRECTION_NORTHWEST:
            newDirection = EleConstants.DIRECTION_SOUTHEAST;
            break;
        case EleConstants.DIRECTION_EAST:
            newDirection = EleConstants.DIRECTION_WEST;
            break;
        case EleConstants.DIRECTION_DOWN:
            newDirection = EleConstants.DIRECTION_UP;
            break;
        case EleConstants.DIRECTION_SOUTH:
            newDirection = EleConstants.DIRECTION_NORTH;
            break;
        case EleConstants.DIRECTION_SOUTHWEST:
            newDirection = EleConstants.DIRECTION_NORTHEAST;
            break;
        case EleConstants.DIRECTION_SOUTHEAST:
            newDirection = EleConstants.DIRECTION_NORTHWEST;
            break;
        case EleConstants.DIRECTION_WEST:
            newDirection = EleConstants.DIRECTION_EAST;
            break;
        }
        return newDirection;
    }


    public static String generateFreemarkerFromTemplate(LpcObject obj) throws IOException, TemplateException {
        EleConstants.init();
        Map<String, Object> model = new HashMap<>();
        model.put(obj.getModelName(), obj);
        Template temp = EleConstants.freeMarkerConfig.getTemplate(obj.getTemplate());
        StringWriter out = new StringWriter();
        temp.process(model, out);
        return out.getBuffer().toString();

    }
}

