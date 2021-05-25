package org.rch.jarvisapp.smarthome.init;

import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.areas.Area;
import org.rch.jarvisapp.smarthome.areas.Room;
import org.rch.jarvisapp.smarthome.init.enums.PlaceKeys;

import java.util.Map;

public class PlaceBuilder {
    public static Place create(Map<String,Object> data, SmartHome smartHome){
        String type;
        if ((type = PlaceKeys.type.get(data)) != null)
            switch (type){
                case (Room.className) :
                    return new Room(
                            PlaceKeys.code.get(data),
                            PlaceKeys.name.get(data),
                            smartHome.getPlaceByCode(PlaceKeys.parent.get(data)));
                case (Area.className) :
                    return new Area(
                            PlaceKeys.code.get(data),
                            PlaceKeys.name.get(data),
                            smartHome.getPlaceByCode(PlaceKeys.parent.get(data)));
            }
        return null;
    }
}
