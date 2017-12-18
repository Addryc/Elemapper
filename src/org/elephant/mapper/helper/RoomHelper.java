package org.elephant.mapper.helper;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Stack;

import org.elephant.mapper.Room;
import org.elephant.mapper.Item;
import org.elephant.mapper.EleHashtable;
import org.elephant.mapper.LoadedObject;
import org.elephant.mapper.Exportable;
import org.elephant.mapper.EleExportableCollection;
import org.elephant.mapper.Function;

/**
 * @author UKJamesCook
 */
public class RoomHelper {
    private List<Room> selectedRooms = new ArrayList<>();
    private Room copiedRoom;

    public Room getSelectedRoom() {
        return selectedRooms.get(0);
    }

    public void addSelectedRoom(Room room) {
//        System.out.println("Add Selected Room: "+ room.toString());
        selectedRooms.add(room);
//        System.out.println("Rooms after add: "+selectedRooms);
    }

    public void removeSelectedRoom(Room room) {
//        System.out.println("Remove Selected Room: "+ room.toString());
        selectedRooms.remove(room);
//        System.out.println("Rooms after removal: "+selectedRooms);

    }

    public boolean isRoomSelected(Room room) {
        return selectedRooms.contains(room);
    }

    public List<Room> getSelectedRooms() {
  //      System.out.println("Selected Rooms: "+selectedRooms);
        return selectedRooms;
    }

    public void clearSelectedRooms() {
        selectedRooms.clear();
    }

    public boolean hasRoomSelected() {
        return selectedRooms.size()>0;
    }

    public boolean hasSingleRoomSelected() {
        return selectedRooms.size() == 1;
    }

    public boolean hasMultipleRoomSelected() {
        return selectedRooms.size() > 1;
    }



    public Room getCopiedRoom() {
        return copiedRoom;
    }

    public boolean hasCopiedRoom() {
        return copiedRoom != null;
    }

    public void createCopyRoom(int roomSize, long roomNumber) {
        if (hasSingleRoomSelected()) {
            copiedRoom = getSelectedRooms().get(0).clone(roomSize, roomNumber);
        }
    }

    public void updateCurrentRoomWithCopiedRoom() {
        if (hasRoomSelected() && hasCopiedRoom()) {
            for(Room room: getSelectedRooms()) {
                room.update(copiedRoom);
            }
        }
    }

    public void makeCopiedRoomTheCurrentRoom() {
        clearSelectedRooms();
        addSelectedRoom(copiedRoom);
    }

    public void deSelect(Graphics g, long level, boolean showUpper, boolean showLower) {
//        System.out.println(("DeSelect"));
        for(Room room: getSelectedRooms()) {
            room.deSelect(g, level, showUpper, showLower);
        }
        clearSelectedRooms();
    }

    public void addItem(ArrayList<String> itemNames, String description) {
        int i;
        Item item;
        boolean found = false;
        if (itemNames.size() > 0) {
            for (Room room: getSelectedRooms()) {
                for (i = 0; i < room.getItems().size(); i++) {
                    if (room.getItem(i).containsNames(itemNames)) {
                        found = true;
                        break;
                    }
                }
                item = new Item(itemNames, description);
                if (found) {
                    room.getItems().set(i, item);
                } else {
                    room.getItems().add(item);
                }
            }

        }
    }

    public void removeItem(int index) {
        if (index > -1 && hasSingleRoomSelected()) {
            getSelectedRooms().get(0).getItems().remove(index);
        }
    }

    public void removeSense(int index, EleHashtable senses) {
        if (index > -1) {
            if (senses != null) {
                senses.remove(index);
            }
        }
    }

    public void addObject(String fileName, boolean loadPresent, boolean loadTrack, boolean loadUnique,
                          boolean isMonster, String enterMessage, String uuid) {
        int loadType = 0;
        LoadedObject obj;
        LoadedObject old = null;
        boolean found = false;
        int i;
        EleExportableCollection<Exportable> loadedObjects;

        if (fileName != null && fileName.length() > 0) {
            if (loadPresent) {
                loadType = LoadedObject.LOAD_TYPE_PRESENT;
            } else if (loadTrack) {
                loadType = LoadedObject.LOAD_TYPE_TRACK;
            } else if (loadUnique) {
                loadType = LoadedObject.LOAD_TYPE_UNIQUE;
            }

            obj = new LoadedObject((isMonster ?LoadedObject.OBJECT_TYPE_MON:LoadedObject.OBJECT_TYPE_OBJ),
                                   loadType, fileName, enterMessage);
            obj.setUuid(uuid);
            for(Room room: getSelectedRooms()) {
                loadedObjects = room.getLoadedObjects();
                for (i = 0; i < loadedObjects.size(); i++) {
                    if (((LoadedObject) loadedObjects.get(i)).getFileName().equals(fileName)) {
                        found = true;
                        old = (LoadedObject) loadedObjects.get(i);
                        break;
                    }
                }

                if (found) {
                    // Persist UUID if its already set
                    if (old != null && old.getUuid()!=null) {
                        obj.setUuid(old.getUuid());
                    }
                    loadedObjects.set(i, obj);
                } else {
                    loadedObjects.add(obj);
                }

            }
        }
    }

    public void addFunction(String functionName, String returnType, String arguments, String body) {
        Function function;
        boolean found = false;
        int i;
        EleExportableCollection<Exportable> functions;

        if (functionName != null && functionName.length() > 0) {

            function = new Function(returnType, functionName, arguments, body);

            functions = getSelectedRoom().getFunctions();
            for (i = 0; i < functions.size(); i++) {
                if (((Function) functions.get(i)).getName().equals(functionName)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                functions.set(i, function);
            } else {
                functions.add(function);
            }
        }
    }

    public void removeObject(LoadedObject object) {
        getSelectedRoom().getLoadedObjects().remove(object);
    }

    public void removeFunction(Function function) {
        getSelectedRoom().getFunctions().remove(function);
    }

    public void setColour(Color c) {
        if (hasRoomSelected()) {
            for(Room room: getSelectedRooms()) {
                room.setColour(c);
            }
        }
    }

    public EleHashtable currentSense(boolean smells, boolean sounds) {
        return currentSense(smells, sounds, getSelectedRoom());
    }

    public static EleHashtable currentSense(boolean smells, boolean sounds, Room room) {
        EleHashtable senses = null;

        if (room != null) {
            if (smells) {
                senses = room.getSmells();
            } else if (sounds) {
                senses = room.getSounds();
            }
        }
        return senses;
    }

}
